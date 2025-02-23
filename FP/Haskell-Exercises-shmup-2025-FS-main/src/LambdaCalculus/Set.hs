module LambdaCalculus.Set (module SetImpl) where

-- Single source of truth for choosing the set implementation used by both, `src/LambdaCalculus/Internal.hs` and `test/LambdaCalculus/Internal_Spec.hs`

-- Default implementation:
--
-- (Comment the following line to use your own BST instead,
-- or keep it uncommented to use the default implementation.
import LambdaCalculus.UnorderedSet as SetImpl

-- Alternative implementation: Your own BST
--
-- (Uncomment the following line to use that instead of the default,
-- or keep it commented to continue using the default implementation.)
--import BinarySearchTree as SetImpl
