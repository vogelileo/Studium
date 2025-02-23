module Chapter_16 where

-- Note: There are neither any tests nor automated tests for this chapter. It appears in this form in order to have a uniform format with respect to the previous chapters.


data Nat = Zero | Succ Nat

add :: Nat -> Nat -> Nat
add Zero m = m                      -- add_Zero
add (Succ n) m = Succ (add n m)     -- add_Succ

-- Exercise 16.1
-- Show that `add n (Succ m) == Succ (add n m)`, by induction on `n`.
-- Prove that this property is valid using a proof of the form as done in the lecture:
-- 1. You are only allowed to use the following properties to perform equational reasoning in your proof. Assume that the properties below are true for any type-correct instantiations of the variables quantified using the universal quantification `∀`:
--
-- ∀m.   add Zero m == m                      -- add_Zero
-- ∀n m. add (Succ n) m == Succ (add n m)     -- add_Succ
-- ∀x.   (x == x) == True                     -- (==)_refl
--
-- 2. When using the above properties, you are required to refer to them explicitly using the names as shown above. You are required to state and justify each step of the proof explicitly and only apply a single rule (equational rewriting or otherwise) at each step of the proof. In case you use equational reasoning, please underline the sub terms that you rewrite at each step. In case you use induction, please explicitly state the variable upon which you choose to perform induction.

-- Hint: Fist state the induction principle that follows from the definition of `Nat`, and then instantiate it to get the base case and the inductive case statements that are required to prove the statement.

-- Specify your answer within the following block comment
{-
???
-}




-- Exercise 16.2
-- Using the property `add n (Succ m) = Succ (add n m)`, together with `add n Zero = n`, show that addition is commutative (i.e. `add n m = add m n`) , by induction on `n`.
-- Prove that this property is valid using a proof of the form as done in the lecture:
-- 1. You are only allowed to use the following properties to perform equational reasoning in your proof. Assume that the properties below are true for any type-correct instantiations of the variables quantified using the universal quantification `∀`:
--
-- ∀m.   add Zero m == m                      -- add_Zero
-- ∀n m. add (Succ n) m == Succ (add n m)     -- add_Succ
-- ∀n.   add n Zero == n                      -- add_Zero_flipped
-- ∀n m. add n (Succ m) = Succ (add n m)      -- add_Ex_16.1
-- ∀x.   (x == x) == True                     -- (==)_refl
--
-- 2. When using the above properties, you are required to refer to them explicitly using the names as shown above. You are required to state and justify each step of the proof explicitly and only apply a single rule (equational rewriting or otherwise) at each step of the proof. In case you use equational reasoning, please underline the sub terms that you rewrite at each step. In case you use induction, please explicitly state the variable upon which you choose to perform induction.

-- Specify your answer within the following block comment
{-
???
-}

