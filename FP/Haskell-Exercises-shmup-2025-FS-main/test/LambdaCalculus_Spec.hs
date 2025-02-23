{-# LANGUAGE ScopedTypeVariables #-}
{-# LANGUAGE TypeApplications #-}

module LambdaCalculus_Spec where

import LambdaCalculus
import Test.Hspec
import Test.QuickCheck
import Test.Validity
import Prettyprinter
import Data.Maybe (fromJust)

-- The main correctness property of the parser
prop_parse_pretty :: Term -> Bool
prop_parse_pretty t = parse (show $ pretty t) == t

-- I = λ x . x
tI :: Term
tI = Abs "x" (Var "x")

-- L = λ x . λ y. x
tL :: Term
tL = Abs "x" (Abs "y" (Var "y"))

-- ω = λ x . x x
tω :: Term
tω = Abs "x" (App (Var "x") (Var "x"))

-- Ω = ω ω = (λ x . x x) (λ x . x x)
tΩ :: Term
tΩ = App tω tω

alphaEqList :: [Term] -> [Term] -> Bool
alphaEqList = liftEq alphaEq
  where
    liftEq :: (a -> a -> Bool) -> ([a] -> [a] -> Bool)
    liftEq _ [] [] = True
    liftEq e (x : xs) (y : ys) = (x `e` y) && liftEq e xs ys
    liftEq _ _ _ = False

spec :: Spec
spec = do
  describe "`parse`" $ do
    it "of a pretty printed terms returns the same term" $ do
      property prop_parse_pretty

  describe "leftmostOutermostStep" $ do
    it "reduces `(tL tΩ) tI` to a term that is alpha equivalent to `tI tI`" $ do
      fromJust (leftmostOutermostStep (App (App tL tΩ) tI)) `alphaEq` App tI tI

  describe "`betaNormalForm`" $ do
    it "reduces `(λx.λy.x y) y` to a term that is alpha equivalent to `λz.y z`" $ do
      betaNormalForm (parse "(λx.λy.x y) y") `alphaEq` parse "λz.y z"

  describe "`derivation`" $ do
    it "computes the correct derivation for `(tL tΩ) tI`" $ do
      derivation leftmostOutermostStep (App (App tL tΩ) tI) `alphaEqList` [App (App (Abs "x" (Abs "y" (Var "y"))) (App (Abs "x" (App (Var "x") (Var "x"))) (Abs "x" (App (Var "x") (Var "x"))))) (Abs "x" (Var "x")),App (Abs "y" (Var "y")) (Abs "x" (Var "x")),Abs "x" (Var "x")]
    it "computes the correct derivation for `tΩ`" $ do
        property $ \n -> take n (derivation leftmostOutermostStep tΩ) `alphaEqList` replicate n tΩ

  describe "Type class instances of `Term` satisfy their type class laws" $ do
    eqSpecOnArbitrary @Term
