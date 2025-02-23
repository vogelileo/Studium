{-# OPTIONS_GHC -Wno-unrecognised-pragmas #-}
{-# HLINT ignore "Eta reduce" #-}
module Chapter_03 where

-- Exercise 3.1
-- What are the types of the following values?

-- Note: Please replace the `???` and uncomment the definitions and your answers in order for them to be checked by the compiler.

-- e3_1_1 = ['a','b','c']
-- e3_1_1 :: ???

-- e3_1_2 = ('a','b','c')
-- e3_1_2 :: ???

-- e3_1_3 = [(False,'O'),(True,'1')]
-- e3_1_3 :: ???

-- e3_1_4 = ([False,True],['0','1'])
-- e3_1_4 :: ???

-- e3_1_5 = [tail, init, reverse]
-- e3_1_5 :: ???


-- Exercise 3.2
-- Write down definitions that have the following types; it does not matter what the definitions actually do as long as they are type correct. The type of your defined function may be more general than the types defined below.

-- Note: Please replace the `???` and uncomment the type declarations and your answers in order for them to be checked by the compiler.

-- bools :: [Bool]
-- bools = ???

-- nums :: [[ Int ]]
-- nums = ???

-- add :: Num a => a -> a -> a -> a
-- add ??? = ???

-- copy :: b -> (b, b)
-- copy ??? = ???

-- apply :: (t1 -> t2) -> t1 -> t2
-- apply ??? = ???


-- Exercise 3.3 (**)
-- What are the types of the following functions?

-- Hint: take care to include the necessary class constraints in the types if the functions are defined using overloaded operators.

-- Note: Please replace the `???` and uncomment your answers and the function definitions in order for them to be checked by the compiler.

-- second xs = head (tail xs) 
-- second :: ???

-- swap (x,y) = (y,x)
-- swap :: ???

-- pair x y = (x,y)
-- pair :: ???

-- double x = x*2
-- double :: ???

-- palindrome xs = reverse xs == xs 
-- palindrome :: ???

-- twice f x = f (f x)
-- twice :: ??? 


-- Exercise 3.4 (*)
-- Check your answers to the preceding three questions using GHCi.
-- Remember: Use the `:t expr` or the more verbose `:type expr` command in ghci to query the type on the expression `expr`.
-- Note: Asking ghci to show the value of `e3_1_5` from the definition `e3_1_5 = [tail, init, reverse]`, will result in an error since it is not possible to obtain a string representation of functions.

-- Copy and paste your ghci session into the block comment below.
{-
-}

-- Exercise 3.5 (**)
-- Why is it not possible in general for function types to be instances of the `Eq` class? When is it possible?
-- Hint: Two functions are equal if they always return equal results for equal arguments. Would it be possible to define a function `equal :: (a->b) -> (a->b) -> Bool` in Haskell that determines whether two functions are equal?

-- Type your answer into the block comment below.
{-
-}

