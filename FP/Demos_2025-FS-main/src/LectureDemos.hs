{-# OPTIONS_GHC -Wno-missing-signatures #-}
{-# OPTIONS_GHC -Wno-missing-export-lists #-}
-- {-# LANGUAGE InstanceSigs #-}

module LectureDemos where

-- Lecture on 19.02.2025

-- >>> sum' [1..10]
-- 55

-- sum' :: Num a => [a] -> a
-- sum' :: [Int] -> Int
sum' [] = 0
sum' (n:ns) = n + sum' ns
