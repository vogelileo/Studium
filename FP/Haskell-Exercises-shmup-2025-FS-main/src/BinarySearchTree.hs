module BinarySearchTree
  ( T
   , empty
   , insert
   , fromList
   , toList
   , member
   , union
   , delete
  )
where

{-
This module merely imports and re-exports the publicly usable API of the Binary Search Tree data structure.

The implementation and exercises are located in the module `BinarySearchTree.Internal`.
-}

import BinarySearchTree.Internal
    ( T, empty, insert, fromList, toList, member, union, delete )

