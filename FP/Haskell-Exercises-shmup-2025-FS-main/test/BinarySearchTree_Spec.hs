{-# LANGUAGE ScopedTypeVariables #-}
{-# LANGUAGE TypeApplications #-}

module BinarySearchTree_Spec where

import BinarySearchTree
import Test.Hspec
import Test.QuickCheck
import Test.Validity


prop_member_insert :: Ord a => a -> T a -> Bool
prop_member_insert e t = member e (insert e t)

spec :: Spec
spec = do

  describe "`empty`" $ do
    it "does not contain any elements" $
      property $ \(e::Int) -> not $ member e empty

  describe "`insert`" $ do
    it "works correctly on empty trees" $
      member 1 (insert 1 empty) `shouldBe` True
    it "works correctly on any tree" $
      property (prop_member_insert @Int)
    it "works correctly on any tree - inline version" $
      property $ \e t -> member (e :: Int) (insert e t)

  describe "`fromList`" $ do
    it "contains all elements in the given list" $
      property $ \(xs::[Int]) -> and [member x (fromList xs) | x <- xs]

  describe "`toList`" $ do
    it "contains all elements in the given tree" $
      property $ \(xs::[Int]) -> and [x `elem` toList(fromList xs) | x <- xs]

  describe "`(==)`" $ do
    it "does not depend on the order of element insertion" $
      property $ \(xs::[Int]) -> fromList xs == fromList (reverse xs)

  describe "`union`" $ do
    it "a tree obtained by merging two trees is equal to the tree that contains all the elements of the two trees" $
      property $ \(xs::[Int]) (ys::[Int]) -> union (fromList xs) (fromList ys) == fromList (xs ++ ys)

    describe "`mempty`" $ do
      it "behaves just like `empty`" $
        (mempty::T Int) == empty

    describe "`(<>)`" $ do
      it "behaves just like `union`" $
        property $ \(xt::T Int) (yt::T Int) -> union xt yt == (xt <> yt)
    
    describe "Type class instances of `T Int` satisfy their type class laws" $ do
      eqSpecOnArbitrary @(T Int)
      monoidSpecOnArbitrary @(T Int) -- subsumes `semigroup`



{-

-- Trying to find some interesting properties using quickspec
-- Note: Quickspec currently does not work with the current LTS 20.17. The last time I tried, LTS 18.28 worked with quickspec.

-- In case one wants to run quickspec, add the following to `stack.yaml`, and add `quickspec` as a dependency in `package.yaml`
{-
extra-deps: 
  - quickspec-2.1.5
  - spoon-0.3.1
  - twee-lib-2.2
-}

import QuickSpec

sig =
  [ con "member" (member :: Int -> T Int -> Bool),
    con "empty" (empty :: T Int),
    con "insert" (insert :: Int -> T Int -> T Int),
    con "fromList" (fromList :: [Int] -> T Int),
    -- con "toList" (toList :: T Int -> [Int]), -- Results in possibly invalid properties since there is not requirement for the list to be sorted
    con "union" (union :: T Int -> T Int -> T Int),
    -- con "subset" (subset :: T Int -> T Int -> Bool),
    -- con "delete" (delete :: Int -> T Int -> T Int),
    background
      [ con "True" (True :: Bool),
        con "False" (False :: Bool),
        con "[]" ([] :: [A]),
        con ":" ((:) :: A -> [A] -> [A]),
        con "++" ((++) :: [A] -> [A] -> [A])
      ],
    monoType (Proxy :: Proxy (T Int))
  ]

{-

Output from QuickSpec from ghci:

> stack ghci src/BinarySearchTree.hs 
...
*BinarySearchTree> quickSpec sig
== Functions ==
 True :: Bool
False :: Bool
   [] :: [a]
  (:) :: a -> [a] -> [a]
 (++) :: [a] -> [a] -> [a]

== Functions ==                    
  member :: Int -> T Int -> Bool
   empty :: T Int
  insert :: Int -> T Int -> T Int
fromList :: [Int] -> T Int
   union :: T Int -> T Int -> T Int

== Laws ==
  1. empty = fromList []           
  2. union x y = union y x        
  3. union x x = x
  4. member x empty = False       
  5. union x empty = x             
  6. fromList (xs ++ ys) = fromList (ys ++ xs)
  7. fromList (xs ++ xs) = fromList xs
  8. insert x (fromList xs) = fromList (x : xs)
  9. member x (insert x y)         
 10. insert x (insert y z) = insert y (insert x z)
 11. insert x (insert x y) = insert x y
 12. union x (insert y z) = insert y (union x z)
 13. union (union x y) z = union x (union y z)
 14. member x (insert y empty) = member y (insert x empty)
 15. union (fromList xs) (fromList ys) = fromList (xs ++ ys)

-}

-}
