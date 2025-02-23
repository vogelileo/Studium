{-# LANGUAGE ScopedTypeVariables #-}
{-# OPTIONS_GHC -Wno-unrecognised-pragmas #-}
{-# HLINT ignore "Redundant $" #-}
{-# HLINT ignore "Use null" #-}
{-# HLINT ignore "Use isNothing" #-}
{-# HLINT ignore "Redundant ==" #-}

module LambdaCalculus.Internal_Spec where

import LambdaCalculus.Internal
import Test.Hspec
import Test.QuickCheck
import qualified LambdaCalculus_Spec

import qualified LambdaCalculus.Set as Set



unitTestsFromInternalModule :: [(String, Bool)]
unitTestsFromInternalModule =
  [ ("toReadableStr tfx == \"(f x)\"", toReadableStr tfx == "(f x)")
  , ("Set.toList (freeVars tI) == []", Set.toList (freeVars tI) == [])
  , ("freeVars tgfx == Set.fromList [\"f\",\"g\",\"x\"]", freeVars tgfx == Set.fromList ["f","g","x"])
  , ("freeVars tω == Set.empty", freeVars tω == Set.empty)
  , ("freeVars tΩ == Set.empty", freeVars tΩ == Set.empty)
  , ("freshId (Set.fromList [\"x\",\"y\"]) \"x\" == \"x'\"", freshId (Set.fromList ["x","y"]) "x" == "x'")
  , ("freshId (Set.fromList [\"x\",\"x'\",\"y\"]) \"x'\" == \"x''\"", freshId (Set.fromList ["x","x'","y"]) "x'" == "x''")
  , ("freshId (Set.fromList [\"x'\",\"y\"]) \"x\" == \"x\"", freshId (Set.fromList ["x'","y"]) "x" == "x")
  , ("renameBoundVar (Abs \"x\" (Var \"x\")) \"y\" == Just (Abs \"y\" (Var \"y\"))", renameBoundVar (Abs "x" (Var "x")) "y" == Just (Abs "y" (Var "y")))
  , ("renameBoundVar (Abs \"x\" (Var \"y\")) \"y\" == Nothing", renameBoundVar (Abs "x" (Var "y")) "y" == Nothing)
  , ("renameBoundVar (Var \"x\") \"y\" == Nothing", renameBoundVar (Var "x") "y" == Nothing)
  , ("applySubst (\"x\", Var \"y\") (Var \"x\") == Var \"y\"", applySubst ("x", Var "y") (Var "x") == Var "y")
  , ("applySubst (\"x\", Var \"y\") (Var \"y\") == Var \"y\"", applySubst ("x", Var "y") (Var "y") == Var "y")
  , ("applySubst (\"x\", Var \"y\") (Abs \"x\" (Var \"x\")) == Abs \"x\" (Var \"x\")", applySubst ("x", Var "y") (Abs "x" (Var "x")) == Abs "x" (Var "x"))
  -- The following test requires renaming bound variables.
  , ("applySubst (\"x\", Var \"y\") (Abs \"y\" (App (Var \"x\") (Var \"y\"))) == Abs \"y'\" (App (Var \"y\") (Var \"y'\"))", applySubst ("x", Var "y") (Abs "y" (App (Var "x") (Var "y"))) == Abs "y'" (App (Var "y") (Var "y'")))
    -- The following test requires renaming bound variables.
  , ("applySubst (\"x\", Var \"y\") (Abs \"y\" $ App (App (Var \"x\") (Var \"y\")) (Var \"y'\")) == Abs \"y''\" (App (App (Var \"y\") (Var \"y''\")) (Var \"y'\"))", applySubst ("x", Var "y") (Abs "y" $ App (App (Var "x") (Var "y")) (Var "y'")) == Abs "y''" (App (App (Var "y") (Var "y''")) (Var "y'")))
  , ("betaReduce (Var \"x\") == Nothing", betaReduce (Var "x") == Nothing)
  , ("betaReduce tI == Nothing", betaReduce tI == Nothing)
  , ("betaReduce (App tI tI) == Just (tI)", betaReduce (App tI tI) == Just tI)
  , ("betaReduce (App (Var \"y\") (App tI tI)) == Nothing", betaReduce (App (Var "y") (App tI tI)) == Nothing)
  , ("containsBetaRedex (Var \"x\") == False", containsBetaRedex (Var "x") == False)
  , ("containsBetaRedex tI == False", containsBetaRedex tI == False)
  , ("containsBetaRedex (App tI tI) == True", containsBetaRedex (App tI tI) == True)
  , ("containsBetaRedex (App (Var \"y\") (App tI tI)) == True", containsBetaRedex (App (Var "y") (App tI tI)) == True)
  , ("leftmostOutermostStep tI == Nothing", leftmostOutermostStep tI == Nothing)
  , ("leftmostOutermostStep (App tI tI) == Just tI", leftmostOutermostStep (App tI tI) == Just tI)
  , ("leftmostOutermostStep (App tL tΩ) == Just (Abs \"y\" (Var \"y\"))", leftmostOutermostStep (App tL tΩ) == Just (Abs "y" (Var "y")))
  , ("leftmostOutermostStep (App (App tL tΩ) tI) == Just (App (Abs \"y\" (Var \"y\")) (Abs \"x\" (Var \"x\")))", leftmostOutermostStep (App (App tL tΩ) tI) == Just (App (Abs "y" (Var "y")) (Abs "x" (Var "x"))))
  -- , ("", )
  ]

propsFromInternalModule :: [(String, Property)]
propsFromInternalModule =
  -- Note: The call to `property` is needed here and cannot be fmapped in later since the values `prop_*` have different types.
  [ ("prop_nfin_y_tfx",        property $ prop_nfin_y_tfx)
  , ("prop_nfin_x_tfx",        property $ prop_nfin_x_tfx)
  , ("prop_nfin_f_tfx", property $ prop_nfin_f_tfx)
  , ("prop_nfin_x_tI", property $ prop_nfin_x_tI)
  , ("prop_freeVars_nfin", property $ prop_freeVars_nfin)
  , ("prop_freshId_not_member", property $ prop_freshId_not_member)
  , ("prop_freshId_prefix", property $ prop_freshId_prefix)
  , ("prop_renameBoundVar_freshId", property $ prop_renameBoundVar_freshId)
  , ("prop_alphaEq_renameBoundVar_freshId", property $ prop_alphaEq_renameBoundVar_freshId)
  , ("prop_alphaEq_refl", property $ prop_alphaEq_refl)
  , ("prop_alphaEq_var_app", property $ prop_alphaEq_var_app)
  , ("prop_alphaEq_app_abs", property $ prop_alphaEq_app_abs)
  , ("prop_alphaEq_abs_var", property $ prop_alphaEq_abs_var)
  , ("prop_derivation_infinite", property $ prop_derivation_infinite)
  , ("prop_betaNormalForm_containsBetaRedex", property $ prop_betaNormalForm_containsBetaRedex)
  -- , ("", property $ )
  ]

startsWith _ [] = True
startsWith (x:xs) (y:ys) = (x==y) && startsWith xs ys
startsWith [] (y:ys) = False


spec :: Spec
spec = do

  describe "`freeVars`" $ do
    it "returns variable names that are free with respect to `nfin`" $
      property $ \m -> and [not (x `nfin` m) | x <- Set.toList (freeVars m)]

  describe "`freshId`" $ do
    it "`freshId s x` is not a member of `s`" $
      property $ \s x -> not (freshId s x `Set.member` s)
    it "`freshId s x` starts with the string`x`" $
      property $ \s x -> freshId s x `startsWith` x

  describe "Unit tests from module `LambdaCalculus.Internal`" $
    sequence_ [it d p | (d,p) <- unitTestsFromInternalModule]

  describe "Properties imported from module `LambdaCalculus.Internal`" $
    sequence_ [it d p | (d,p) <- propsFromInternalModule]

  describe "LambdaCalculus public API" LambdaCalculus_Spec.spec