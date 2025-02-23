{-# OPTIONS_GHC -Wno-unrecognised-pragmas #-}
{-# HLINT ignore "Use camelCase" #-}

module LambdaCalculus 
    (Term (Var, Abs, App)
    , alphaEq
    , derivation
    , leftmostOutermostStep
    , betaNormalForm
    , parse
    , pretty
    ) where

{-
This module merely imports and re-exports the publicly usable API of the lambda calculus implementation in the `LambdaCalculus` directory.

The implementation and exercises are located in the module `LambdaCalculus.Internal`.
-}

import LambdaCalculus.Internal 
import LambdaCalculus.Parsing
import Prettyprinter
