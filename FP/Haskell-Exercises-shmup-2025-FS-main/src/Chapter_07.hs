{-# OPTIONS_GHC -Wno-unrecognised-pragmas #-}

{-# HLINT ignore "Use map" #-}
{-# HLINT ignore "Redundant lambda" #-}
{-# HLINT ignore "Use uncurry" #-}
{-# HLINT ignore "Use curry" #-}
{-# HLINT ignore "Use any" #-}
{-# HLINT ignore "Use all" #-}

module Chapter_07 where

-- Exercise 7.1 (*)
-- Show how the list comprehension `[f x | x <- xs, p x]` cane b re-expressed using the higher-order functions `map` and `filter`.

e_7_1 :: (a -> b) -> (a -> Bool) -> [a] -> [b]
-- e_7_1 f p xs = ???map???filter???
e_7_1 f p xs = map f (filter p xs)

-- Exercise 7.2 (**)
-- Without looking at the definitions from the standard prelude, define the following higher-order library functions on lists. Try to use the higher-order functions `and`, `or`, `(.)`, `foldr` and `map` within your definitions if possible.
-- Note: in the prelude the first two of these functions are generic functions rather than being specific to the type of lists.
-- Note: If you are trying this within ghc, please note that these functions are already defined in the Prelude. Use fresh function names by prefixing each name with a `my` and changing the resulting name into camelcase, for example `all` becomes `myAll`.

-- (a) Decide if all elements of a list satisfy a predicate:
--     all :: (a -> Bool) -> [a] -> Bool

myAll :: (a -> Bool) -> [a] -> Bool
myAll p (x : xs) = p x && (myAll p xs)

myAllHigh p = foldr (\x n -> p x && n) True

-- >>> myAllHigh (<5) [1,2,3,4,5]
-- False

--     :: (a0_aUBR[tau:1] -> Bool) -> [a1_aUBT[tau:1]] -> t_aUBE[sk:1]

myAny :: (a -> Bool) -> [a] -> Bool
myAny p (x : xs) = p x || (myAll p xs)

myAnyHigh p = foldr (\x n -> p x || n) False

-- (c) Select elements from a list while they satisfy a predicate:
--     takeWhile :: (a -> Bool) -> [a] -> [a]

-- Hint: Try to first define `takeWhile` using recursion before attempting a definiton using `foldr`.

myTakeWhile :: (a -> Bool) -> [a] -> [a]
myTakeWhile p (x : xs)
  | p x = x : myTakeWhile p xs
  | otherwise = []

myTakeWhileHigh p = foldr (\x n -> if p x then x : n else []) []

-- >>> myTakeWhileHigh (< 5) [1, 2, 3, 6, 2, 4]
-- [1,2,3]

-- (d) Remove elements from a list while they satisfy a predicate:
--     dropWhile :: (a -> Bool) -> [a] -> [a]

{-
Note:
You may have noticed that already for takeWhile, it's difficult to come up with a definition that meaningfully uses one or several of the higher-order functions `and`, `or`, `(.)`, `foldr` or `map`.

For dropWhile, this is even more challenging. Thus, define it using recursion (and without these higher-order functions), similar to the exercises for Chapter 6.
-}

-- myDropWhile :: (a -> Bool) -> [a] -> [a]
-- myDropWhile p (x: xs)
--    | p x = []:myDropWhile p xs
--    | otherwise (x:xs)

myDropWhileHigh p = foldr (\x n -> if p x then n else x : n) []

-- >>> myDropWhileHigh (<3) [1,2,3,4,1]
-- [3,4]

-- Exercise 7.2.d.WithFoldRight (OPTIONAL, ****)
{-
A solution to 7.2 d) using foldr is possible, but a lot more complicated and therefore not a good idea. A parameter is needed to decide whether to continue dropping or not. Understanding how the following definition works is a challenge exercise:

myDropWhile f ls =
    foldr
    (\a r b -> if b && f a then r True else a:r False)
    (const [])
    ls
    True

Here is another simpler sultion from Mr.Florian Schlickenrieder (FS 2024):

myDropWhile p xs = foldr (\x rs -> if p x then tail rs else xs) xs xs

-}

-- Exercise 7.3 (**)
-- Redefine the functions map and filter using foldr.

myMap :: (a -> b) -> [a] -> [b]
-- myMap f = foldr ??? ???
myMap f = foldr (\x n -> f x : n) []

-- >>>myMap (+1) [1,2,3]
-- [2,3,4]

myFilter :: (a -> Bool) -> [a] -> [a]
-- myFilter f = foldr ??? ???
myFilter f = foldr (\x n -> if f x then x : n else n) []

-- >>> myFilter (<4) [1,6,3,4,5]
-- [1,3]

-- -- myCurry :: ???
-- myCurry f = foldr(\x n -> f x:n)[]

-- -- myUncurry :: ???
-- myUncurry f = undefined
