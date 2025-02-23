{-# LANGUAGE InstanceSigs #-}

module LambdaCalculus.UnorderedSet
  ( T
   , empty
   , insert
   , fromList
   , toList
   , member
   , union
   , delete
  ) where

{-
This is an alternative implementation of sets using lists. You may use this implementation as a drop-in replacement for binary search trees in case you suspect that your implementation has errors.

Remember to swap the implementations in both modules `src/LambdaCalculus/Internal.hs` and `test/LambdaCalculus/Internal_Spec.hs` by commenting and uncommenting the appropriate import lines.
-}

import Test.QuickCheck
import qualified Data.List (delete)

newtype T a = T {getList :: [a]} deriving (Show)

-- Invariant for the type `T a`: The list within the constructor `T` does not have any duplicates.
invariant :: Eq a => T a -> Bool
invariant (T xs) = hasNoDups xs
  where
    hasNoDups [] = True
    hasNoDups (x : xs) = notElem x xs && hasNoDups xs

member :: Eq a => a -> T a -> Bool
member e (T xs) = e `elem` xs

empty :: T a
empty = T []

insert :: Eq a => a -> T a -> T a
insert e (T xs) = if e `member` T xs then T xs else T (e : xs)

fromList :: Eq a => [a] -> T a
fromList = foldr insert empty

toList :: T a -> [a]
toList = getList

delete :: Eq a => a -> T a -> T a
delete e (T xs) = T (Data.List.delete e xs)

union :: Eq a => T a -> T a -> T a
union (T xs) (T ys) = foldr insert (T ys) xs

instance Eq a => Eq (T a) where
  (==) :: Eq a => T a -> T a -> Bool
  ta == tb = ta `subset` tb && tb `subset` ta

subset :: Eq a => T a -> T a -> Bool
(T xs) `subset` (T ys) = and [x `elem` ys | x <-xs]

instance Eq a => Semigroup (T a) where
  (<>) :: Eq a => T a -> T a -> T a
  (<>) = union

instance Eq a => Monoid (T a) where
  mempty :: Eq a => T a
  mempty = empty


instance (Arbitrary a, Eq a) => Arbitrary (T a) where
  arbitrary :: (Arbitrary a, Eq a) => Gen (T a)
  arbitrary = fromList <$> arbitrary

-- >>> generate (arbitrary :: Gen (T Int) )
-- T {getList = [24,0,-15,-18,28,9,-27,-16,30,11,-4,1,-17,-3,19,26,-22]}
