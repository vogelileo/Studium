{-# LANGUAGE ScopedTypeVariables #-}
{-# LANGUAGE TypeApplications #-}
{-# OPTIONS_GHC -Wno-unrecognised-pragmas #-}
{-# HLINT ignore "Redundant $" #-}

module BinarySearchTree.Internal_Spec where

import BinarySearchTree.Internal
import Test.Hspec
import Test.QuickCheck
import qualified BinarySearchTree_Spec

propsFromInternalModule :: [(String, Property)]
propsFromInternalModule =
  -- Note: The call to `property` is needed here and cannot be fmapped in later since the values `prop_*` have different types.
  [ ("prop_valid_empty",        property $ prop_valid_empty)
  , ("prop_valid_insert",       property $ prop_valid_insert @Int)
  , ("prop_valid_fromList",     property $ prop_valid_fromList @Int)
  , ("prop_member_empty",       property $ prop_member_empty @Int)
  , ("prop_member_insert",      property $ prop_member_insert @Int)
  , ("prop_member_fromList",    property $ prop_member_fromList @Int)
  , ("prop_valid_union",        property $ prop_valid_union @Int)
  , ("prop_member_union",       property $ prop_member_union @Int)
  , ("prop_valid_delete",       property $ prop_valid_delete @Int)
  , ("prop_member_delete",      property $ prop_member_delete @Int)
  -- , ("",  @Int)
  ]

spec :: Spec
spec = let 
    t_invalid_1 = Node (Node Leaf 3 Leaf) 2 (Node Leaf 4 Leaf)
    in do

  describe "`valid`" $ do
    it "returns `True` for a valid BST" $
      valid (Node (Node Leaf 2 Leaf) 3 (Node Leaf 4 Leaf)) `shouldBe` True
    it "returns `False` for an invalid BST" $
      valid (Node (Node Leaf 3 Leaf) 2 (Node Leaf 4 Leaf)) `shouldBe` False
    it "returns `False` for an invalid BST" $
      valid (Node (Node Leaf 1 (Node Leaf 3 Leaf)) 2 Leaf) `shouldBe` False
    it "returns `False` for an invalid BST (duplicates)" $
      valid (Node (Node Leaf 2 Leaf) 2 (Node Leaf 2 Leaf)) `shouldBe` False
    it "returns `False` for an invalid BST (right subtree invalid)" $
      valid (Node t_invalid_1 5 Leaf) `shouldBe` False
    it "returns `False` for an invalid BST (right subtree invalid)" $
      valid (Node Leaf 1 t_invalid_1) `shouldBe` False

  describe "Properties imported from module `BinarySearchTree.Internal`" $ 
    sequence_ [it d p | (d,p) <- propsFromInternalModule]

  describe "BinarySearchTree public API" BinarySearchTree_Spec.spec
