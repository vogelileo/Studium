{-# OPTIONS_GHC -Wno-incomplete-patterns #-}
{-# OPTIONS_GHC -Wno-unrecognised-pragmas #-}
{-# HLINT ignore "Use foldr" #-}

module Chapter_06 where


-- Exercise 6.2 (*)
-- Define a recursive function `sumdown :: Int -> Int` that returns the sum of the non-negative integers from a given non-negative integer to zero. For example, `sumdown 3` should return the result 3 + 2 + 1 + 0 = 6.

sumdown :: Int -> Int
sumdown = undefined




-- Exercise 6.3 (*)
-- Define the exponentiation operator `^` for non-negative integers using the same pattern of recursion as the multiplication operator `*`, and show how the expression `2 ^ 3` is evaluated using your definition.
-- Note: If you are trying this within ghc, please note that ^ is already defined in the Prelude. Use the operator ^# which is currently undefined instead.

(^#) ::  Int -> Int -> Int
-- ??? ^# ??? = ???
-- ??? ^# ??? = ???
(^#) = undefined

{-
2^3
= {???}
???
...
-}



-- Exercise 6.5 (**)
-- Using the recursive definitions given in chapter ”Recursive Functions”,
-- show how length [1,2,3], drop 3 [1,2,3,4,5], and init [1,2,3] are evaluated.

-- length :: [a] -> Int
-- length [] = 0
-- length (_ : xs) = 1 + length xs

-- drop :: Int -> [a] -> [a]
-- drop 0 xs = xs
-- drop _ [] = []
-- drop n (_ : xs) = drop (n - 1) xs

-- init :: [a] -> [a]
-- init [_] = []
-- init (x:xs) = x : init xs

-- No executable code required.
-- Enter your solution within the block comment below.

{-
length [1,2,3]
= {applying ???}
???
...

drop 3 [1,2,3,4,5]
= {applying ???}
???
...

init [1,2,3]
= {applying ???}
???
...

-}


-- Exercise 6.6 (**)
-- Without looking at the definitions from the standard prelude, define the following library functions on lists using recursion.
-- Note: If you are trying this within ghc, please note that these functions are already defined in the Prelude. Use fresh function names by prefixing each name with a `my` and changing the resulting name into camelcase, for example `and` becomes `myAnd`. The operators you define should be suffixed with a `#` (i.e., `!!` becomes `!!#`).
-- Note: Most of these functions are defined in the prelude using other library functions rather than using explicit recursion, and are generic functions rather than being specific to the type of lists.

-- (a) Decide if all logical values in a list are True: 
--     and :: [Bool] -> Bool


myAnd :: [Bool] -> Bool
-- myAnd [] = ??? 
-- myAnd (b:bs) = ???
myAnd = undefined



-- (b) Concatenate a list of lists:
--     concat :: [[a]] -> [a]

myConcat :: [[a]] -> [a]
-- myConcat [] = ???
-- myConcat (xs:xss) = ???
myConcat = undefined


-- (c) Produce a list with a non-negative number of identical elements: 
--     replicate :: Int -> a -> [a]

myReplicate :: Int -> a -> [a]
-- myReplicate 0 ??? = ???
-- myReplicate n ??? = ???
myReplicate = undefined 


-- (d) Select the nth element of a list: 
--     (!!) :: [a] -> Int -> a

(!!#) :: [a] -> Int -> a
-- ??? !!# ??? = ???
-- ??? !!# ??? = ???
(!!#) = undefined



-- (e) Decide if a value is an element of a list:
--     elem :: Eq a => a -> [a] -> Bool

myElem :: Eq a => a -> [a] -> Bool
-- myElem ??? = ???
-- myElem ??? = ??? 
myElem = undefined


-- Exercise 6.7 (**)
-- Define a recursive function `merge :: Ord a => [a] -> [a] -> [a]` that merges two sorted lists to give a single sorted list. For example:
--     > merge [2 ,5 ,6] [1 ,3 ,4] 
--     [1,2,3,4,5,6]
-- Note: your definition should not use other functions on sorted lists such as insert or isort, but should be defined using explicit recursion.

merge :: Ord a => [a] -> [a] -> [a]
-- merge ??? = ???
-- merge ??? = ???
-- merge ??? = ??? 
merge = undefined 


-- Exercise 6.8 (**)
-- Using `merge`, define a function `msort :: Ord a => [a] -> [a]` that implements merge sort, in which the empty list and singleton lists are already sorted, and any other list is sorted by merging together the two lists that result from sorting the two halves of the list separately.
-- Hint: first define a function `halve :: [a] -> ([a],[a])` that splits a list into two halves whose lengths differ by at most one.


halve :: [a] -> ([a], [a])
halve = undefined 

msort :: Ord a => [a] -> [a]
msort = undefined


