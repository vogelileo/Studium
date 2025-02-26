{-# OPTIONS_GHC -Wno-unrecognised-pragmas #-}

{-# HLINT ignore "Eta reduce" #-}
module Chapter_03 where

-- Exercise 3.1
-- What are the types of the following values?

-- Note: Please replace the `???` and uncomment the definitions and your answers in order for them to be checked by the compiler.

e3_1_1 = ['a', 'b', 'c']

-- e3_1_1 :: [Char]

e3_1_2 = ('a', 'b', 'c')

-- e3_1_2 :: (Char)

e3_1_3 = [(False, 'O'), (True, '1')]

-- e3_1_3 :: [(Bool, Char)]

e3_1_4 = ([False, True], ['0', '1'])

-- e3_1_4 :: ([Bool],[Char])

e3_1_5 = [tail, init, reverse]

-- e3_1_5 :: [a->a]

-- Exercise 3.2
-- Write down definitions that have the following types; it does not matter what the definitions actually do as long as they are type correct. The type of your defined function may be more general than the types defined below.

-- Note: Please replace the `???` and uncomment the type declarations and your answers in order for them to be checked by the compiler.

-- bools :: [Bool]
bools = [True, False]

-- nums :: [[ Int ]]
nums = [[3], [4]]

-- add :: Num a => a -> a -> a -> a
add x y z = x + y + z

-- copy :: b -> (b, b)
copy b = (b, b)

-- apply :: (t1 -> t2) -> t1 -> t2
apply f x = f x

-- Exercise 3.3 (**)
-- What are the types of the following functions?

-- Hint: take care to include the necessary class constraints in the types if the functions are defined using overloaded operators.

-- Note: Please replace the `???` and uncomment your answers and the function definitions in order for them to be checked by the compiler.

second xs = head (tail xs)

-- second ::[a] -> a

swap (x, y) = (y, x)

-- swap :: (a,b) -> (b,a)

pair x y = (x, y)

-- pair :: a -> b -> (a,b)

double x = x * 2

-- double :: a=> a -> a

palindrome xs = reverse xs == xs

-- palindrome :: [a] -> Bool

twice f x = f (f x)

-- twice ::  ((t1 -> t2) -> t1 -> t2) -> t1 -> t2

-- Exercise 3.4 (*)
-- Check your answers to the preceding three questions using GHCi.
-- Remember: Use the `:t expr` or the more verbose `:type expr` command in ghci to query the type on the expression `expr`.
-- Note: Asking ghci to show the value of `e3_1_5` from the definition `e3_1_5 = [tail, init, reverse]`, will result in an error since it is not possible to obtain a string representation of functions.

-- Copy and paste your ghci session into the block comment below.
{-
Ok, one module loaded.
ghci> :t bools
bools :: [Bool]
ghci> :t e3_1_1
e3_1_1 :: [Char]
ghci> :t e3_1_2
e3_1_2 :: (Char, Char, Char)
ghci> :t e3_1_3
e3_1_3 :: [(Bool, Char)]
ghci> :t e3_1_4
e3_1_4 :: ([Bool], [Char])
ghci> :t e3_1_5
e3_1_5 :: [[a] -> [a]]
ghci> :t bools
bools :: [Bool]
ghci> :t nums
nums :: [[Integer]]
ghci> :t add
add :: Num a => a -> a -> a -> a
ghci> :t copy
copy :: b -> (b, b)
ghci> :t apply
apply :: (t1 -> t2) -> t1 -> t2
ghci> :t second
second :: [a] -> a
ghci> :t swap
swap :: (b, a) -> (a, b)
ghci> :t pair
pair :: a -> b -> (a, b)
ghci> :t double
double :: Num a => a -> a
ghci> :t palindrome
palindrome :: Eq a => [a] -> Bool
ghci> :t twice
twice :: (t -> t) -> t -> t
ghci>
-}

-- Exercise 3.5 (**)
-- Why is it not possible in general for function types to be instances of the `Eq` class? When is it possible?
-- Hint: Two functions are equal if they always return equal results for equal arguments. Would it be possible to define a function `equal :: (a->b) -> (a->b) -> Bool` in Haskell that determines whether two functions are equal?

-- Type your answer into the block comment below.
{-

diffrent inputs result in diffrent output, if they would be same we need to check every input
-}
