{-# LANGUAGE InstanceSigs #-}
{-# OPTIONS_GHC -Wno-unrecognised-pragmas #-}
{-# HLINT ignore "Eta reduce" #-}
{-# HLINT ignore "Use const" #-}
{-# HLINT ignore "Use <$>" #-}

module LambdaCalculus.Parsing (parse) where

import Control.Applicative
import Data.Char
import Data.Tuple
import LambdaCalculus.Internal (Term (Abs, App, Var), toReadableStr)
import Test.QuickCheck
import Prettyprinter

-- The aim of this file is to implement a parser for the lambda calculus. 

-- We do not do this directly, but first roll our own parser combinator library from scratch along the lines of chapter 13 "Monadic Parsing".
-- Why? Because we can.

-- This file is more verbose than it needs to be. It defines everything from first principles and shows multiple variants of how operations may be defined.
-- It should be able to use a pre-existing parsing combinator library like [Parsec](https://github.com/haskell/parsec) or [Megaparsec](https://github.com/mrkkrp/megaparsec) and define the lambda calculus parser in 4-5 lines. (Personal estimate that needs to be checked)
-- Alternatively, it should be possible to use a parser generator such as [Happy](https://www.haskell.org/happy/) or [Peggy](https://hackage.haskell.org/package/peggy) to generate a parser.

newtype Parser a = P (String -> [(a, String)])

-- We need a newtype here since we want parsers to be instances of applicatives and monads later

runParser :: Parser a -> String -> [(a, String)]
runParser (P p) str = p str

item :: Parser Char
item = P itemParser
  where
    itemParser :: String -> [(Char, String)]
    itemParser [] = []
    itemParser (c : cs) = [(c, cs)]

-- >>> runParser item ""
-- []

-- >>> runParser item "abc"
-- [('a',"bc")]

instance Functor Parser where
  fmap :: (a -> b) -> Parser a -> Parser b
  fmap f (P pa) = P pb
    where
      pb str = fmap (map2nd f) (pa str)
      map2nd :: (a -> b) -> (a, c) -> (b, c)
      map2nd f (a, c) = (f a, c)

-- Also works:
-- map2nd f = swap . fmap f . swap

-- Note: In the text book, only lists up to length 1 are considered for instance definitions. Ask Graham why.
-- Reason: It is stated "For simplicity, however, we only consider parsers that return at most one result.". In this case, IMHO, the type `P (String -> Maybe (a, String))` should have been used in the textbook instead.
-- Anyway, we carry on with the list version, but with the more general (list) instance definitions with the hope that they come in handy later. Note that this is also identical to the `ReadS` type in the prelude.

-- >>> runParser (fmap toUpper item) "abc"
-- [('A',"bc")]

-- Some experiments with parsers that return multiple results

-- Apply parsers in parallel
parallel :: Parser a -> Parser a -> Parser a
parallel (P p1) (P p2) = P (\str -> p1 str ++ p2 str)

duplicate :: Parser a -> Parser a
duplicate p = parallel p p

-- >>> runParser (fmap toUpper (duplicate item)) "abc"
-- [('A',"bc"),('A',"bc")]

-- >>> runParser (parallel (fmap toUpper item) (fmap toLower item)) "aBcD"
-- [('A',"BcD"),('a',"BcD")]

-- >>> runParser ((,) <$> item <*> item) "abc"
-- [(('a','b'),"c")]
-- >>> runParser ((,) <$> item <*> item) "a"
-- []
-- >>> runParser ((,) <$> (duplicate item) <*> item) "ab"
-- [(('a','b'),""),(('a','b'),"")]

instance Applicative Parser where
  pure :: a -> Parser a
  pure x = P (\str -> [(x, str)])
  (<*>) :: Parser (a -> b) -> Parser a -> Parser b
  -- Note: It helps to first see what this looks like  for empty and singleton lists and then generalize.
  -- pf <*> pa = P (\ str -> case runParser pf str of
  --         [] -> []
  --         [(f,rest)] -> runParser (fmap f pa) rest
  pf <*> pa = P (\str -> concat [runParser (fmap f pa) rest | (f, rest) <- runParser pf str])

-- Excerpt from text book: "In turn, <*> applies a parser that returns a function to a parser that returns an argument to give a parser that returns the result of applying the function to the argument, and only succeeds if all the components succeed." :-)

instance Monad Parser where
  (>>=) :: Parser a -> (a -> Parser b) -> Parser b
  pa >>= f = P (\str -> concat [runParser (f a) rest | (a, rest) <- runParser pa str])

-- >>> runParser (do {x <- item; y <- item; return (x,y)}) "ab"
-- [(('a','b'),"")]

-- -- Note: Deriving the functor and applicative instance from the monad instances is also possible:
-- instance Functor Parser where
--     fmap f x = pure f <*> x
-- instance Applicative Parser where
--     pure = return
--     mf <*> mx = do
--         f <- mf
--         x <- mx
--         return (f x)

instance Alternative Parser where
  empty :: Parser a
  empty = P (\_ -> [])
  (<|>) :: Parser a -> Parser a -> Parser a
  pa <|> pb =
    P
      ( \str -> case runParser pa str of
          [] -> runParser pb str
          (x : xs) -> x : xs
      )

sat :: (Char -> Bool) -> Parser Char
sat p = do
  c <- item
  if p c then return c else empty

-- -- The following should also work, but the above uses the machinery already developed instead of doing everything at the low level.
-- ```
-- sat p = P (\s -> case s of
--     [] -> []
--     (c:cs) -> if p c then [(c,cs)] else [])
-- ```

digit :: Parser Char
digit = sat isDigit

lower :: Parser Char
lower = sat isLower

upper :: Parser Char
upper = sat isUpper

alpha :: Parser Char
alpha = sat isAlpha

alphaNum :: Parser Char
alphaNum = sat isAlphaNum

char :: Char -> Parser Char
char x = sat (== x)

string :: String -> Parser String
string [] = return []
string (x : xs) = do
  char x
  string xs
  return (x : xs)

kleeneStar :: Parser a -> Parser [a]
kleeneStar x = kleenePlus x <|> pure []

kleenePlus :: Parser a -> Parser [a]
kleenePlus x = ((:) <$> x) <*> kleeneStar x

-- Note: The `Alternative` type class contains default definitions of `many` and `some` that are identical to the definitions of `kleeneStar` and `kleenePlus` above.
-- kleeneStar = many
-- kleenePlus = some

-- >>> runParser (kleenePlus lower) "abc de"
-- [("abc"," de")]
-- >>> runParser (kleenePlus lower) " abc de"
-- []
-- >>> runParser (kleeneStar lower) " abc de"
-- [(""," abc de")]

zeroOrMoreSpaces :: Parser ()
zeroOrMoreSpaces = do
  kleeneStar (sat isSpace)
  return ()

token :: Parser b -> Parser b
token p = do
  zeroOrMoreSpaces
  v <- p
  zeroOrMoreSpaces
  return v

symbol :: String -> Parser String
symbol str = token (string str)

paren :: Parser a -> Parser a
paren p = do
  symbol "("
  v <- p
  symbol ")"
  return v

--------------------------------------------
-- The lambda calculus parser starts here --
--------------------------------------------


pId :: Parser [Char]
pId = token (kleenePlus pIdChar)

pIdChar :: Parser Char
pIdChar = sat $ not . (\c -> c `elem` ".()λ=" || isSpace c)

-- >>> runParser pId "x y"
-- [("x","y")]

-- >>> runParser (kleenePlus pId) "x y"
-- [(["x","y"],"")]

{-

Uses the following PEG grammar rules:

Term ← Atom+
Atom ← Lam / Var / '(' Term ')'
Lam ← ('λ'/'%') Var '.' Term
Var ← [^.()λ%=\s]+

Note:
- The parser will accept `λ` and `%` as "lambda" for the convenience of people who do not have a λ on their keyboard.
- The expression `[^.()λ%=\s]` denotes the negation of the character class containing the characters `.`,`(`,`)`,`λ`,`%`,`=`, and any space characters.
- Left recursion has been removed. This step is crucial to get the parser to terminate, and is nontrivial to do for anyone who has not yet done a course in compiler construction. These rules should also work as a CFG grammar.

-}

pDef :: Parser ([Char], Term)
pDef = do
  id <- pId
  symbol "="
  val <- pTerm
  return (id, val)

pTerm :: Parser Term
pTerm = do
  ms <- kleenePlus pAtom
  return (foldl1 App ms)

pAtom :: Parser Term
pAtom = pLam <|> pVar <|> paren pTerm

pLam :: Parser Term
pLam = do
  symbol "%" <|> symbol "λ"
  id <- pId
  symbol "."
  m <- pTerm
  return (Abs id m)

pVar :: Parser Term
pVar = Var <$> pId

-- Also works, but is more verbose
-- var = do
--     id <- pId
--     return (Var id)

-- >>> runParser pTerm "λx.x"
-- [(Abs "x" (Var "x"),"")]

-- >>> runParser pTerm "λy.x"
-- [(Abs "y" (Var "x"),"")]

-- >>> runParser pTerm "λx.x y"
-- [(Abs "x" (App (Var "x") (Var "y")),"")]

-- >>> runParser pTerm "a λx. x y"
-- [(App (Var "a") (Abs "x" (App (Var "x") (Var "y"))),"")]

-- >>> runParser pTerm "a (λx. x) y"
-- [(App (App (Var "a") (Abs "x" (Var "x"))) (Var "y"),"")]

-- Returns the single parse result if it exists, or an exception if one or many exist.
parse :: String -> Term
parse str = case runParser pTerm str of
  [] -> error "Invalid Input"
  [(t, [])] -> t
  [(_, rest)] -> error ("Unused input: " ++ rest)
  (x : xs) -> error "Ambiguous input"

-- Returns all parse results
allParseResults :: String -> [(Term, String)]
allParseResults = runParser pTerm

-- The main correctness property of the parser
prop_parse_toReadableStr :: Term -> Bool
prop_parse_toReadableStr t = parse (toReadableStr t) == t

-- prop> prop_parse_toReadableStr
-- +++ OK, passed 100 tests.

-- The main correctness property of the parser
prop_parse_pretty :: Term -> Bool
prop_parse_pretty t = parse (show $ pretty t) == t

-- prop> prop_parse_pretty
-- +++ OK, passed 100 tests.
