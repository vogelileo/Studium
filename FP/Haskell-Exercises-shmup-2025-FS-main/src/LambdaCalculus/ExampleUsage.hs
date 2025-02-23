{-# OPTIONS_GHC -Wno-unrecognised-pragmas #-}
{-# HLINT ignore "Use camelCase" #-}
module LambdaCalculus.ExampleUsage where

import qualified LambdaCalculus as LC

tL :: LC.Term
tL = LC.parse "(λx.(λy.y))"

tΩ :: LC.Term
tΩ = LC.parse "(λ x . x x) (λ x . x x)"

tL_tΩ :: LC.Term
tL_tΩ = LC.App tL tΩ

-- >>> tL_tΩ
-- App (Abs "x" (Abs "y" (Var "y"))) (App (Abs "x" (App (Var "x") (Var "x"))) (Abs "x" (App (Var "x") (Var "x"))))


-- >>> LC.pretty tL_tΩ
-- ((λx.(λy.y)) ((λx.(x x)) (λx.(x x))))

-- >>> LC.pretty $ LC.derivation LC.leftmostOutermostStep tL_tΩ
-- [((λx.(λy.y)) ((λx.(x x)) (λx.(x x)))), (λy.y)]

-- >>> LC.pretty $ LC.betaNormalForm tL_tΩ
-- (λy.y)

-- >>> LC.pretty $ take 10 (LC.derivation LC.leftmostOutermostStep tΩ)
-- [ ((λx.(x x)) (λx.(x x)))
-- , ((λx.(x x)) (λx.(x x)))
-- , ((λx.(x x)) (λx.(x x)))
-- , ((λx.(x x)) (λx.(x x)))
-- , ((λx.(x x)) (λx.(x x)))
-- , ((λx.(x x)) (λx.(x x)))
-- , ((λx.(x x)) (λx.(x x)))
-- , ((λx.(x x)) (λx.(x x)))
-- , ((λx.(x x)) (λx.(x x)))
-- , ((λx.(x x)) (λx.(x x))) ]
