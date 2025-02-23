{-# LANGUAGE InstanceSigs #-}
{-# OPTIONS_GHC -Wno-unrecognised-pragmas #-}
{-# HLINT ignore "Use isJust" #-}

module LambdaCalculus.Internal (module Set, module LambdaCalculus.Internal) where

{-

Interest-based Project - Lambda Calculus Interpreter
====================================================


The main aim of this project is to implement an interpreter for the lambda calculus.

Till now, we have covered the fundamentals of functional programming and have taken a look at its formal foundations, the lambda calculus. It is now time to combine and deepen our hands-on understanding of both these topics by implementing an interpreter for the lambda calculus in Haskell! This should allow you to test your understanding of the functional programming material done till now by applying it to solving a larger problem, and at the same time consolidate your understanding of the lambda calculus in a practical hands-on style. Additionally, this assignment should serve as a motivation for the topics and techniques (e.g. functors, applicative functors, monads, property-based testing, parsing combinators, algebraic design, ...) that are used in modern programming and may be covered later in the course. For most of you, this may be your first implementation of a Turing-complete programming language!

Make sure that you have completed the recommended paper-based exercises from the chapters "Lambda Calculus Basics" and "Computation with the Lambda Calculus" before starting with this project.

Note that this is an interest based project. Although a minimal required goal is given as exercises in this file, it is up to you to develop it further with additional features in a way that is of interest to you. The minimal goal will be checked by the automated tests in this package. The additional features are to be demonstrated individually to the person supervising your exercise session.

Learning Goals:
- Using embedded domain-specific languages (EDSLs) to represent and solve problems (the lambda calculus, but also exposure to pretty printing, parsing, property-based testing, etc.)
- Explaining formally how the lambda calculus works and can be used for computation.
- Being able to read and understand a formal specification and produce a faithful implementation based on it.
- Revision and application of all previous lesson goals, in particular:
  - Recursion
  - Specification of correctness properties
  - Property-based testing
  - List comprehension

Note: A general problem with testing is that most functions within an API can only be tested with respect to others in the same API. Therefore, running `stack test` before completing all the exercises in this module may give you unexpected results, since the tests for one function may depend on another function that is not yet implemented. It is therefore recommended to use the tests stated as eval comments within each exercises in this module while solving each exercise, and only running `stack test` after you have completed all exercises. It may also be the case, that a test stated as an eval comment fails due to an error in a function that you defined earlier and thought was correct since the tests you tried at that time all passed. In such cases, it helps to take a closer look at the statement of the property that is being tested to make sure that you are looking for faults in the right places. 

Note: This file heavily uses characters in UTF-8 encoding. In case you get an error mentioning "invalid argument (invalid character)" on Windows when executing `stack test`, here is something that you can try:
  stack clean
  chcp 65001
  stack test
The command `chcp 65001` sets the codepage to UTF-8. For more information: https://stackoverflow.com/questions/57131654/using-utf-8-encoding-chcp-65001-in-command-prompt-windows-powershell-window/57134096#57134096

-}



-- This module requires an implementation of sets. By default, it uses the implementation provided in the module `LambdaCalculus.UnorderedSet`. But you could replace it with your own implementation in the module `BinarySearchTree` by modifying file `src/LambdaCalculus/Set.hs`.
import qualified LambdaCalculus.Set as Set

import Test.QuickCheck
import Prettyprinter

{-
Section 1: Representing Lambda Terms
------------------------------------

We will start by defining a data type that we can use to represent lambda terms in Haskell
-}


-- The name of a variable, also referred to as an identifier, is a value of type String.
type Id = String

-- The algebraic data type (ADT) `Term` is used to represent lambda terms as follows.
data Term           -- Lambda Terms are either    (M ::=)
  = Var Id          -- Variables, or              (x)
  | Abs Id Term     -- Abstractions, or           (λ x. M)
  | App Term Term   -- Applications               (M M)
  deriving (Eq, Show, Read)

-- Note on validity of the term representation: Since all values that are representable using the ADT `Term` are valid lambda terms, one can safely export its data constructors `Var`, `Abs`, and `App`. Strictly speaking, the name of a variable should not contain the parentheses `(` and `)`, spaces (reserved for application), nor the character `λ` (reserved for abstraction). The above representation would work nevertheless, but we would run into problems during parsing or pretty printing. We therefore leave this as it is currently in favor of simplicity, with the option of treating it appropriately (using, for example, escape sequences) during parsing and pretty printing, should the need arise.

-- Below are some examples of how some of the lambda terms that we have seen till now are represented as values of type `Term`.

{-
Note: Haskell variables corresponding to lambda terms are prefixed with the character `t` so that upper case letters and unicode letters can be used to refer to them, just like in the lecture notes.
Refer to https://www.haskell.org/onlinereport/haskell2010/haskellch2.html#x7-150002.1 for more details.
-}


-- (f x)
tfx :: Term
tfx = App (Var "f") (Var "x")

-- >>> tfx
-- App (Var "f") (Var "x")

-- g (f x)
tgfx :: Term
tgfx = App (Var "g") tfx

-- >>> tgfx
-- App (Var "g") (App (Var "f") (Var "x"))

-- I = λ x . x
tI :: Term
tI = Abs "x" (Var "x")

-- >>> tI
-- Abs "x" (Var "x")

-- L = λ x . λ y. y
tL :: Term
tL = Abs "x" (Abs "y" (Var "y"))

-- >>> tL
-- Abs "x" (Abs "y" (Var "y"))

-- ω = λ x . x x
tω :: Term
tω = Abs "x" (App (Var "x") (Var "x"))

-- >>> tω
-- Abs "x" (App (Var "x") (Var "x"))

-- Ω = ω ω = (λ x . x x) (λ x . x x)
tΩ :: Term
tΩ = App tω tω

-- >>> tΩ
-- App (Abs "x" (App (Var "x") (Var "x"))) (Abs "x" (App (Var "x") (Var "x")))

-- As we can see above, the function `show` is not always appropriate to generate human-readable output since its output should be valid Haskell code. This is especially true for large terms.

-- Generates large terms of (exponentially) varying size to demonstrate the advantages of good pretty printing
tLargeGen :: (Ord t, Num t) => t -> Term
tLargeGen n
  | (n<0) || (n>6)  = tI  -- Avoid negative (and too large) terms to ensure termination
  | otherwise = App (tLargeGen (n-1)) (tLargeGen (n-1))

-- >>> tLargeGen 3
-- App (App (App (App (Abs "x" (Var "x")) (Abs "x" (Var "x"))) (App (Abs "x" (Var "x")) (Abs "x" (Var "x")))) (App (App (Abs "x" (Var "x")) (Abs "x" (Var "x"))) (App (Abs "x" (Var "x")) (Abs "x" (Var "x"))))) (App (App (App (Abs "x" (Var "x")) (Abs "x" (Var "x"))) (App (Abs "x" (Var "x")) (Abs "x" (Var "x")))) (App (App (Abs "x" (Var "x")) (Abs "x" (Var "x"))) (App (Abs "x" (Var "x")) (Abs "x" (Var "x")))))

-- It is therefore often a better idea to define a separate function for pretty printing. 

-- Exercise LC.1.1 (*)
-- The automatically derived `show` function returns a value of type `String` that is valid Haskell code that can be used for debugging, but it is not very human readable.
-- Define a function `toReadableStr` that returns a human readable version of a `Term`. As shown below, the output of this function should not contain any spaces and be fully parenthesized. This will simplify your implementation and make its output unique, which is required to make its automated tests pass.

-- >>> toReadableStr tfx == "(f x)"
-- True

-- >>> toReadableStr tgfx == "(g (f x))"
-- True

-- >>> toReadableStr tI == "(λx.x)"
-- True

-- >>> toReadableStr tL == "(λx.(λy.y))"
-- True

-- >>> toReadableStr tω == "(λx.(x x))"
-- True


toReadableStr :: Term -> String
toReadableStr (Var x) = undefined
toReadableStr (Abs x m) = undefined
toReadableStr (App m n) = undefined


-- Note the following behavior when using ghci or the HLS eval plugin:

-- >>> toReadableStr tI
-- "(\955x.x)"

-- We see that the `λ` symbol has been escaped to its unicode equivalent `\955`. This is because ghci and the HLS eval plugin uses `print :: Show a => a -> IO ()`, which internally uses `show`. The implementation of `show` for `Char` and `String` escapes non-ASCII characters (more details: https://gitlab.haskell.org/ghc/ghc/-/issues/11529). 

-- In addition to the `λ` symbol being escaped, the implementation of `toReadableStr` does not include indentation or other formatting, making larger terms difficult to read. 

-- >>> toReadableStr (tLargeGen 3)
-- "(((((\955x.x) (\955x.x)) ((\955x.x) (\955x.x))) (((\955x.x) (\955x.x)) ((\955x.x) (\955x.x)))) ((((\955x.x) (\955x.x)) ((\955x.x) (\955x.x))) (((\955x.x) (\955x.x)) ((\955x.x) (\955x.x)))))"

-- Implementing a pretty printer that indents terms appropriately, taking varying line lengths into consideration is tricky. 
-- Fortunately for us, many very smart people have made it a priority to provide a general elegant algebraic EDSL-based solution to this problem (See https://homepages.inf.ed.ac.uk/wadler/papers/prettier/prettier.pdf for more details).
-- This module uses the package `pretty-printer` (https://hackage.haskell.org/package/prettyprinter) to implement a pretty printer using the function `pretty` for lambda terms. Its implementation can be found towards the end of the file. You may take a look at it in case you are interested, but understanding it is not required to complete this exercise. You may use the pretty printer as follows.

-- >>> pretty tI
-- (λx.x)

-- >>> pretty (tLargeGen 3)
-- (
--   ((((λx.x) (λx.x)) ((λx.x) (λx.x))) (((λx.x) (λx.x)) ((λx.x) (λx.x))))
--   ((((λx.x) (λx.x)) ((λx.x) (λx.x))) (((λx.x) (λx.x)) ((λx.x) (λx.x))))
-- )

-- >>> pretty (tLargeGen 4)
-- (
--   (
--     ((((λx.x) (λx.x)) ((λx.x) (λx.x))) (((λx.x) (λx.x)) ((λx.x) (λx.x))))
--     ((((λx.x) (λx.x)) ((λx.x) (λx.x))) (((λx.x) (λx.x)) ((λx.x) (λx.x))))
--   )
--   (
--     ((((λx.x) (λx.x)) ((λx.x) (λx.x))) (((λx.x) (λx.x)) ((λx.x) (λx.x))))
--     ((((λx.x) (λx.x)) ((λx.x) (λx.x))) (((λx.x) (λx.x)) ((λx.x) (λx.x))))
--   )
-- )

-- Since `pretty` is defined within the type class `Pretty`, it also extends nicely, for instance to lists of lambda terms.

-- >>> pretty [tI, tL, tω, tΩ]
-- [(λx.x), (λx.(λy.y)), (λx.(x x)), ((λx.(x x)) (λx.(x x)))]

-- >>> pretty [tI, tL, tω, tΩ, tI, tL, tω, tΩ]
-- [ (λx.x)
-- , (λx.(λy.y))
-- , (λx.(x x))
-- , ((λx.(x x)) (λx.(x x)))
-- , (λx.x)
-- , (λx.(λy.y))
-- , (λx.(x x))
-- , ((λx.(x x)) (λx.(x x))) ]


-- Below are some more examples of terms that we will use later.

-- ωI = ω I = (λx.x x) λx.x
tωI :: Term
tωI = App tω tI

-- >>> pretty tωI
-- ((λx.(x x)) (λx.x))

-- t$ = λx.λy. x y
-- Note: The `$` character cannot be used as part of a variable name in Haskell since it is a symbol. Refer to https://www.haskell.org/onlinereport/haskell2010/haskellch2.html#x7-150002.1 for more details.
tDollar :: Term
tDollar = Abs "x" (Abs "y" (App (Var "x") (Var "y")))

-- >>> pretty tDollar
-- (λx.(λy.(x y)))

-- ω$ = ω $ = (λx.x x) (λx.λy. x y)
tωDollar :: Term
tωDollar = App tω tDollar

-- >>> pretty tωDollar
-- ((λx.(x x)) (λx.(λy.(x y))))

{-

Now that we are familiar with lambda terms and their representation using the `Term` datatype, we can think about how to achieve our main aim of implementing an interpreter for the lambda calculus.

From the lecture notes we know that:
  - Execution = Reduction in the lambda calculus.
  - In order to implement beta reduction, we need to implement substitution.
  - In order to implement substitution, we need to be able to:
    - Decide whether a variable occurs freely within a term
    - Be able to perform alpha conversion and generate fresh variables to rename bound variables to fresh variables in order to avoid variable capture

We will therefore proceed by first building up the set of operations that we need to implement substitution.
-}

{-
Section 2: Basic Operations on Lambda Terms
-------------------------------------------
-}


-- Exercise LC.2.1 (*)
-- The function `nfin x m` returns `True` if and only if the variable with name `x` does not occur freely within the lambda term `m`.
-- Equivalently: The function `nfin x m` returns `False` if the variable with name `x` occurs freely within the lambda term `m`, and `True` in all other cases.
-- Complete the definition of the function `nfin`.
-- Hint: Directly translating the definition of this function into Haskell syntax should suffice.
-- After you are done with the implementation, examine the properties that follow, convince yourself of their meaning and validity and verify that your implementations are correct by running the tests.

nfin :: Id -> Term -> Bool
x `nfin` (Var y) = undefined
x `nfin` (Abs y t) = undefined
x `nfin` (App m n) = undefined


-- `y` is not present, and therefore does not occur freely in `f x`
prop_nfin_y_tfx :: Bool
prop_nfin_y_tfx = "y" `nfin` tfx

-- prop> prop_nfin_y_tfx
-- +++ OK, passed 1 test.

-- x is free in (f x)
prop_nfin_x_tfx :: Bool
prop_nfin_x_tfx = not ("x" `nfin` tfx)

-- prop> prop_nfin_x_tfx
-- +++ OK, passed 1 test.

-- f is free in (f x)
prop_nfin_f_tfx :: Bool
prop_nfin_f_tfx = not ("f" `nfin` tfx)

-- prop> prop_nfin_f_tfx
-- +++ OK, passed 1 test.

-- x is not free in (λx.x)
prop_nfin_x_tI :: Bool
prop_nfin_x_tI = "x" `nfin` tI

-- prop> prop_nfin_x_tI
-- +++ OK, passed 1 test.


-- Exercise LC.2.2 (*)
-- The function `freeVars m` returns a set containing all the variables occurring freely in the term `m`.
-- Complete the definition of the function `freeVars`.
-- After you are done with the implementation, examine the properties that follow, convince yourself of their meaning and validity and verify that your implementations are correct by running the tests.

freeVars :: Term -> Set.T Id
freeVars (Var x) = undefined
freeVars (Abs x m) = undefined
freeVars (App m n) = undefined

-- >>> Set.toList (freeVars tI) == []
-- True

-- >>> freeVars tgfx == Set.fromList ["f","g","x"]
-- True

-- >>> freeVars tω == Set.empty
-- True

-- >>> freeVars tΩ == Set.empty
-- True


-- Exercise LC.2.3 (**)
-- The property `prop_freeVars_nfin` expresses the fact that all the variables returned by `freeVars` are also free with respect to `nfin`.
-- Complete the definition of `prop_freeVars_nfin` and make sure that the property-based tests pass for this property.
-- Hint: Use list comprehension and the function `and :: [Bool] -> Bool`.

prop_freeVars_nfin :: Term -> Bool
prop_freeVars_nfin m = undefined



-- prop> prop_freeVars_nfin
-- +++ OK, passed 100 tests.


-- Exercise LC.2.4 (**)
-- Recall that while performing substitution, it is sometimes required to rename bound variables to fresh variables in order to avoid variable capture. Although not strictly necessary, renaming bound variables using a name similar to its original name (for instance, by appending a `'` to the name) makes the renamed term easier to recognize.
-- The function `freshId s x` returns an identifier that does not occur in set `s`, but still starts with the string `x`. This is to be achieved by successively appending the character `'` to `x` until it does not occur in `s`.
-- Hint: Use recursion to define this function.

-- >>> freshId (Set.fromList ["x","y"]) "x" == "x'"
-- True

-- >>> freshId (Set.fromList ["x","x'","y"]) "x'" == "x''"
-- True

-- >>> freshId (Set.fromList ["x'","y"]) "x" == "x"
-- True

freshId :: Set.T Id -> Id -> Id
freshId s x = if x `Set.member` s then undefined else x


-- Exercise LC.2.5 (**)
-- Complete the following definitions of the correctness properties of `freshId` and make sure their the property-based tests pass.

-- `freshId s x` is not a member of `s`
prop_freshId_not_member :: Set.T Id -> Id -> Bool
prop_freshId_not_member s x = undefined


-- prop> prop_freshId_not_member
-- +++ OK, passed 100 tests.

-- `freshId s x` starts with the string`x`
prop_freshId_prefix :: Set.T Id -> Id -> Bool
prop_freshId_prefix s x = undefined


-- prop> prop_freshId_prefix
-- +++ OK, passed 100 tests.

{-
Section 3: Substitution, alpha conversion & alpha equivalence on Lambda Terms
-----------------------------------------------------------------------------

We are now in a position to define the substitution, alpha conversion & alpha equivalence operations on lambda terms.

-}

-- A substitution "[x:=M]" is represented using the pair `(x,M)` where `x` is the identifier to be substituted, and `M` the term it is to be substituted with.
type Subst = (Id, Term)

-- Looking at the definitions of substitution and alpha conversion (i.e., renaming of bound variables), we notice that the definition of substitution requires alpha conversion, and the definition of alpha conversion requires substitution! We therefore need to define both these operations using mutual recursion.


-- Exercise LC.3.1 (****)
-- The function `applySubst (x,l) m` replaces all free occurrences of the variable `x` within the term `m` with the term `l`. 
-- The function `renameBoundVar (Abs x m) y` performs alpha conversion by renaming the bound variable `x` within `(Abs x m)` with the given identifier `y`. The function returns `Nothing` in case this is not possible (i.e., in case the input is not a lambda abstraction or in case `y` is free in `m`. In case `y` is not free in `m`, `renameBoundVar (Abs x m) y` is not `Nothing` and its content is alpha equivalent to `(Abs x m)`.
-- Complete the definitions of `applySubst` and `renameBoundVar` below.
-- Hint: A good way to start is to directly translate the formal definitions of these operations into Haskell syntax.
-- Hint: Take care to avoid variable capture (you will have to do some alpha renaming with fresh variables to avoid this). Use `freshId` to generate any non-free variables that are needed while implementing `applySubst`. In case you find the task of avoiding variable capture too challenging, skip this initially and try to work it into your solution later.

-- After you are done with the implementation, examine the properties that follow, convince yourself of their meaning and validity and verify that your implementations are correct by running the tests.

-- Hint: The following function (copied from the module `Data.Maybe`) can be used to extract the underlying value of a value of type `Maybe a` in cases when one is certain that this value is not `Nothing`
fromJust :: Maybe a -> a
fromJust (Just x) = x

-- >>> renameBoundVar (Abs "x" (Var "x")) "y" == Just (Abs "y" (Var "y"))
-- True

-- >>> renameBoundVar (Abs "x" (Var "y")) "y" == Nothing
-- True

-- >>> renameBoundVar (Var "x") "y" == Nothing
-- True

-- >>> applySubst ("x", Var "y") (Var "x") == Var "y"
-- True

-- >>> applySubst ("x", Var "y") (Var "y") == Var "y"
-- True

-- >>> applySubst ("x", Var "y") (Abs "x" (Var "x")) == Abs "x" (Var "x")
-- True

-- The following test requires renaming bound variables and also depends on the choice of names chosen for the fresh variables. But the test should pass if the function `freshId` is implemented as specified.
-- [x := y] λy.x y = λy'.y y'
-- >>> applySubst ("x", Var "y") (Abs "y" (App (Var "x") (Var "y"))) == Abs "y'" (App (Var "y") (Var "y'"))
-- True

-- The following test requires renaming bound variables and also depends on the choice of names chosen for the fresh variables. But the test should pass if the function `freshId` is implemented as specified.
-- [x := y] λy.x y y' = λy''.y y'' y'
-- >>> applySubst ("x", Var "y") (Abs "y" $ App (App (Var "x") (Var "y")) (Var "y'")) == Abs "y''" (App (App (Var "y") (Var "y''")) (Var "y'"))
-- True


applySubst :: Subst -> Term -> Term
applySubst (x, l) (Var y)
  | x == y = undefined
  | otherwise = undefined -- case (x /= y)
applySubst (x, l) (Abs y m)
  | x == y = undefined
  | x /= y && y `nfin` l = undefined
  | otherwise = -- case (x /= y && y `Set.member` freeVars l)
      undefined
applySubst (x, l) (App m n) = undefined




-- [x := y] λy.x y y' = λy''.y y'' y'
-- >>> applySubst ("x", Var "y") (Abs "y" $ App (App (Var "x") (Var "y")) (Var "y'")) == Abs "y''" (App (App (Var "y") (Var "y''")) (Var "y'"))
-- True


renameBoundVar :: Term -> Id -> Maybe Term
renameBoundVar (Abs x m) y = if y `nfin` m 
                              then Just undefined
                              else Nothing
renameBoundVar _ _ = Nothing


-- Renaming a bound variable within a lambda abstraction with a fresh variable always succeeds.
prop_renameBoundVar_freshId :: Term -> Bool
prop_renameBoundVar_freshId m = renameBoundVar (Abs "x" m) (freshId (Set.insert "x" (freeVars m)) "x") /= Nothing

-- prop> prop_renameBoundVar_freshId
-- +++ OK, passed 100 tests.



-- In order to state the correctness property of alpha conversion we need to be able to decide whether two lambda terms are alpha equivalent. Alpha equivalence is additionally useful to compare the expected and actual results of beta reduction, since the result of beta reductions are only equal modulo alpha renaming.


-- Exercise LC.3.2 (**)
-- The function `alphaEq m n` returns `True` if and only if the terms `m` and `n` are alpha equivalent, i.e., only differ in the names of their bound variables. 
-- Complete the definition of `alphaEq` below.
-- Hint: Use recursion to define this function.
-- Hint: When comparing two lambda abstractions with distinct bound variables, use `renameBoundVar` or `applySubst` to make these variables identical before continuing with the recursion.

alphaEq :: Term -> Term -> Bool
alphaEq (Var x) (Var y) = undefined
alphaEq (App m1 n1) (App m2 n2) = undefined
alphaEq (Abs x m) (Abs y n) = undefined
alphaEq _ _ = undefined


-- Renaming a bound variable with a fresh variable results in an alpha equivalent term.
-- Convince yourself of the validity of the property below and verify that your implementations are correct by running the tests.
prop_alphaEq_renameBoundVar_freshId :: Term -> Bool
prop_alphaEq_renameBoundVar_freshId m = alphaEq (fromJust (renameBoundVar (Abs "x" m) (freshId (freeVars m) "x"))) (Abs "x" m)

-- prop> prop_alphaEq_renameBoundVar_freshId
-- +++ OK, passed 100 tests.

-- Alpha equivalence is reflexive
prop_alphaEq_refl :: Term -> Bool
prop_alphaEq_refl m = alphaEq m m

-- prop> prop_alphaEq_refl
-- +++ OK, passed 100 tests.

prop_alphaEq_var_app :: Id -> Term -> Term -> Bool
prop_alphaEq_var_app x m n = not $ alphaEq (Var x) (App m n)

-- prop> prop_alphaEq_var_app
-- +++ OK, passed 100 tests.

prop_alphaEq_app_abs :: Term -> Term -> Id -> Term -> Bool
prop_alphaEq_app_abs m n x m' = not $ alphaEq (App m n) (Abs x m')

-- prop> prop_alphaEq_app_abs
-- +++ OK, passed 100 tests.

prop_alphaEq_abs_var :: Id -> Term -> Id -> Bool
prop_alphaEq_abs_var x m y = not $ alphaEq (Abs x m) (Var y)

-- prop> prop_alphaEq_abs_var
-- +++ OK, passed 100 tests.

{-
Section 4: Beta Reduction, Evaluation Strategies and Derivations
----------------------------------------------------------------

Now that we have completed all the low-level heavy lifting, we can get down to the actual reduction.

-}


-- Exercise LC.4.1 (*)
-- The function `betaReduce t` performs beta reduction at the top-level (i.e., root) of the term `t`. It returns `Nothing` in case beta reduction cannot be performed at the topmost level of the input term.
-- Complete the definition of `betaReduce` below.
-- Hint: Use the function `applySubst` that you have just defined.

-- >>> betaReduce (Var "x") == Nothing
-- True

-- >>> betaReduce tI == Nothing
-- True

-- >>> betaReduce (App tI tI) == Just (tI)
-- True

-- >>> betaReduce (App (Var "y") (App tI tI)) == Nothing
-- True

betaReduce :: Term -> Maybe Term
betaReduce (App (Abs x m) n) = Just undefined
betaReduce _ = Nothing



-- Exercise LC.4.2 (**)
-- The function `containsBetaRedex t` returns `True` if and only if the term `t` contains at least one beta redex.
-- Complete the definition of `containsBetaRedex` below.
-- Hint: Use the function `betaReduce` that you have just defined.
-- Hint: Defining the following helper functions may be helpful:
--  isBetaRedex :: Term -> Bool
--  subTerms :: Term -> [Term] -- Note: The top-level term is also a sub term
--  anySubTerm :: (Term -> Bool) -> Term -> Bool

-- >>>  containsBetaRedex (Var "x") == False
-- True

-- >>> containsBetaRedex tI == False
-- True

-- >>> containsBetaRedex (App tI tI) == True
-- True

-- >>> containsBetaRedex (App (Var "y") (App tI tI)) == True
-- True

containsBetaRedex :: Term -> Bool
containsBetaRedex = undefined



-- Exercise LC.4.3 (*)
-- The function `isInBetaNormalForm t` returns `True` if and only if the term `t` is in beta-normal form.
-- Complete the definition of `isInBetaNormalForm` below, only using `containsBetaRedex`, `(.)`, and `not`.

isInBetaNormalForm :: Term -> Bool
isInBetaNormalForm = undefined


-- A reduction step can be thought of as a function from `Term` to `Maybe Term`, which returns `Nothing` if the reduction is not applicable, or `Just n` if `n` is the result of applying a single step of the reduction to the input term.
type ReductionStep = Term -> Maybe Term

-- Exercise LC.4.4 (*)
-- The function `leftmostOutermostStep m` implements a single reduction step using leftmost outermost reduction strategy on the term `m`.
-- Complete the definition of `leftmostOutermostStep` below.
-- Hint: Use the functions `containsBetaRedex` and `betaReduce` you have just defined.

-- >>> leftmostOutermostStep tI == Nothing
-- True

-- >>> leftmostOutermostStep (App tI tI) == Just tI
-- True

-- >>> leftmostOutermostStep (App tL tΩ) == Just (Abs "y" (Var "y"))
-- True

-- >>> leftmostOutermostStep (App (App tL tΩ) tI) == Just (App (Abs "y" (Var "y")) (Abs "x" (Var "x")))
-- True

leftmostOutermostStep :: ReductionStep
-- remember `ReductionStep = Term -> Maybe Term`
leftmostOutermostStep m = if containsBetaRedex m then Just (lo m) else Nothing
  where
    lo (Var x) = undefined
    lo (Abs x m) = undefined
    lo (App m n) = undefined




-- Lets now try to see what reductions look like in pretty printed syntax.

-- Note: The function `pretty`, when given an input of type `Maybe a`, just ignores `Nothing` and pretty prints the `x` in `Just x`. Source: https://hackage.haskell.org/package/prettyprinter-1.7.1/docs/Prettyprinter.html#v:pretty 

-- >>> pretty (Nothing:: Maybe Term)

-- >>> pretty $ leftmostOutermostStep tΩ
-- ((λx.(x x)) (λx.(x x)))

-- >>> pretty $ leftmostOutermostStep tωI
-- ((λx.x) (λx.x))

-- >>> pretty $ leftmostOutermostStep (fromJust (leftmostOutermostStep tωI))
-- (λx.x)

-- Exercise LC.4.5 (*)
-- The function `derivation step m` computes a derivation starting at term `m` and successively applying the reduction step `step` on the result of the previous reduction step until it cannot be applied any further. The function returns a (potentially infinite) list of all successive steps of the derivation starting with `m`.
-- Complete the definition of `derivation` below.

derivation :: ReductionStep -> Term -> [Term]
-- remember `ReductionStep = Term -> Maybe Term`
-- Note: `derivation` can also have the more general type `(t -> Maybe t) -> t -> [t]`
derivation step m = undefined



leftmostOutermostDerivation :: Term -> [Term]
leftmostOutermostDerivation = derivation leftmostOutermostStep

-- >>> pretty $ leftmostOutermostDerivation tωI
-- [((λx.(x x)) (λx.x)), ((λx.x) (λx.x)), (λx.x)]

-- >>> leftmostOutermostDerivation tωI == [App (Abs "x" (App (Var "x") (Var "x"))) (Abs "x" (Var "x")),App (Abs "x" (Var "x")) (Abs "x" (Var "x")),Abs "x" (Var "x")]
-- True

-- >>> pretty $ take 10 (leftmostOutermostDerivation tΩ)
-- [ ((λx.(x x)) (λx.(x x)))
-- , ((λx.(x x)) (λx.(x x)))
-- , ((λx.(x x)) (λx.(x x)))
-- , ((λx.(x x)) (λx.(x x)))
-- , ((λx.(x x)) (λx.(x x)))
-- , ((λx.(x x)) (λx.(x x)))
-- , ((λx.(x x)) (λx.(x x)))
-- , ((λx.(x x)) (λx.(x x)))
-- , ((λx.(x x)) (λx.(x x)))
-- , ((λx.(x x)) (λx.(x x))) ]

-- >>> take 10 (leftmostOutermostDerivation tΩ) == replicate 10 tΩ
-- True

alphaEqList :: [Term] -> [Term] -> Bool
alphaEqList = liftEq alphaEq
  where
    liftEq :: (a -> a -> Bool) -> ([a] -> [a] -> Bool)
    liftEq _ [] [] = True
    liftEq e (x : xs) (y : ys) = (x `e` y) && liftEq e xs ys
    liftEq _ _ _ = False

-- Alternative definition of alphaEqList
-- alphaEqList :: [Term] -> [Term] -> Bool
-- alphaEqList = liftEq alphaEq
--   where
--     liftEq :: (a -> a -> Bool) -> ([a] -> [a] -> Bool)
--     liftEq e xs ys = all (uncurry e) (zip xs ys) && length xs == length ys

prop_derivation_infinite :: Int -> Bool
prop_derivation_infinite n = take n (derivation leftmostOutermostStep tΩ) `alphaEqList` replicate n tΩ

-- prop> prop_derivation_infinite
-- +++ OK, passed 100 tests.

-- We can now define a function that computes the beta normal form of a given lambda term!

-- Exercise LC.4.6 (*)
-- The function `betaNormalForm m` returns the beta normal form of the term `m`.
-- Complete the definition of `betaNormalForm` below, only using the functions `derivation`, `leftmostOutermostStep`, `.`, and `last :: [a] -> a`.

betaNormalForm :: Term -> Term
betaNormalForm = undefined



-- `(λx.x) a` reduces to `a`
-- >>> betaNormalForm (App (Abs "x" (Var "x")) (Var "a")) == (Var "a")
-- True

-- `(λx.x x) (λx.x)` reduces to `λx.x`
-- >>> betaNormalForm (App (Abs "x" (App (Var "x") (Var "x"))) (Abs "x" (Var "x"))) == (Abs "x" (Var "x"))
-- True

-- `(λx.λy.x y) y` reduces to `λy'.y y'`, and not `λy.y y`
-- >>> betaNormalForm (App (Abs "x" (Abs "y" (App (Var "x") (Var "y")))) (Var "y")) == (Abs "y'" (App (Var "y") (Var "y'")))
-- True

-- Note: The above test case is too brittle since its success depends on the choice of the fresh variable `y'` chosen to avoid variable capture.
-- To make the test more robust, it would be better to check that the two terms are alpha equivalent instead.

-- `(λx.λy.x y) y` reduces to a tern that is alpha equivalent to `λz.y z`
-- >>> alphaEq (betaNormalForm (App (Abs "x" (Abs "y" (App (Var "x") (Var "y")))) (Var "y"))) (Abs "z" (App (Var "y") (Var "z")))
-- True

-- The terms that `betaNormalForm` returns do not contain any redexes.
-- Note: Since not all reductions terminate, this test may never terminate in cases where the random term generator just happens to generate a non-terminating term. But this is highly unlikely. In case this test does not terminate consistently, you probably have an error in your code.
prop_betaNormalForm_containsBetaRedex :: Term -> Bool
prop_betaNormalForm_containsBetaRedex t = 
  not $ containsBetaRedex (betaNormalForm t)

-- prop> prop_betaNormalForm_containsBetaRedex
-- +++ OK, passed 100 tests.

{-
Section 5: Additional features
------------------------------

You have completed the minimal required goal for this project and demonstrated your understanding of the lambda calculus and functional programming. For most of you, this may be your first implementation of a Turing-complete programming language. Congratulations! 

It is now your turn to develop this implementation further in a way that is of interest to you. 

Here are some ideas for further development: 
- Try out some more reductions using the interpreter, maybe use it to check your answers to the exercise sheets.  
- Implement additional evaluation strategies, for instance leftmost innermost.  
- Define some more interesting properties or unit tests and test them.  
- Try out other interesting ways of implementing the above operations.  
- Implement your own parser for lambda terms. (Note: I have given you a simple parser for lambda terms in the module `LambdaCalculus.Parsing`. Feel free to use it in case you need one for whatever reason)
- Implement your own pretty printer for lambda terms, maybe one with minimal parentheses).  
- Implement a user friendly command line interface, maybe one that gives the user a choice for which redex they want to reduce next.  
- Implement a graphical user interface.  

You may have already done some of these things while working on the exercises above. Please demonstrate your additional work to the person supervising your exercise session.

-}



-- **********************
-- * EXERCISE ENDS HERE *
-- *   YOU MADE IT !!   *
-- **********************



-- The following section contains generators for test data and demonstrations thereof. The generators are required for property-based testing and may be treated later in the course as an application of applicatives and monads.
-- Ideally, by convention this should go in a separate module called `LambdaCalculus.Gen`, but having it here makes it possible to run property-based tests from within this module.

instance Arbitrary Term where
  arbitrary :: Gen Term
  arbitrary = sized term
    where
      arbitraryId = (: []) <$> choose ('o', 'z') -- use a limited set to force aliasing
      term 0 = Var <$> arbitraryId
      term n =
        oneof
          [ Var <$> arbitraryId,
            Abs <$> arbitraryId <*> term (n -1),
            App <$> term (n `div` 2) <*> term (n `div` 2)
          ]


-- >>> generate (arbitrary :: Gen Term)
-- Abs "u" (Abs "r" (Abs "s" (Var "x")))


-- The following section contains the implementation of a pretty printer for lambda terms. See the following for more information:
-- https://homepages.inf.ed.ac.uk/wadler/papers/prettier/prettier.pdf 
-- https://hackage.haskell.org/package/prettyprinter
-- Ideally, by convention this should go in a separate module called `LambdaCalculus.Pretty`, but having it here makes it possible to use the pretty pretty printer by calling `pretty` from within this module.

instance Pretty Term where
  pretty :: Term -> Doc ann
  pretty (Var x)= pretty x
  pretty (Abs x m) = paren $ cat [cat [pretty "λ", pretty x, pretty "."], pretty m]
  pretty (App m n) = paren $ sep [pretty m, pretty n]

paren :: Doc ann -> Doc ann
paren d = cat [nest 2 (cat [pretty "(", d]), pretty ")"]


-- >>> pretty <$> generate (arbitrary :: Gen Term)
-- ((λw.y) (λy.(λt.(λw.(λu.(λs.(λv.(λz.u))))))))

-- >>> generate (arbitrary :: Gen Term)
-- Abs "u" (App (Abs "o" (Var "o")) (Var "r"))

