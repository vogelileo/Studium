import Data.Fixed (div')

double x = x + x

quadruple x = double (double x)

factorial n = product [1 .. n]

average ns = div (sum ns) (length ns)