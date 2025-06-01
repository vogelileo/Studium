{-# LANGUAGE DeriveGeneric #-}
{-# LANGUAGE InstanceSigs #-}
{-# OPTIONS_GHC -Wno-unrecognised-pragmas #-}

{-# HLINT ignore "Eta reduce" #-}

module Chapter_12 where

import GHC.Generics
import Prelude hiding (Just, Maybe, Nothing)

-- Exercise 12.WarmUp1
-- Consider following type:

data Maybe a = Nothing | Just a
  deriving (Eq, Ord, Show, Read)

-- Provide instance definitions to make this type into instances of the `Functor`, `Applicative` and `Monad` classes.

-- Here are some examples of how these instance definitions can be used:

-- >>> fmap (+1) Nothing
-- Nothing

-- >>> fmap (+1) (Just 1)
-- Just 2

-- >>> pure (+1) <*> Just 1
-- Just 2

-- >>> pure (+) <*> Just 1 <*> Just 3
-- Just 4

-- >>> pure (+) <*> Nothing <*> Just 2
-- Nothing

-- >>> safediv n m = if (m == 0) then Nothing else (Just (div n m))
-- >>> do {n <- pure 10; m <- pure 2; safediv n m}
-- Just 5

-- >>> safediv n m = if (m == 0) then Nothing else (Just (div n m))
-- >>> do {n <- pure 10; m <- pure 0; safediv n m}
-- Nothing

instance Functor Maybe where
  -- fmap :: ???
  fmap f x = case x of
    Nothing -> Nothing
    Just a -> Just (f a)

instance Applicative Maybe where
  -- pure :: ???
  pure :: a -> Maybe a
  pure x = Just x

  -- (<*>) :: ???
  (<*>) :: Maybe (a -> b) -> Maybe a -> Maybe b
  (<*>) _ Nothing = Nothing
  (<*>) Nothing _ = Nothing
  (<*>) (Just f) (Just x) = Just (f x)

instance Monad Maybe where
  -- (>>=) :: ???
  (>>=) :: Maybe a -> (a -> Maybe b) -> Maybe b
  (>>=) Nothing _ = Nothing
  (>>=) (Just x) f = f x

-- Exercise 12.1 (*)
-- Define an instance of the `Functor` class for the following type of binary trees that have data in their nodes:

data Tree a = Leaf | Node (Tree a) a (Tree a)
  deriving (Read, Show, Eq, Generic)

instance Functor Tree where
  -- fmap :: ???
  fmap _ Leaf = Leaf
  fmap f (Node l x r) = Node (fmap f l) (f x) (fmap f r)

-- Note: Just in case you are wondering, this type of tree does not have an appropriate instance of the `Applicative` or `Monad` class. Take a look at https://dkalemis.wordpress.com/2014/03/22/trees-as-monads/ for an explanation.
