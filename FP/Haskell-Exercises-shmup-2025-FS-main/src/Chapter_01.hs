{-# OPTIONS_GHC -Wno-unrecognised-pragmas #-}
{-# HLINT ignore "Use foldr" #-}
module Chapter_01 where

-- Exercise 1.ToolCheck1
-- Verify that you have correctly installed the Haskell development tools on your local machine by running the commands `stack build`, `stack run`, and `stack test` from the root directory of this repository.
-- Look at the output and make sure that it does not contain any errors:
--   `stack build` should not contain any compile errors.
--   `stack run` should end with the message "All good!".
--   `stack test` should end with a failure. Check that the tests pass after entering the correct solution to exercises below.

-- Copy and paste the contents of your shell session with the outputs of the commands above into the block comment below.
{-
???
-}


-- Exercise 1.1 (*)
-- Look at the two ways to calculate `double (double 2)` on pages 3 and 4 in the book Programming in Haskell.
-- Give another possible way to calculate the result of `double (double 2)`. Use the same definition `double x = x + x` as in the book.


-- Complete the following block comment.
-- Note: Unlike the textbook, we will use the ‘==’ symbol instead of ‘=’ for equational reasoning to be consistent with the notation of equality used in Haskell, since ‘=’ in Haskell is used for definitions. 

{-
double (double 2)
== {applying ???}
???
...
-}


-- Exercise 1.2 (*)
-- Show that `sum [x] = x` for any number `x`. Use the following definition of `sum` stated on page 9 in the book "Programming in Haskell". 

-- sum [] = 0
-- sum (n:ns) = n + sum ns

-- Note: In case you are wondering, the syntax `[x]` for a singleton list containing the element `x` is just syntactic sugar for, and equivalent to the list `(x:[])`. The list `[x,y,z]` is syntactic sugar for `(x:(y:(z:[])))`. This syntactic sugar is referred to as "list patterns", and will be explained in more detail in chapter 4.  

-- Complete the following block comment.
{-
sum [x]
== {applying ???}
???
...
-}


-- Exercise 1.3 (*)
-- Define a function myProduct that produces the product of a list of numbers, and show using your definition that myProduct [2,3,4] == 24.
-- Note: We use the name "myProduct" since the name product is already defined in the ghc Prelude.

myProduct :: Num p => [p] -> p
myProduct [] = undefined
myProduct (n:ns) = undefined


{-
myProduct [2,3,4]
== {???}
???
...
-}

