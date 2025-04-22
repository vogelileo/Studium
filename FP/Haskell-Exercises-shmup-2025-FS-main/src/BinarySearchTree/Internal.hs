{-# LANGUAGE InstanceSigs #-}
{-# LANGUAGE TypeApplications #-}

{-
The aim of this exercise is to complete the implementation of a binary search tree (BST) data structure as described in the lecture and test its basic functionality using property-based testing.

Note: The time required to solve this exercise can vary depending on your interest, how far you want to go with the solutions, and how much fun you decide to have. I suggest you start with simple direct solutions that you can improve (i.e., make simpler and more efficient) over time.

Learning Goals:
- Using modules for code organization and information hiding
- Expressing representational invariants as properties
- Implementing functions that satisfy invariants (using recursion as well as non-recursive definitions)
- Expressing correctness properties for functions and using them for property-based testing.
- Revision and application of all previous lesson goals, in particular:
  - Recursion
  - Specification and testing of correctness properties
  - List comprehension

Note: You will use the implementation you come up with here within the "lambda calculus" exercise that follows.

Note: A general problem with testing is that most functions within an API can only be tested with respect to others in the same API. Therefore, running `stack test` before completing all the exercises in this module may give you unexpected results, since the tests for one function may depend on another function that is not yet implemented. It is therefore recommended to use the tests stated as eval comments within each exercises in this module while solving each exercise, and only running `stack test` after you have completed all exercises. It may also be the case, that a test stated as an eval comment fails due to an error in a function that you defined earlier and thought was correct since the tests you tried at that time all passed. In such cases, it helps to take a closer look at the statement of the property that is being tested to make sure that you are looking for faults in the right places.

-}

module BinarySearchTree.Internal where

{-
Note that this module contains the internal representation of the BST data structure, hence its name `BinarySearchTree.Internal`. By convention, the entire contents of `BinarySearchTree.Internal` may be imported for testing, but it is only intended to be used via its public interface in the module `BinarySearchTree`.
-}

import Test.QuickCheck

-- The underlying tree representation for BSTs
data T a = Leaf | Node (T a) a (T a)
  deriving (Show)

-- Note: The derived implementation of `show` will reveal the internal representation of a BST. This is tolerated since the output of the `show` method is to be used for debugging and test output, where the internal representation is relevant.

-- >>> t = Node (Node Leaf 3 Leaf) 2 (Node Leaf 4 Leaf)
-- >>> t
-- Node (Node Leaf 3 Leaf) 2 (Node Leaf 4 Leaf)

-- Exercise BST.1 (**)
-- Not every tree that can be represented using the data type definition for `T` above is a valid binary search tree. Define the function `valid` that returns `True` if and only if the given value of type `T` is a valid binary search tree. This property is also referred to as the invariant of the BST representation and must hold for any BSTs visible outside this module.
-- Hint: Define `valid` recursively via pattern matching. Look at the graphical version of the invariant in the lecture slides. Define a helper function `allT :: (a -> Bool) -> T a -> Bool` and use it in the recursive case of your definition. The function `allT p t` returns `True` if and only if `p x` returns `True` for all members `x` of the tree `t`.

-- >>> t_valid = Node (Node Leaf 2 Leaf) 3 (Node Leaf 4 Leaf)
-- >>> t_invalid_1 = Node (Node Leaf 3 Leaf) 2 (Node Leaf 4 Leaf)
-- >>> t_invalid_2 = Node (Node Leaf 1 (Node Leaf 3 Leaf)) 2 Leaf
-- >>> t_invalid_3 = Node (Node Leaf 2 Leaf) 2 (Node Leaf 2 Leaf)
-- >>> t_invalid_4 = Node t_invalid_1 5 Leaf
-- >>> t_invalid_5 = Node Leaf 1 t_invalid_1
-- >>> valid t_valid
-- >>> not $ valid t_invalid_1
-- >>> not $ valid t_invalid_2
-- >>> not $ valid t_invalid_3
-- >>> not $ valid t_invalid_4
-- >>> not $ valid t_invalid_5
-- True
-- False
-- False
-- False
-- False
-- False

valid :: (Ord a) => T a -> Bool
valid Leaf = True
valid (Node ltt x gtt) = valid ltt && valid gtt && allT (< x) ltt && allT (> x) gtt
  where
    allT :: (a -> Bool) -> T a -> Bool
    allT _ Leaf = True
    allT p (Node l y r) = p y && allT p l && allT p r

-- Hint: Read `ltt` as "less than tree" and `gtt` as "greater than tree"
-- TODO: check if ltt.x is smaller than x and gtt.x is greater than x

-- Exercise BST.2 (*)
-- Complete the definitions of the functions `empty` and `insert`. These should be the only functions that construct values of type `T`. All other functions that construct values of type `T` do so through these functions. Therefore, if `empty` and `insert` return valid BSTs, we are ensured that all other functions do so as well.

empty :: T a
empty = Leaf

insert :: (Ord a) => a -> T a -> T a
insert e Leaf = Node Leaf e Leaf
insert e (Node ltt p gtt)
  | e < p = Node (insert e ltt) p gtt
  | e > p = Node ltt p (insert e gtt)
  | otherwise = Node ltt p gtt

-- wenn e < p -> insert e ltt
-- wenn e > p -> insert e gtt
-- wenn e == p -> do nothing

-- Exercise BST.3 (*)
-- Use `foldr` to define `fromList` only using `empty` and `insert`.

fromList :: (Ord a) => [a] -> T a
fromList = foldr insert empty

-- Exercise BST.4 (*)
-- Property-based testing can be used to test whether the functions you just defined only return valid BSTs. Examine the properties below, convince yourself of their meaning and validity, and verify that your implementations are correct by running the tests.

-- The function `empty` only returns valid BSTs
prop_valid_empty :: Bool
prop_valid_empty = valid (empty :: T Int)

-- Note: The type `T Int` for `empty` needs to be given explicitly since `valid` needs to know which instance of `Ord` it should use.

-- prop> prop_valid_empty
-- +++ OK, passed 1 test.

{-
Note: In case you do not have the HLS eval plugin, you may also execute property-based tests from within ghci as follows:

    $ stack ghci src/BinarySearchTree/Internal.hs
    ...
    ghci> quickCheck prop_valid_insert
    +++ OK, passed 100 tests.
-}

-- The function `insert` only returns valid BSTs
prop_valid_insert :: (Ord a) => a -> T a -> Bool
prop_valid_insert e t = valid (insert e t)

-- Note: The random value generator for BSTs used for property-based testing generates only valid BSTs.

-- Note: The property above is polymorphic. In order to generate random values for a property-based test, one needs to specify a concrete type to generate random values for. The `@Int` in the invocation below instantiates the first type variable `a` in the property, to `Int`. This is important since otherwise the type of `a` used will default to `()`, which only has one element `()`.
-- Refer to https://ghc.gitlab.haskell.org/ghc/doc/users_guide/exts/type_applications.html for more information on type application.

-- >>> :t prop_valid_insert
-- prop_valid_insert :: Ord a => a -> T a -> Bool

-- >>> :t prop_valid_insert @Int
-- prop_valid_insert @Int :: Int -> T Int -> Bool

-- prop> prop_valid_insert @Int
-- +++ OK, passed 100 tests.

-- prop> prop_valid_insert
-- +++ OK, passed 100 tests.

-- The function `fromList` only returns valid BSTs
prop_valid_fromList :: (Ord a) => [a] -> Bool
prop_valid_fromList xs = valid $ fromList xs

-- prop> prop_valid_fromList @Int
-- +++ OK, passed 100 tests.

-- Exercise BST.5 (*)
-- Complete the definitions of the functions `toList` and `member`.
-- The function `toList` should return the elements of the BST in increasing order. For valid BSTs, this would correspond to an in-order tree traversal.
-- The function `member` returns `True` if and only if a given element occurs in a given BST. Use the BST invariant to arrive at an implementation that is more efficient than searching through a list.
-- After you are done with the implementations, examine the properties that follow, convince yourself of their meaning and validity and verify that your implementations are correct by running the tests.

toList :: T a -> [a]
toList Leaf = []
toList (Node ltt x gtt) = toList ltt ++ [x] ++ toList gtt

-- >>>toList Leaf
-- []

-- >>> toList (Node (Node Leaf 1 Leaf) 2 (Node Leaf 3 Leaf))
-- [1,2,3]

member :: (Ord a) => a -> T a -> Bool
member x Leaf = False
member x (Node l y r)
  | x < y = member x l
  | x > y = member x r
  | x == y = True

-- >>> member 4 (Node (Node Leaf 3 (Node Leaf 4 Leaf)) 5 (Node (Node Leaf 6 Leaf) 7 (Node Leaf 8 Leaf)))
-- True

-- >>> member 1 (Node (Node Leaf 3 (Node Leaf 4 Leaf)) 5 (Node (Node Leaf 6 Leaf) 7 (Node Leaf 8 Leaf)))
-- False

-- Nothing is a member of an empty BST.
prop_member_empty :: (Ord a) => a -> Bool
prop_member_empty e = not (member e empty)

-- prop> prop_member_empty @Int
-- +++ OK, passed 100 tests.

-- Inserting an element into a BST makes it a member of the BST.
prop_member_insert :: (Ord a) => a -> T a -> Bool
prop_member_insert e t = member e (insert e t)

-- prop> prop_member_insert @Int
-- +++ OK, passed 100 tests.

prop_member_fromList :: (Ord a) => [a] -> Bool
prop_member_fromList xs = and [member x (fromList xs) | x <- xs]

-- prop> prop_member_fromList @Int
-- +++ OK, passed 100 tests.

-- Exercise BST.6 (*)
-- Complete the definition of the function `union`.
-- The function `union` should return a BST that contains all the elements of the two input BSTs.
-- Hint: You may use the list representation of one or both of the input BSTs to be in your implementation.
-- After you are done with the implementation, examine the properties that follow, convince yourself of their meaning and validity and verify that your implementations are correct by running the tests.
-- Optional Challenge (*??): Would it be possible to define `union` directly via recursion and pattern matching, without going via the list representation or needing to go through all elements? (Disclaimer: I currently do not have a solution for this, nor am I certain whether this has a good solution. Please get in touch in case you find one.)

union :: (Ord a) => T a -> T a -> T a
union ta tb = foldr insert tb (toList ta)

prop_valid_union :: (Ord a) => T a -> T a -> Bool
prop_valid_union ta tb = valid $ union ta tb

-- prop> prop_valid_union @Int
-- +++ OK, passed 100 tests.

prop_member_union :: (Ord a) => a -> T a -> T a -> Bool
prop_member_union e ta tb =
  (e `member` ta || e `member` tb) == (e `member` (ta `union` tb))

-- prop> prop_member_union @Int
-- +++ OK, passed 100 tests.

-- Exercise BST.7 (*)
-- Complete the definition of the function `delete`.
-- The function `delete e t` should return a BST that contains all the elements of `t` except `e`.
-- Hint: You may use the list representation of the input BST in your implementation.
-- Optional Challenge (***): It is possible to delete an element from a BST directly, without going via its list representation. [Wikipedia](https://en.wikipedia.org/wiki/Binary_search_tree#Deletion) gives an idea of how this could be done, but its description, being imperative, is too complicated. Come up with a recursive definition of `delete` that deletes an element from a BST without first converting it to a list.

delete :: (Ord a) => a -> T a -> T a
delete e t = fromList (filter (/= e) (toList t))

prop_valid_delete :: (Ord a) => a -> T a -> Bool
prop_valid_delete x t = valid $ delete x t

-- prop> prop_valid_delete
-- +++ OK, passed 100 tests.

prop_member_delete :: (Ord a) => a -> T a -> Bool
prop_member_delete x t = not (member x (delete x t))

-- prop> prop_member_delete @Int
-- +++ OK, passed 100 tests.

-- Exercise BST.8 (*)
-- Make the type T a member of the type classes `Eq`.
-- Make sure that your implementation satisfies all type class laws for the class you are implementing.
-- Refer to https://hackage.haskell.org/package/base-4.15.0.0/docs/Prelude.html#t:Eq for more information on `Eq`.
-- Note that the BST implementation of `==` should be for set equality, and not structural equality based on the BST tree structure. Two sets are equal if and only if they contain the same elements.
-- Hint: You may use `toList` in your implementation.

instance (Ord a) => Eq (T a) where
  (==) :: (Ord a) => T a -> T a -> Bool
  t1 == t2 = toList t1 == toList t2

-- Exercise BST.9 (*)
-- Make the type T a member of the type classes `Semigroup` and `Monoid`.
-- Make sure that your implementation satisfies all type class laws for the class you are implementing.
-- Refer to https://hackage.haskell.org/package/base-4.15.0.0/docs/Prelude.html#g:9 and https://hackage.haskell.org/package/base-4.15.0.0/docs/Prelude.html#t:Monoid for more information on `Semigroup` and `Monoid`.

instance (Ord a) => Semigroup (T a) where
  (<>) :: (Ord a) => T a -> T a -> T a
  (<>) = union

instance (Ord a) => Monoid (T a) where
  mempty :: (Ord a) => T a
  mempty = Leaf

-- Exercise BST.10 (*)
-- Think of, implement, and test some other interesting functions and properties for the BST data type.
-- Hint: You may use https://hackage.haskell.org/package/containers-0.6.7/docs/Data-Set.html as inspiration.

-- * EXERCISE ENDS HERE *

-- *   YOU MADE IT !!   *

-- The remainder of this file contains generators for test data and demonstrations thereof. The generators are required for property-based testing and may be treated later in the course as an application of applicatives and monads.
-- Ideally, by convention this should go in a separate module called `BinarySearchTree.Gen`, but having it here makes it possible to run property-based tests from within this module.

-- Definition of `arbitrary` in the applicative style
instance (Arbitrary a, Ord a) => Arbitrary (T a) where
  arbitrary :: (Arbitrary a, Ord a) => Gen (T a)
  arbitrary = fL <$> arbitrary
    where
      -- Note `fL` used here to be independent of the implementation of `fromList`
      fL :: (Ord a) => [a] -> T a
      fL = foldr arbInsert arbEmpty
      -- Note `arbInsert` and `arbEmpty` used here to be independent of the implementation of `insert` and `empty` above
      arbEmpty = Leaf
      arbInsert e Leaf = Node Leaf e Leaf
      arbInsert e (Node ltt p gtt)
        | e == p = Node ltt p gtt
        | e < p = Node (arbInsert e ltt) p gtt
        | otherwise = Node ltt p (arbInsert e gtt)

-- -- Alternative equivalent definition of `arbitrary` in the monadic style
-- instance (Arbitrary a, Ord a) => Arbitrary (T a) where
--   arbitrary = do
--     xs <- arbitrary
--     return (fL xs) where
--     -- Note `fL` used here to be independent of the implementation of `fromList`
--     fL :: Ord a => [a] -> T a
--     fL = foldr insert empty

-- >>> generate (arbitrary :: Gen [Int])
-- [-30,-11,6,-23,-13,13,8,28,12,-7]

-- >>> generate (arbitrary :: Gen (T Int) )
-- Node (Node (Node (Node (Node Leaf (-23) Leaf) (-16) Leaf) (-15) (Node Leaf (-14) Leaf)) (-1) (Node Leaf 11 Leaf)) 12 (Node (Node Leaf 21 Leaf) 30 Leaf)

-- Here is an alternative generator for illustration proposes that returns trees with a limited range of elements.
arbitraryLimited :: Gen (T Int)
arbitraryLimited = sized tree
  where
    tree :: Int -> Gen (T Int)
    tree 0 = pure empty
    tree n
      | n > 0 = insert <$> choose (1, 4) <*> oneof [pure empty, tree (n - 1)]
      | otherwise = undefined -- This should never happen. Added to make case distinction exhaustive

-- >>> generate arbitraryLimited
-- Node Leaf 2 (Node Leaf 4 Leaf)

-- | A trivial generator that always returns an empty tree.
-- Just for illustration purposes.
trivialArbitraryTree :: Gen (T Int)
trivialArbitraryTree = pure empty

-- >>> generate trivialArbitraryTree
-- Leaf

-- >>> generate (arbitrary :: Gen (T Int))
-- Node (Node (Node Leaf (-29) Leaf) (-28) (Node Leaf (-24) Leaf)) (-22) (Node (Node (Node Leaf (-15) Leaf) (-13) (Node (Node (Node (Node Leaf (-10) Leaf) (-7) (Node Leaf (-1) Leaf)) 4 (Node Leaf 9 Leaf)) 10 (Node Leaf 11 (Node Leaf 14 Leaf)))) 15 (Node (Node Leaf 21 Leaf) 28 Leaf))

-- >> verboseCheck prop_member_insert
