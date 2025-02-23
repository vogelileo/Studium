{-# LANGUAGE ScopedTypeVariables #-}
{-# LANGUAGE InstanceSigs #-}
{-# LANGUAGE TypeApplications #-}

module Chapter_08_Spec where

import Chapter_08
import Test.Hspec
import Test.QuickCheck
import Test.Validity

nat2int :: Nat -> Int
nat2int Zero = 0
nat2int (Succ n) = 1 + nat2int n

int2nat :: Int -> Nat
int2nat 0 = Zero
int2nat n = Succ (int2nat (n-1))

instance Arbitrary Nat where
    arbitrary :: Gen Nat
    arbitrary = int2nat . abs <$> arbitrary

-- >>> :t int2nat <$> abs <$> arbitrary
-- int2nat <$> abs <$> arbitrary :: Gen Nat

-- >>> sample' (arbitrary :: Gen Nat)
-- [Zero,Succ Zero,Succ Zero,Zero,Succ (Succ (Succ (Succ (Succ Zero)))),Succ (Succ Zero),Succ Zero,Succ (Succ (Succ (Succ (Succ (Succ Zero))))),Succ (Succ (Succ (Succ (Succ (Succ (Succ (Succ (Succ (Succ (Succ (Succ Zero))))))))))),Succ (Succ (Succ Zero)),Succ (Succ (Succ (Succ (Succ (Succ (Succ (Succ (Succ (Succ (Succ (Succ Zero)))))))))))]

instance Arbitrary Expr where
    arbitrary :: Gen Expr
    arbitrary = sized tree
      where
        tree 0 = Val <$> arbitrary
        tree n | n > 0 = Add <$> (subtree n) <*> (subtree n)
        subtree n = oneof [Val <$> arbitrary, tree (n `div` 2)]

-- >>> sample' (arbitrary :: Gen Expr)
-- [Val 0,Add (Val 0) (Add (Val (-2)) (Val 1)),Add (Val 1) (Val 1),Add (Val 0) (Add (Val 5) (Add (Val 3) (Val 2))),Add (Val 0) (Val 3),Add (Add (Add (Val 7) (Add (Val 9) (Val 9))) (Add (Val (-9)) (Add (Val 2) (Val (-1))))) (Val (-6)),Add (Val 2) (Add (Add (Val 8) (Val 5)) (Val 8)),Add (Add (Val 11) (Add (Add (Val 6) (Val (-6))) (Val (-13)))) (Add (Val 7) (Val 2)),Add (Val 7) (Val 7),Add (Add (Add (Val 18) (Val 17)) (Val (-14))) (Val 12),Add (Val (-14)) (Val (-6))]

-- The test for `folde` in exercise 8.5 requires a correct implementation of  `(=)` which is the solution of exercise 8.7. The test for exercise 8.5 therefore requires the correct solution of exercise 8.7. In order for exercise 8.5 to be tested independently of exercise 8.7, the following alternative definition of equality is used.
localEquals :: Expr -> Expr -> Bool
localEquals (Val i) (Val j) = i == j
localEquals (Add e11 e12) (Add e21 e22) = e11 `localEquals` e21 && e12 `localEquals` e22 
localEquals _ _ = False 

instance Arbitrary a => Arbitrary (List a) where
    arbitrary :: Gen (List a)
    arbitrary = fromHaskellList <$> arbitrary 
        where
            fromHaskellList [] = Nil
            fromHaskellList (x:xs) = Cons x (fromHaskellList xs)



spec :: Spec
spec = do

    describe "Exercise 8.1" $ do
        describe "mult" $ do
            it "behaves just like (*)" $
                property $ \ n m -> mult n m == int2nat (nat2int n * nat2int m)

    describe "Exercise 8.5" $ do
        describe "folde" $ do
            it "Returns an identical expresion if given the constructors" $
                property $ \ e -> folde Val Add e `localEquals` e

    describe "Exercise 8.6" $ do
        describe "eval" $ do
            it "correctly evaluates 10" $
                eval (Val 10) `shouldBe` 10
            it "correctly evaluates 1+1" $
                eval (Add (Val 1) (Val 1)) `shouldBe` 2
        describe "size" $ do
            it "correctly calculates the size of 10" $
                size (Val 10) `shouldBe` 1
            it "correctly calculates the size of 1+1" $
                size (Add (Val 1) (Val 1)) `shouldBe` 2

    describe "Exercise 8.7" $ do
        describe "(==) for Expr" $ do
            it "is reflexive" $
                property $ \ (e::Expr) -> e == e
            it "is not true for the expressions representing 10 and 5+5" $
                Add (Val 5) (Val 5) == Val 10 `shouldBe` False
            it "is false for different `Val`s" $
                Val 5 == Val 23 `shouldBe` False
            it "compares `Val`s by value" $
                property $ \ n m -> Val n == Val m `shouldBe` n == m
            it "is false for `Add`s with swapped distinct operands" $
                Add (Val 2) (Val 3) == Add (Val 3) (Val 2) `shouldBe` False
            it "compares `Add`s by their operands" $
                property $ \ a b x y -> Add a b == Add x y `shouldBe` a `localEquals` x && b `localEquals` y
            it "behaves as one would expect" $
                property $ \ (e1::Expr) e2 -> e1 == e2 `shouldBe` (e1 `localEquals` e2)
            eqSpecOnArbitrary @Expr
        describe "(==) for List" $ do
            it "is reflexive" $
                property $ \ (e::List Int) -> e == e
            it "is not true for the lists Nil and Cons 1 Nil" $
                Cons 1 Nil == Nil `shouldBe` False
            it "is not true for the lists Cons 1 Nil and Cons 1 (Cons 2 Nil)" $
                Cons 1 Nil == Cons 1 (Cons 2 Nil) `shouldBe` False
            eqSpecOnArbitrary @(List Int)

