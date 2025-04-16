{-# LANGUAGE InstanceSigs #-}

module Chapter_08 where

-- Exercise 8.1 (*)
-- In a similar manner to the function `add` for the data type `Nat` below, define a recursive multiplication function `mult :: Nat -> Nat -> Nat` for the recursive type of natural numbers.
-- Hint: make use of add in your definition.

data Nat = Zero | Succ Nat
  deriving (Eq, Show)

add :: Nat -> Nat -> Nat
add Zero n = n
add (Succ m) n = Succ (add m n)

mult :: Nat -> Nat -> Nat
mult Zero n = Zero
mult (Succ m) n = add n (mult m n)

-- >>> mult (Succ(Zero)) (Succ (Succ Zero))
-- Succ (Succ Zero)

-- Exercise 8.5 (**)
-- Given the type declaration for `Expr` below, define a higher-order function `folde` such that `folde f g` replaces each `Val` constructor in an expression by the function `f`, and each `Add` constructor by the function `g`.

-- Example 1: Evaluating an expression

-- Output: 5  (since it replaces `Val` with `id` (identity) and `Add` with `+`)

data Expr = Val Int | Add Expr Expr
  deriving (Show)

folde :: (Int -> a) -> (a -> a -> a) -> Expr -> a
folde f g (Val n) = f n
folde f g (Add x y) = g (folde f g x) (folde f g y)

-- >>> folde id (+) (Add (Add (Val 1) (Val 2)) (Val 3))
-- 6

-- Exercise 8.6 (**)
-- Using folde, define a function `eval :: Expr -> Int` that evaluates an expression to an integer value, and a function `size :: Expr -> Int` that calculates the number of values in an expression.

eval :: Expr -> Int
eval = folde id (+)

-- TODO use folde DONE
--- >>> eval (Val 5)
-- 5

--- >>> eval (Add (Val 2) (Val 3))
-- 5

--- >>>eval (Add (Val 1) (Add (Val 4) (Val 6)))
-- 11

-- we want to replace all Val node regardless of what it has as an value with 1
size :: Expr -> Int
size = folde (const 1) (+)

-- TODO use folde DONE

--- >>> size (Val 5)
-- 1

--- >>> size (Add (Val 2) (Val 3))
-- 2

-- Exercise 8.7 (**)
-- Provide instance definitions to make the `Expr` and `List` types an instance of the `Eq` type class.
-- Note that we consider an expr equal to another expr if and only if they are exactly equal (same structure and same content at the same places), not if just their results are equal. Therefore, 'Add (Val 1) (Val 2)' and 'Add (Val 2) (Val 1)' would not be equal.

instance Eq Expr where
  (Val n1) == (Val n2) = n1 == n2
  (Add x1 y1) == (Add x2 y2) = x1 == x2 && y1 == y2
  _ == _ = False

-- >>>Add (Val 1) (Val 2) == Add (Val 2) (Val 1)
-- False

-- >>>Add (Val 2) (Val 1) == Add (Val 2) (Val 1)
-- True

data List a = Nil | Cons a (List a)
  deriving (Show)

-- For every element a that is comparable, we can also compare a List of elements of that type
instance (Eq a) => Eq (List a) where
  (==) :: (Eq a) => List a -> List a -> Bool
  (==) Nil Nil = True
  (==) (Cons n1 m1) (Cons n2 m2) = n1 == n2 && m1 == m2
  (==) _ _ = False

-- >>> Cons 1 (Cons 2 Nil) == Cons 2 (Cons 1 Nil)
-- False

-- >>> Cons 1 (Cons 2 Nil) == Cons 1 Nil
-- False

-- >>> Cons 1 (Cons 2 Nil) == Cons 1 (Cons 2 Nil)
-- True

-- >>> Nil == Nil
-- True
