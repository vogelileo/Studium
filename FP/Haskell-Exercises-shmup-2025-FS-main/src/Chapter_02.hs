module Chapter_02 where

-- Note: Do not forget to uncomment the relevant lines in `test/Spec.hs` before starting to work on a chapter to enable its automated tests.

-- Exercise 2.1 (*)
-- Work through all the examples of chapter "First Steps" from page 14 to 20 in the book Programming in Haskell using GHCi.

-- Copy and paste your ghci session and the contents of the file `test.hs` you created into the block comment below.
{-
???

PS C:\Users\092132848\Documents\GitHub\Studium\FP\Haskell-Exercises-shmup-2025-FS-main\src> ghci test.hs
GHCi, version 9.4.8: https://www.haskell.org/ghc/  :? for help
[1 of 2] Compiling Main             ( test.hs, interpreted )
Ok, one module loaded.
ghci> quadruple 3
12
ghci> quadruple 10
40
ghci> :reload
[1 of 2] Compiling Main             ( test.hs, interpreted ) [Source file changed]
Ok, one module loaded.
ghci> facotrial 10

<interactive>:4:1: error:
    Variable not in scope: facotrial :: t0 -> t
    Suggested fix: Perhaps use `factorial' (line 7)
ghci> factorial 10
3628800
ghci>

-}

-- Exercise 2.2 (*)
-- Parenthesize the following numeric expressions:

e_2_2_a :: Int
e_2_2_a = (2 ^ 3) * 4

e_2_2_b :: Int
e_2_2_b = (2 * 3) + (4 * 5)

e_2_2_c :: Int
e_2_2_c = 2 + (3 * (4 ^ 5))
