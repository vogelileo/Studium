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
stack build
Downloaded msys2-20240727.
Downloaded 7z.dll.
Downloaded 7z.exe.
Decompressing msys2-20240727.tar.xz...

7-Zip 22.01 (x64) : Copyright (c) 1999-2022 Igor Pavlov : 2022-07-15

Scanning the drive for archives:
1 file, 71943628 bytes (69 MiB)

Extracting archive: C:\Users\schwe\AppData\Local\Programs\stack\x86_64-windows\msys2-20240727.tar.xz
--
Path = C:\Users\schwe\AppData\Local\Programs\stack\x86_64-windows\msys2-20240727.tar.xz
Type = xz
Physical Size = 71943628
Method = LZMA2:24 CRC32
Streams = 1
Blocks = 5
Cluster Size = 67108864
Characteristics = BlockPackSize BlockUnpackSize

Everything is Ok

Size:       331865088
Compressed: 71943628
Extracting msys2-20240727.tar...
Extracted total of 21 files from msys2-20240727.tar
MSYS2 is starting for the first time. Executing the initial setup.
Copying skeleton files.
These files are for the users to personalise their msys2 experience.

They will never be overwritten nor automatically updated.

'./.bashrc' -> '/home/schwe/.bashrc'
'./.bash_logout' -> '/home/schwe/.bash_logout'
'./.bash_profile' -> '/home/schwe/.bash_profile'
'./.profile' -> '/home/schwe/.profile'
Initial setup complete. MSYS2 is now ready to use.
Preparing to install GHC to an isolated location. This will not interfere with any system-level installation.
Downloaded ghc-9.4.8.
Already downloaded 7z.dll.
Already downloaded 7z.exe.
Decompressing ghc-9.4.8.tar.xz...

7-Zip 22.01 (x64) : Copyright (c) 1999-2022 Igor Pavlov : 2022-07-15

Scanning the drive for archives:
1 file, 307598296 bytes (294 MiB)

Extracting archive: C:\Users\schwe\AppData\Local\Programs\stack\x86_64-windows\ghc-9.4.8.tar.xz
--
Path = C:\Users\schwe\AppData\Local\Programs\stack\x86_64-windows\ghc-9.4.8.tar.xz
Type = xz
Physical Size = 307598296
Method = LZMA2:26 CRC64
Streams = 1
Blocks = 1

Everything is Ok

haskell-lexer          > Language\Haskell\Lexer\Lex.hs:367:1: warning: [-Wunused-top-binds]
mintty                 > [2 of 2] Compiling System.Console.MinTTY
haskell-lexer          >     Defined but not used: `start16'
haskell-lexer          >     |
haskell-lexer          > 367 | start16 is = state16 (\ as is -> gotError as is) "" is
haskell-lexer          >     | ^^^^^^^
haskell-lexer          >
haskell-lexer          > Language\Haskell\Lexer\Lex.hs:377:1: warning: [-Wunused-top-binds]
haskell-lexer          >     Defined but not used: `start17'
haskell-lexer          >     |
haskell-lexer          > 377 | start17 is = state17 (\ as is -> gotError as is) "" is
haskell-lexer          >     | ^^^^^^^
haskell-lexer          >
haskell-lexer          > Language\Haskell\Lexer\Lex.hs:386:1: warning: [-Wunused-top-binds]
colour                 > [ 7 of 14] Compiling Data.Colour.RGBSpace.HSL
haskell-lexer          >     Defined but not used: `start18'
haskell-lexer          >     |
haskell-lexer          > 386 | start18 is = state18 (\ as is -> gotError as is) "" is
haskell-lexer          >     | ^^^^^^^
haskell-lexer          >
haskell-lexer          > Language\Haskell\Lexer\Lex.hs:397:1: warning: [-Wunused-top-binds]
haskell-lexer          >     Defined but not used: `start19'
haskell-lexer          >     |
haskell-lexer          > 397 | start19 is = state19 (\ as is -> gotError as is) "" is
haskell-lexer          >     | ^^^^^^^
haskell-lexer          >
haskell-lexer          > Language\Haskell\Lexer\Lex.hs:406:1: warning: [-Wunused-top-binds]
haskell-lexer          >     Defined but not used: `start20'
haskell-lexer          >     |
haskell-lexer          > 406 | start20 is = state20 (\ as is -> gotError as is) "" is
haskell-lexer          >     | ^^^^^^^
haskell-lexer          >
haskell-lexer          > Language\Haskell\Lexer\Lex.hs:415:1: warning: [-Wunused-top-binds]
haskell-lexer          >     Defined but not used: `start21'
haskell-lexer          >     |
haskell-lexer          > 415 | start21 is = state21 (\ as is -> gotError as is) "" is
haskell-lexer          >     | ^^^^^^^
haskell-lexer          >
haskell-lexer          > Language\Haskell\Lexer\Lex.hs:428:1: warning: [-Wunused-top-binds]
haskell-lexer          >     Defined but not used: `start22'
haskell-lexer          >     |
haskell-lexer          > 428 | start22 is = state22 (\ as is -> gotError as is) "" is
haskell-lexer          >     | ^^^^^^^
haskell-lexer          >
haskell-lexer          > Language\Haskell\Lexer\Lex.hs:437:1: warning: [-Wunused-top-binds]
haskell-lexer          >     Defined but not used: `start23'
haskell-lexer          >     |
haskell-lexer          > 437 | start23 is = state23 (\ as is -> gotError as is) "" is
haskell-lexer          >     | ^^^^^^^
haskell-lexer          >
haskell-lexer          > Language\Haskell\Lexer\Lex.hs:446:1: warning: [-Wunused-top-binds]
haskell-lexer          >     Defined but not used: `start24'
haskell-lexer          >     |
haskell-lexer          > 446 | start24 is = state24 (\ as is -> gotError as is) "" is
haskell-lexer          >     | ^^^^^^^
haskell-lexer          >
haskell-lexer          > Language\Haskell\Lexer\Lex.hs:455:1: warning: [-Wunused-top-binds]
haskell-lexer          >     Defined but not used: `start25'
haskell-lexer          >     |
haskell-lexer          > 455 | start25 is = state25 (\ as is -> gotError as is) "" is
haskell-lexer          >     | ^^^^^^^
haskell-lexer          >
haskell-lexer          > Language\Haskell\Lexer\Lex.hs:465:1: warning: [-Wunused-top-binds]
haskell-lexer          >     Defined but not used: `start26'
haskell-lexer          >     |
haskell-lexer          > 465 | start26 is = state26 (\ as is -> gotError as is) "" is
haskell-lexer          >     | ^^^^^^^
haskell-lexer          >
haskell-lexer          > Language\Haskell\Lexer\Lex.hs:475:1: warning: [-Wunused-top-binds]
haskell-lexer          >     Defined but not used: `start27'
haskell-lexer          >     |
haskell-lexer          > 475 | start27 is = state27 (\ as is -> gotError as is) "" is
haskell-lexer          >     | ^^^^^^^
haskell-lexer          >
haskell-lexer          > Language\Haskell\Lexer\Lex.hs:484:1: warning: [-Wunused-top-binds]
haskell-lexer          >     Defined but not used: `start28'
haskell-lexer          >     |
haskell-lexer          > 484 | start28 is = state28 (\ as is -> gotError as is) "" is
haskell-lexer          >     | ^^^^^^^
haskell-lexer          >
haskell-lexer          > Language\Haskell\Lexer\Lex.hs:493:1: warning: [-Wunused-top-binds]
haskell-lexer          >     Defined but not used: `start29'
haskell-lexer          >     |
haskell-lexer          > 493 | start29 is = state29 (\ as is -> gotError as is) "" is
haskell-lexer          >     | ^^^^^^^
haskell-lexer          >
haskell-lexer          > Language\Haskell\Lexer\Lex.hs:503:1: warning: [-Wunused-top-binds]
haskell-lexer          >     Defined but not used: `start30'
haskell-lexer          >     |
haskell-lexer          > 503 | start30 is = state30 (\ as is -> gotError as is) "" is
haskell-lexer          >     | ^^^^^^^
haskell-lexer          >
haskell-lexer          > Language\Haskell\Lexer\Lex.hs:516:1: warning: [-Wunused-top-binds]
haskell-lexer          >     Defined but not used: `start32'
haskell-lexer          >     |
haskell-lexer          > 516 | start32 is = state32 (\ as is -> gotError as is) "" is
haskell-lexer          >     | ^^^^^^^
haskell-lexer          >
haskell-lexer          > Language\Haskell\Lexer\Lex.hs:525:1: warning: [-Wunused-top-binds]
haskell-lexer          >     Defined but not used: `start33'
haskell-lexer          >     |
haskell-lexer          > 525 | start33 is = state33 (\ as is -> gotError as is) "" is
haskell-lexer          >     | ^^^^^^^
haskell-lexer          >
haskell-lexer          > Language\Haskell\Lexer\Lex.hs:534:1: warning: [-Wunused-top-binds]
haskell-lexer          >     Defined but not used: `start34'
haskell-lexer          >     |
haskell-lexer          > 534 | start34 is = state34 (\ as is -> gotError as is) "" is
haskell-lexer          >     | ^^^^^^^
haskell-lexer          >
haskell-lexer          > Language\Haskell\Lexer\Lex.hs:571:1: warning: [-Wunused-top-binds]
haskell-lexer          >     Defined but not used: `start35'
haskell-lexer          >     |
haskell-lexer          > 571 | start35 is = state35 (\ as is -> gotError as is) "" is
haskell-lexer          >     | ^^^^^^^
haskell-lexer          >
haskell-lexer          > Language\Haskell\Lexer\Lex.hs:582:1: warning: [-Wunused-top-binds]
haskell-lexer          >     Defined but not used: `start36'
haskell-lexer          >     |
haskell-lexer          > 582 | start36 is = state36 (\ as is -> gotError as is) "" is
haskell-lexer          >     | ^^^^^^^
haskell-lexer          >
haskell-lexer          > Language\Haskell\Lexer\Lex.hs:601:1: warning: [-Wunused-top-binds]
haskell-lexer          >     Defined but not used: `start38'
haskell-lexer          >     |
haskell-lexer          > 601 | start38 is = state38 (\ as is -> gotError as is) "" is
haskell-lexer          >     | ^^^^^^^
haskell-lexer          >
haskell-lexer          > Language\Haskell\Lexer\Lex.hs:625:1: warning: [-Wunused-top-binds]
haskell-lexer          >     Defined but not used: `start39'
haskell-lexer          >     |
haskell-lexer          > 625 | start39 is = state39 (\ as is -> gotError as is) "" is
haskell-lexer          >     | ^^^^^^^
haskell-lexer          >
haskell-lexer          > Language\Haskell\Lexer\Lex.hs:657:1: warning: [-Wunused-top-binds]
haskell-lexer          >     Defined but not used: `start41'
haskell-lexer          >     |
haskell-lexer          > 657 | start41 is = state41 (\ as is -> gotError as is) "" is
haskell-lexer          >     | ^^^^^^^
haskell-lexer          >
haskell-lexer          > Language\Haskell\Lexer\Lex.hs:673:1: warning: [-Wunused-top-binds]
haskell-lexer          >     Defined but not used: `start42'
haskell-lexer          >     |
haskell-lexer          > 673 | start42 is = state42 (\ as is -> gotError as is) "" is
haskell-lexer          >     | ^^^^^^^
haskell-lexer          >
haskell-lexer          > Language\Haskell\Lexer\Lex.hs:685:1: warning: [-Wunused-top-binds]
haskell-lexer          >     Defined but not used: `start44'
haskell-lexer          >     |
haskell-lexer          > 685 | start44 is = state44 (\ as is -> gotError as is) "" is
haskell-lexer          >     | ^^^^^^^
haskell-lexer          >
haskell-lexer          > Language\Haskell\Lexer\Lex.hs:725:1: warning: [-Wunused-top-binds]
haskell-lexer          >     Defined but not used: `start45'
haskell-lexer          >     |
haskell-lexer          > 725 | start45 is = state45 (\ as is -> gotError as is) "" is
haskell-lexer          >     | ^^^^^^^
haskell-lexer          >
haskell-lexer          > Language\Haskell\Lexer\Lex.hs:738:1: warning: [-Wunused-top-binds]
haskell-lexer          >     Defined but not used: `start46'
haskell-lexer          >     |
haskell-lexer          > 738 | start46 is = state46 (\ as is -> gotError as is) "" is
haskell-lexer          >     | ^^^^^^^
haskell-lexer          >
haskell-lexer          > Language\Haskell\Lexer\Lex.hs:747:1: warning: [-Wunused-top-binds]
haskell-lexer          >     Defined but not used: `start47'
haskell-lexer          >     |
haskell-lexer          > 747 | start47 is = state47 (\ as is -> gotError as is) "" is
haskell-lexer          >     | ^^^^^^^
haskell-lexer          >
haskell-lexer          > Language\Haskell\Lexer\Lex.hs:756:1: warning: [-Wunused-top-binds]
haskell-lexer          >     Defined but not used: `start48'
haskell-lexer          >     |
haskell-lexer          > 756 | start48 is = state48 (\ as is -> gotError as is) "" is
haskell-lexer          >     | ^^^^^^^
haskell-lexer          >
haskell-lexer          > Language\Haskell\Lexer\Lex.hs:766:1: warning: [-Wunused-top-binds]
haskell-lexer          >     Defined but not used: `start49'
haskell-lexer          >     |
haskell-lexer          > 766 | start49 is = state49 (\ as is -> gotError as is) "" is
haskell-lexer          >     | ^^^^^^^
haskell-lexer          >
haskell-lexer          > Language\Haskell\Lexer\Lex.hs:775:1: warning: [-Wunused-top-binds]
haskell-lexer          >     Defined but not used: `start50'
haskell-lexer          >     |
haskell-lexer          > 775 | start50 is = state50 (\ as is -> gotError as is) "" is
haskell-lexer          >     | ^^^^^^^
haskell-lexer          >
haskell-lexer          > Language\Haskell\Lexer\Lex.hs:785:1: warning: [-Wunused-top-binds]
haskell-lexer          >     Defined but not used: `start51'
haskell-lexer          >     |
haskell-lexer          > 785 | start51 is = state51 (\ as is -> gotError as is) "" is
haskell-lexer          >     | ^^^^^^^
haskell-lexer          >
haskell-lexer          > Language\Haskell\Lexer\Lex.hs:794:1: warning: [-Wunused-top-binds]
haskell-lexer          >     Defined but not used: `start52'
haskell-lexer          >     |
haskell-lexer          > 794 | start52 is = state52 (\ as is -> gotError as is) "" is
haskell-lexer          >     | ^^^^^^^
haskell-lexer          >
haskell-lexer          > Language\Haskell\Lexer\Lex.hs:805:1: warning: [-Wunused-top-binds]
haskell-lexer          >     Defined but not used: `start53'
haskell-lexer          >     |
haskell-lexer          > 805 | start53 is = state53 (\ as is -> gotError as is) "" is
haskell-lexer          >     | ^^^^^^^
haskell-lexer          >
haskell-lexer          > Language\Haskell\Lexer\Lex.hs:814:1: warning: [-Wunused-top-binds]
haskell-lexer          >     Defined but not used: `start54'
haskell-lexer          >     |
haskell-lexer          > 814 | start54 is = state54 (\ as is -> gotError as is) "" is
haskell-lexer          >     | ^^^^^^^
haskell-lexer          >
haskell-lexer          > Language\Haskell\Lexer\Lex.hs:823:1: warning: [-Wunused-top-binds]
haskell-lexer          >     Defined but not used: `start55'
haskell-lexer          >     |
haskell-lexer          > 823 | start55 is = state55 (\ as is -> gotError as is) "" is
haskell-lexer          >     | ^^^^^^^
haskell-lexer          >
haskell-lexer          > Language\Haskell\Lexer\Lex.hs:836:1: warning: [-Wunused-top-binds]
haskell-lexer          >     Defined but not used: `start56'
haskell-lexer          >     |
haskell-lexer          > 836 | start56 is = state56 (\ as is -> gotError as is) "" is
haskell-lexer          >     | ^^^^^^^
haskell-lexer          >
haskell-lexer          > Language\Haskell\Lexer\Lex.hs:845:1: warning: [-Wunused-top-binds]
haskell-lexer          >     Defined but not used: `start57'
haskell-lexer          >     |
haskell-lexer          > 845 | start57 is = state57 (\ as is -> gotError as is) "" is
haskell-lexer          >     | ^^^^^^^
haskell-lexer          >
haskell-lexer          > Language\Haskell\Lexer\Lex.hs:854:1: warning: [-Wunused-top-binds]
haskell-lexer          >     Defined but not used: `start58'
haskell-lexer          >     |
haskell-lexer          > 854 | start58 is = state58 (\ as is -> gotError as is) "" is
haskell-lexer          >     | ^^^^^^^
haskell-lexer          >
haskell-lexer          > Language\Haskell\Lexer\Lex.hs:863:1: warning: [-Wunused-top-binds]
haskell-lexer          >     Defined but not used: `start59'
haskell-lexer          >     |
haskell-lexer          > 863 | start59 is = state59 (\ as is -> gotError as is) "" is
haskell-lexer          >     | ^^^^^^^
haskell-lexer          >
haskell-lexer          > Language\Haskell\Lexer\Lex.hs:873:1: warning: [-Wunused-top-binds]
haskell-lexer          >     Defined but not used: `start60'
haskell-lexer          >     |
haskell-lexer          > 873 | start60 is = state60 (\ as is -> gotError as is) "" is
haskell-lexer          >     | ^^^^^^^
haskell-lexer          >
haskell-lexer          > Language\Haskell\Lexer\Lex.hs:883:1: warning: [-Wunused-top-binds]
haskell-lexer          >     Defined but not used: `start61'
haskell-lexer          >     |
haskell-lexer          > 883 | start61 is = state61 (\ as is -> gotError as is) "" is
haskell-lexer          >     | ^^^^^^^
haskell-lexer          >
haskell-lexer          > Language\Haskell\Lexer\Lex.hs:892:1: warning: [-Wunused-top-binds]
haskell-lexer          >     Defined but not used: `start62'
haskell-lexer          >     |
haskell-lexer          > 892 | start62 is = state62 (\ as is -> gotError as is) "" is
haskell-lexer          >     | ^^^^^^^
haskell-lexer          >
haskell-lexer          > Language\Haskell\Lexer\Lex.hs:901:1: warning: [-Wunused-top-binds]
haskell-lexer          >     Defined but not used: `start63'
haskell-lexer          >     |
haskell-lexer          > 901 | start63 is = state63 (\ as is -> gotError as is) "" is
haskell-lexer          >     | ^^^^^^^
haskell-lexer          >
haskell-lexer          > Language\Haskell\Lexer\Lex.hs:911:1: warning: [-Wunused-top-binds]
haskell-lexer          >     Defined but not used: `start64'
haskell-lexer          >     |
haskell-lexer          > 911 | start64 is = state64 (\ as is -> gotError as is) "" is
haskell-lexer          >     | ^^^^^^^
haskell-lexer          >
haskell-lexer          > Language\Haskell\Lexer\Lex.hs:924:1: warning: [-Wunused-top-binds]
haskell-lexer          >     Defined but not used: `start65'
haskell-lexer          >     |
haskell-lexer          > 924 | start65 is = state65 (\ as is -> gotError as is) "" is
haskell-lexer          >     | ^^^^^^^
haskell-lexer          >
haskell-lexer          > Language\Haskell\Lexer\Lex.hs:934:1: warning: [-Wunused-top-binds]
haskell-lexer          >     Defined but not used: `start66'
haskell-lexer          >     |
haskell-lexer          > 934 | start66 is = state66 (\ as is -> gotError as is) "" is
haskell-lexer          >     | ^^^^^^^
haskell-lexer          >
haskell-lexer          > Language\Haskell\Lexer\Lex.hs:943:1: warning: [-Wunused-top-binds]
haskell-lexer          >     Defined but not used: `start67'
haskell-lexer          >     |
haskell-lexer          > 943 | start67 is = state67 (\ as is -> gotError as is) "" is
haskell-lexer          >     | ^^^^^^^
haskell-lexer          >
haskell-lexer          > Language\Haskell\Lexer\Lex.hs:952:1: warning: [-Wunused-top-binds]
haskell-lexer          >     Defined but not used: `start68'
haskell-lexer          >     |
haskell-lexer          > 952 | start68 is = state68 (\ as is -> gotError as is) "" is
haskell-lexer          >     | ^^^^^^^
haskell-lexer          >
haskell-lexer          > Language\Haskell\Lexer\Lex.hs:989:1: warning: [-Wunused-top-binds]
haskell-lexer          >     Defined but not used: `start69'
haskell-lexer          >     |
haskell-lexer          > 989 | start69 is = state69 (\ as is -> gotError as is) "" is
haskell-lexer          >     | ^^^^^^^
haskell-lexer          >
haskell-lexer          > Language\Haskell\Lexer\Lex.hs:1000:1: warning: [-Wunused-top-binds]
haskell-lexer          >     Defined but not used: `start70'
haskell-lexer          >      |
haskell-lexer          > 1000 | start70 is = state70 (\ as is -> gotError as is) "" is
haskell-lexer          >      | ^^^^^^^
haskell-lexer          >
haskell-lexer          > Language\Haskell\Lexer\Lex.hs:1012:1: warning: [-Wunused-top-binds]
haskell-lexer          >     Defined but not used: `start71'
haskell-lexer          >      |
haskell-lexer          > 1012 | start71 is = state71 (\ as is -> gotError as is) "" is
haskell-lexer          >      | ^^^^^^^
haskell-lexer          >
haskell-lexer          > Language\Haskell\Lexer\Lex.hs:1036:1: warning: [-Wunused-top-binds]
haskell-lexer          >     Defined but not used: `start72'
haskell-lexer          >      |
haskell-lexer          > 1036 | start72 is = state72 (\ as is -> gotError as is) "" is
haskell-lexer          >      | ^^^^^^^
haskell-lexer          >
haskell-lexer          > Language\Haskell\Lexer\Lex.hs:1194:1: warning: [-Wunused-top-binds]
haskell-lexer          >     Defined but not used: `start82'
haskell-lexer          >      |
haskell-lexer          > 1194 | start82 is = state82 (\ as is -> gotError as is) "" is
haskell-lexer          >      | ^^^^^^^
haskell-lexer          >
haskell-lexer          > Language\Haskell\Lexer\Lex.hs:1220:1: warning: [-Wunused-top-binds]
haskell-lexer          >     Defined but not used: `start84'
haskell-lexer          >      |
haskell-lexer          > 1220 | start84 is = state84 (\ as is -> gotError as is) "" is
haskell-lexer          >      | ^^^^^^^
haskell-lexer          >
haskell-lexer          > Language\Haskell\Lexer\Lex.hs:1234:1: warning: [-Wunused-top-binds]
haskell-lexer          >     Defined but not used: `start85'
haskell-lexer          >      |
haskell-lexer          > 1234 | start85 is = state85 (\ as is -> gotError as is) "" is
haskell-lexer          >      | ^^^^^^^
haskell-lexer          >
haskell-lexer          > Language\Haskell\Lexer\Lex.hs:1271:1: warning: [-Wunused-top-binds]
haskell-lexer          >     Defined but not used: `start88'
haskell-lexer          >      |
haskell-lexer          > 1271 | start88 is = state88 (\ as is -> gotError as is) "" is
haskell-lexer          >      | ^^^^^^^
haskell-lexer          >
haskell-lexer          > Language\Haskell\Lexer\Lex.hs:1293:1: warning: [-Wunused-top-binds]
haskell-lexer          >     Defined but not used: `start90'
haskell-lexer          >      |
haskell-lexer          > 1293 | start90 is = state90 (\ as is -> gotError as is) "" is
haskell-lexer          >      | ^^^^^^^
haskell-lexer          >
haskell-lexer          > Language\Haskell\Lexer\Lex.hs:1485:1: warning: [-Wunused-top-binds]
colour                 > [ 8 of 14] Compiling Data.Colour.RGBSpace.HSV
haskell-lexer          >     Defined but not used: `start98'
haskell-lexer          >      |
haskell-lexer          > 1485 | start98 is = state98 (\ as is -> gotError as is) "" is
haskell-lexer          >      | ^^^^^^^
haskell-lexer          >
haskell-lexer          > Language\Haskell\Lexer\Lex.hs:1592:1: warning: [-Wunused-top-binds]
haskell-lexer          >     Defined but not used: `start101'
haskell-lexer          >      |
haskell-lexer          > 1592 | start101 is = state101 (\ as is -> gotError as is) "" is
haskell-lexer          >      | ^^^^^^^^
haskell-lexer          >
haskell-lexer          > Language\Haskell\Lexer\Lex.hs:1614:1: warning: [-Wunused-top-binds]
haskell-lexer          >     Defined but not used: `start102'
haskell-lexer          >      |
haskell-lexer          > 1614 | start102 is = state102 (\ as is -> gotError as is) "" is
haskell-lexer          >      | ^^^^^^^^
haskell-lexer          >
haskell-lexer          > Language\Haskell\Lexer\Lex.hs:1658:1: warning: [-Wunused-top-binds]
haskell-lexer          >     Defined but not used: `start104'
haskell-lexer          >      |
haskell-lexer          > 1658 | start104 is = state104 (\ as is -> gotError as is) "" is
haskell-lexer          >      | ^^^^^^^^
haskell-lexer          >
haskell-lexer          > Language\Haskell\Lexer\Lex.hs:1702:1: warning: [-Wunused-top-binds]
haskell-lexer          >     Defined but not used: `start106'
haskell-lexer          >      |
haskell-lexer          > 1702 | start106 is = state106 (\ as is -> gotError as is) "" is
haskell-lexer          >      | ^^^^^^^^
haskell-lexer          >
haskell-lexer          > Language\Haskell\Lexer\Lex.hs:1746:1: warning: [-Wunused-top-binds]
haskell-lexer          >     Defined but not used: `start108'
haskell-lexer          >      |
haskell-lexer          > 1746 | start108 is = state108 (\ as is -> gotError as is) "" is
haskell-lexer          >      | ^^^^^^^^
haskell-lexer          >
haskell-lexer          > Language\Haskell\Lexer\Lex.hs:1802:1: warning: [-Wunused-top-binds]
haskell-lexer          >     Defined but not used: `start110'
haskell-lexer          >      |
haskell-lexer          > 1802 | start110 is = state110 (\ as is -> gotError as is) "" is
haskell-lexer          >      | ^^^^^^^^
haskell-lexer          >
haskell-lexer          > Language\Haskell\Lexer\Lex.hs:2716:1: warning: [-Wunused-top-binds]
haskell-lexer          >     Defined but not used: `start136'
haskell-lexer          >      |
haskell-lexer          > 2716 | start136 is = state136 (\ as is -> gotError as is) "" is
haskell-lexer          >      | ^^^^^^^^
haskell-lexer          >
haskell-lexer          > Language\Haskell\Lexer\Lex.hs:2822:1: warning: [-Wunused-top-binds]
haskell-lexer          >     Defined but not used: `start139'
haskell-lexer          >      |
haskell-lexer          > 2822 | start139 is = state139 (\ as is -> gotError as is) "" is
haskell-lexer          >      | ^^^^^^^^
haskell-lexer          >
haskell-lexer          > Language\Haskell\Lexer\Lex.hs:3650:1: warning: [-Wunused-top-binds]
haskell-lexer          >     Defined but not used: `start162'
haskell-lexer          >      |
haskell-lexer          > 3650 | start162 is = state162 (\ as is -> gotError as is) "" is
haskell-lexer          >      | ^^^^^^^^
haskell-lexer          >
haskell-lexer          > Language\Haskell\Lexer\Lex.hs:3684:1: warning: [-Wunused-top-binds]
haskell-lexer          >     Defined but not used: `start163'
haskell-lexer          >      |
haskell-lexer          > 3684 | start163 is = state163 (\ as is -> gotError as is) "" is
haskell-lexer          >      | ^^^^^^^^
haskell-lexer          >
haskell-lexer          > Language\Haskell\Lexer\Lex.hs:3740:1: warning: [-Wunused-top-binds]
haskell-lexer          >     Defined but not used: `start164'
haskell-lexer          >      |
haskell-lexer          > 3740 | start164 is = state164 (\ as is -> gotError as is) "" is
haskell-lexer          >      | ^^^^^^^^
haskell-lexer          >
haskell-lexer          > Language\Haskell\Lexer\Lex.hs:3774:1: warning: [-Wunused-top-binds]
haskell-lexer          >     Defined but not used: `start165'
haskell-lexer          >      |
haskell-lexer          > 3774 | start165 is = state165 (\ as is -> gotError as is) "" is
haskell-lexer          >      | ^^^^^^^^
haskell-lexer          >
haskell-lexer          > Language\Haskell\Lexer\Lex.hs:3808:1: warning: [-Wunused-top-binds]
haskell-lexer          >     Defined but not used: `start166'
haskell-lexer          >      |
haskell-lexer          > 3808 | start166 is = state166 (\ as is -> gotError as is) "" is
haskell-lexer          >      | ^^^^^^^^
haskell-lexer          >
haskell-lexer          > Language\Haskell\Lexer\Lex.hs:3818:1: warning: [-Wunused-top-binds]
haskell-lexer          >     Defined but not used: `start169'
haskell-lexer          >      |
haskell-lexer          > 3818 | start169 is = state169 (\ as is -> gotError as is) "" is
haskell-lexer          >      | ^^^^^^^^
haskell-lexer          >
haskell-lexer          > Language\Haskell\Lexer\Lex.hs:3832:1: warning: [-Wunused-top-binds]
haskell-lexer          >     Defined but not used: `start173'
haskell-lexer          >      |
haskell-lexer          > 3832 | start173 is = state173 (\ as is -> gotError as is) "" is
haskell-lexer          >      | ^^^^^^^^
haskell-lexer          >
haskell-lexer          > Language\Haskell\Lexer\Lex.hs:3868:1: warning: [-Wunused-top-binds]
haskell-lexer          >     Defined but not used: `start174'
haskell-lexer          >      |
haskell-lexer          > 3868 | start174 is = state174 (\ as is -> gotError as is) "" is
haskell-lexer          >      | ^^^^^^^^
haskell-lexer          >
haskell-lexer          > Language\Haskell\Lexer\Lex.hs:3903:1: warning: [-Wunused-top-binds]
haskell-lexer          >     Defined but not used: `start175'
haskell-lexer          >      |
haskell-lexer          > 3903 | start175 is = state175 (\ as is -> gotError as is) "" is
haskell-lexer          >      | ^^^^^^^^
haskell-lexer          >
haskell-lexer          > Language\Haskell\Lexer\Lex.hs:3938:1: warning: [-Wunused-top-binds]
haskell-lexer          >     Defined but not used: `start176'
haskell-lexer          >      |
haskell-lexer          > 3938 | start176 is = state176 (\ as is -> gotError as is) "" is
haskell-lexer          >      | ^^^^^^^^
haskell-lexer          >
haskell-lexer          > Language\Haskell\Lexer\Lex.hs:3973:1: warning: [-Wunused-top-binds]
haskell-lexer          >     Defined but not used: `start177'
haskell-lexer          >      |
haskell-lexer          > 3973 | start177 is = state177 (\ as is -> gotError as is) "" is
haskell-lexer          >      | ^^^^^^^^
haskell-lexer          >
haskell-lexer          > Language\Haskell\Lexer\Lex.hs:4008:1: warning: [-Wunused-top-binds]
haskell-lexer          >     Defined but not used: `start178'
haskell-lexer          >      |
haskell-lexer          > 4008 | start178 is = state178 (\ as is -> gotError as is) "" is
haskell-lexer          >      | ^^^^^^^^
haskell-lexer          >
haskell-lexer          > Language\Haskell\Lexer\Lex.hs:4043:1: warning: [-Wunused-top-binds]
haskell-lexer          >     Defined but not used: `start179'
haskell-lexer          >      |
haskell-lexer          > 4043 | start179 is = state179 (\ as is -> gotError as is) "" is
haskell-lexer          >      | ^^^^^^^^
haskell-lexer          >
haskell-lexer          > Language\Haskell\Lexer\Lex.hs:4080:1: warning: [-Wunused-top-binds]
haskell-lexer          >     Defined but not used: `start180'
haskell-lexer          >      |
haskell-lexer          > 4080 | start180 is = state180 (\ as is -> gotError as is) "" is
haskell-lexer          >      | ^^^^^^^^
haskell-lexer          >
haskell-lexer          > Language\Haskell\Lexer\Lex.hs:4115:1: warning: [-Wunused-top-binds]
haskell-lexer          >     Defined but not used: `start181'
haskell-lexer          >      |
haskell-lexer          > 4115 | start181 is = state181 (\ as is -> gotError as is) "" is
haskell-lexer          >      | ^^^^^^^^
haskell-lexer          >
haskell-lexer          > Language\Haskell\Lexer\Lex.hs:4150:1: warning: [-Wunused-top-binds]
haskell-lexer          >     Defined but not used: `start182'
haskell-lexer          >      |
haskell-lexer          > 4150 | start182 is = state182 (\ as is -> gotError as is) "" is
haskell-lexer          >      | ^^^^^^^^
haskell-lexer          >
haskell-lexer          > Language\Haskell\Lexer\Lex.hs:4186:1: warning: [-Wunused-top-binds]
haskell-lexer          >     Defined but not used: `start183'
haskell-lexer          >      |
haskell-lexer          > 4186 | start183 is = state183 (\ as is -> gotError as is) "" is
haskell-lexer          >      | ^^^^^^^^
haskell-lexer          >
haskell-lexer          > Language\Haskell\Lexer\Lex.hs:4221:1: warning: [-Wunused-top-binds]
haskell-lexer          >     Defined but not used: `start184'
haskell-lexer          >      |
haskell-lexer          > 4221 | start184 is = state184 (\ as is -> gotError as is) "" is
haskell-lexer          >      | ^^^^^^^^
haskell-lexer          >
haskell-lexer          > Language\Haskell\Lexer\Lex.hs:4256:1: warning: [-Wunused-top-binds]
haskell-lexer          >     Defined but not used: `start185'
haskell-lexer          >      |
haskell-lexer          > 4256 | start185 is = state185 (\ as is -> gotError as is) "" is
haskell-lexer          >      | ^^^^^^^^
haskell-lexer          >
haskell-lexer          > Language\Haskell\Lexer\Lex.hs:4291:1: warning: [-Wunused-top-binds]
haskell-lexer          >     Defined but not used: `start186'
haskell-lexer          >      |
haskell-lexer          > 4291 | start186 is = state186 (\ as is -> gotError as is) "" is
haskell-lexer          >      | ^^^^^^^^
haskell-lexer          >
haskell-lexer          > Language\Haskell\Lexer\Lex.hs:4326:1: warning: [-Wunused-top-binds]
haskell-lexer          >     Defined but not used: `start187'
haskell-lexer          >      |
haskell-lexer          > 4326 | start187 is = state187 (\ as is -> gotError as is) "" is
haskell-lexer          >      | ^^^^^^^^
haskell-lexer          >
haskell-lexer          > Language\Haskell\Lexer\Lex.hs:4361:1: warning: [-Wunused-top-binds]
haskell-lexer          >     Defined but not used: `start188'
haskell-lexer          >      |
haskell-lexer          > 4361 | start188 is = state188 (\ as is -> gotError as is) "" is
haskell-lexer          >      | ^^^^^^^^
haskell-lexer          >
haskell-lexer          > Language\Haskell\Lexer\Lex.hs:4396:1: warning: [-Wunused-top-binds]
haskell-lexer          >     Defined but not used: `start189'
haskell-lexer          >      |
haskell-lexer          > 4396 | start189 is = state189 (\ as is -> gotError as is) "" is
haskell-lexer          >      | ^^^^^^^^
haskell-lexer          >
haskell-lexer          > Language\Haskell\Lexer\Lex.hs:4431:1: warning: [-Wunused-top-binds]
haskell-lexer          >     Defined but not used: `start190'
haskell-lexer          >      |
haskell-lexer          > 4431 | start190 is = state190 (\ as is -> gotError as is) "" is
haskell-lexer          >      | ^^^^^^^^
haskell-lexer          >
haskell-lexer          > Language\Haskell\Lexer\Lex.hs:4466:1: warning: [-Wunused-top-binds]
haskell-lexer          >     Defined but not used: `start191'
haskell-lexer          >      |
haskell-lexer          > 4466 | start191 is = state191 (\ as is -> gotError as is) "" is
haskell-lexer          >      | ^^^^^^^^
haskell-lexer          >
haskell-lexer          > Language\Haskell\Lexer\Lex.hs:4501:1: warning: [-Wunused-top-binds]
haskell-lexer          >     Defined but not used: `start192'
haskell-lexer          >      |
haskell-lexer          > 4501 | start192 is = state192 (\ as is -> gotError as is) "" is
haskell-lexer          >      | ^^^^^^^^
haskell-lexer          >
haskell-lexer          > Language\Haskell\Lexer\Lex.hs:4536:1: warning: [-Wunused-top-binds]
haskell-lexer          >     Defined but not used: `start193'
haskell-lexer          >      |
haskell-lexer          > 4536 | start193 is = state193 (\ as is -> gotError as is) "" is
haskell-lexer          >      | ^^^^^^^^
haskell-lexer          >
haskell-lexer          > Language\Haskell\Lexer\Lex.hs:4573:1: warning: [-Wunused-top-binds]
haskell-lexer          >     Defined but not used: `start194'
haskell-lexer          >      |
haskell-lexer          > 4573 | start194 is = state194 (\ as is -> gotError as is) "" is
haskell-lexer          >      | ^^^^^^^^
haskell-lexer          >
haskell-lexer          > Language\Haskell\Lexer\Lex.hs:4608:1: warning: [-Wunused-top-binds]
haskell-lexer          >     Defined but not used: `start195'
haskell-lexer          >      |
haskell-lexer          > 4608 | start195 is = state195 (\ as is -> gotError as is) "" is
haskell-lexer          >      | ^^^^^^^^
haskell-lexer          >
haskell-lexer          > Language\Haskell\Lexer\Lex.hs:4643:1: warning: [-Wunused-top-binds]
haskell-lexer          >     Defined but not used: `start196'
haskell-lexer          >      |
haskell-lexer          > 4643 | start196 is = state196 (\ as is -> gotError as is) "" is
haskell-lexer          >      | ^^^^^^^^
haskell-lexer          >
haskell-lexer          > Language\Haskell\Lexer\Lex.hs:4678:1: warning: [-Wunused-top-binds]
haskell-lexer          >     Defined but not used: `start197'
haskell-lexer          >      |
haskell-lexer          > 4678 | start197 is = state197 (\ as is -> gotError as is) "" is
haskell-lexer          >      | ^^^^^^^^
haskell-lexer          >
haskell-lexer          > Language\Haskell\Lexer\Lex.hs:4714:1: warning: [-Wunused-top-binds]
haskell-lexer          >     Defined but not used: `start198'
haskell-lexer          >      |
haskell-lexer          > 4714 | start198 is = state198 (\ as is -> gotError as is) "" is
haskell-lexer          >      | ^^^^^^^^
haskell-lexer          >
haskell-lexer          > Language\Haskell\Lexer\Lex.hs:4749:1: warning: [-Wunused-top-binds]
haskell-lexer          >     Defined but not used: `start199'
haskell-lexer          >      |
haskell-lexer          > 4749 | start199 is = state199 (\ as is -> gotError as is) "" is
haskell-lexer          >      | ^^^^^^^^
haskell-lexer          >
haskell-lexer          > Language\Haskell\Lexer\Lex.hs:4784:1: warning: [-Wunused-top-binds]
haskell-lexer          >     Defined but not used: `start200'
haskell-lexer          >      |
haskell-lexer          > 4784 | start200 is = state200 (\ as is -> gotError as is) "" is
haskell-lexer          >      | ^^^^^^^^
haskell-lexer          >
haskell-lexer          > Language\Haskell\Lexer\Lex.hs:4820:1: warning: [-Wunused-top-binds]
haskell-lexer          >     Defined but not used: `start201'
haskell-lexer          >      |
haskell-lexer          > 4820 | start201 is = state201 (\ as is -> gotError as is) "" is
haskell-lexer          >      | ^^^^^^^^
haskell-lexer          >
haskell-lexer          > Language\Haskell\Lexer\Lex.hs:4855:1: warning: [-Wunused-top-binds]
haskell-lexer          >     Defined but not used: `start202'
haskell-lexer          >      |
haskell-lexer          > 4855 | start202 is = state202 (\ as is -> gotError as is) "" is
haskell-lexer          >      | ^^^^^^^^
haskell-lexer          >
haskell-lexer          > Language\Haskell\Lexer\Lex.hs:4890:1: warning: [-Wunused-top-binds]
haskell-lexer          >     Defined but not used: `start203'
haskell-lexer          >      |
haskell-lexer          > 4890 | start203 is = state203 (\ as is -> gotError as is) "" is
haskell-lexer          >      | ^^^^^^^^
haskell-lexer          >
haskell-lexer          > Language\Haskell\Lexer\Lex.hs:4925:1: warning: [-Wunused-top-binds]
haskell-lexer          >     Defined but not used: `start204'
haskell-lexer          >      |
haskell-lexer          > 4925 | start204 is = state204 (\ as is -> gotError as is) "" is
haskell-lexer          >      | ^^^^^^^^
haskell-lexer          >
haskell-lexer          > Language\Haskell\Lexer\Lex.hs:4960:1: warning: [-Wunused-top-binds]
haskell-lexer          >     Defined but not used: `start205'
haskell-lexer          >      |
haskell-lexer          > 4960 | start205 is = state205 (\ as is -> gotError as is) "" is
haskell-lexer          >      | ^^^^^^^^
haskell-lexer          >
haskell-lexer          > Language\Haskell\Lexer\Lex.hs:4995:1: warning: [-Wunused-top-binds]
haskell-lexer          >     Defined but not used: `start206'
haskell-lexer          >      |
haskell-lexer          > 4995 | start206 is = state206 (\ as is -> gotError as is) "" is
haskell-lexer          >      | ^^^^^^^^
haskell-lexer          >
haskell-lexer          > Language\Haskell\Lexer\Lex.hs:5030:1: warning: [-Wunused-top-binds]
haskell-lexer          >     Defined but not used: `start207'
haskell-lexer          >      |
haskell-lexer          > 5030 | start207 is = state207 (\ as is -> gotError as is) "" is
haskell-lexer          >      | ^^^^^^^^
haskell-lexer          >
haskell-lexer          > Language\Haskell\Lexer\Lex.hs:5065:1: warning: [-Wunused-top-binds]
haskell-lexer          >     Defined but not used: `start208'
haskell-lexer          >      |
haskell-lexer          > 5065 | start208 is = state208 (\ as is -> gotError as is) "" is
haskell-lexer          >      | ^^^^^^^^
haskell-lexer          >
haskell-lexer          > Language\Haskell\Lexer\Lex.hs:5100:1: warning: [-Wunused-top-binds]
haskell-lexer          >     Defined but not used: `start209'
haskell-lexer          >      |
haskell-lexer          > 5100 | start209 is = state209 (\ as is -> gotError as is) "" is
haskell-lexer          >      | ^^^^^^^^
haskell-lexer          >
haskell-lexer          > Language\Haskell\Lexer\Lex.hs:5135:1: warning: [-Wunused-top-binds]
haskell-lexer          >     Defined but not used: `start210'
haskell-lexer          >      |
haskell-lexer          > 5135 | start210 is = state210 (\ as is -> gotError as is) "" is
haskell-lexer          >      | ^^^^^^^^
haskell-lexer          >
haskell-lexer          > Language\Haskell\Lexer\Lex.hs:5170:1: warning: [-Wunused-top-binds]
hspec-discover         > [2 of 2] Compiling Paths_hspec_discover
haskell-lexer          >     Defined but not used: `start211'
haskell-lexer          >      |
haskell-lexer          > 5170 | start211 is = state211 (\ as is -> gotError as is) "" is
haskell-lexer          >      | ^^^^^^^^
haskell-lexer          >
haskell-lexer          > Language\Haskell\Lexer\Lex.hs:5205:1: warning: [-Wunused-top-binds]
haskell-lexer          >     Defined but not used: `start212'
haskell-lexer          >      |
haskell-lexer          > 5205 | start212 is = state212 (\ as is -> gotError as is) "" is
haskell-lexer          >      | ^^^^^^^^
haskell-lexer          >
haskell-lexer          > Language\Haskell\Lexer\Lex.hs:5240:1: warning: [-Wunused-top-binds]
haskell-lexer          >     Defined but not used: `start213'
haskell-lexer          >      |
haskell-lexer          > 5240 | start213 is = state213 (\ as is -> gotError as is) "" is
haskell-lexer          >      | ^^^^^^^^
haskell-lexer          >
haskell-lexer          > Language\Haskell\Lexer\Lex.hs:5275:1: warning: [-Wunused-top-binds]
haskell-lexer          >     Defined but not used: `start214'
haskell-lexer          >      |
haskell-lexer          > 5275 | start214 is = state214 (\ as is -> gotError as is) "" is
haskell-lexer          >      | ^^^^^^^^
haskell-lexer          >
haskell-lexer          > Language\Haskell\Lexer\Lex.hs:5310:1: warning: [-Wunused-top-binds]
haskell-lexer          >     Defined but not used: `start215'
haskell-lexer          >      |
haskell-lexer          > 5310 | start215 is = state215 (\ as is -> gotError as is) "" is
haskell-lexer          >      | ^^^^^^^^
haskell-lexer          >
haskell-lexer          > Language\Haskell\Lexer\Lex.hs:5345:1: warning: [-Wunused-top-binds]
haskell-lexer          >     Defined but not used: `start216'
haskell-lexer          >      |
haskell-lexer          > 5345 | start216 is = state216 (\ as is -> gotError as is) "" is
haskell-lexer          >      | ^^^^^^^^
haskell-lexer          >
haskell-lexer          > Language\Haskell\Lexer\Lex.hs:5381:1: warning: [-Wunused-top-binds]
haskell-lexer          >     Defined but not used: `start217'
haskell-lexer          >      |
haskell-lexer          > 5381 | start217 is = state217 (\ as is -> gotError as is) "" is
haskell-lexer          >      | ^^^^^^^^
haskell-lexer          >
haskell-lexer          > Language\Haskell\Lexer\Lex.hs:5416:1: warning: [-Wunused-top-binds]
haskell-lexer          >     Defined but not used: `start218'
haskell-lexer          >      |
haskell-lexer          > 5416 | start218 is = state218 (\ as is -> gotError as is) "" is
haskell-lexer          >      | ^^^^^^^^
haskell-lexer          >
haskell-lexer          > Language\Haskell\Lexer\Lex.hs:5451:1: warning: [-Wunused-top-binds]
haskell-lexer          >     Defined but not used: `start219'
haskell-lexer          >      |
haskell-lexer          > 5451 | start219 is = state219 (\ as is -> gotError as is) "" is
haskell-lexer          >      | ^^^^^^^^
haskell-lexer          >
haskell-lexer          > Language\Haskell\Lexer\Lex.hs:5486:1: warning: [-Wunused-top-binds]
haskell-lexer          >     Defined but not used: `start220'
haskell-lexer          >      |
haskell-lexer          > 5486 | start220 is = state220 (\ as is -> gotError as is) "" is
haskell-lexer          >      | ^^^^^^^^
haskell-lexer          >
haskell-lexer          > Language\Haskell\Lexer\Lex.hs:5521:1: warning: [-Wunused-top-binds]
haskell-lexer          >     Defined but not used: `start221'
haskell-lexer          >      |
haskell-lexer          > 5521 | start221 is = state221 (\ as is -> gotError as is) "" is
haskell-lexer          >      | ^^^^^^^^
colour                 > [ 9 of 14] Compiling Data.Colour.SRGB.Linear
colour                 > [10 of 14] Compiling Data.Colour.RGBSpace
mintty                 > copy/register
mintty                 > Installing library in C:\Users\schwe\AppData\Roaming\stack\snapshots\0bf3cf80\lib\x86_64-windows-ghc-9.4.8\mintty-0.1.4-8vyqfHW1t6CEjNWaBs6Xax
HUnit                  > Configuring HUnit-1.6.2.0...
colour                 > [11 of 14] Compiling Data.Colour.SRGB
mintty                 > Registering library for mintty-0.1.4..
splitmix               > copy/register
hspec-discover         > [3 of 3] Linking .stack-work\dist\f1a1ac53\build\hspec-discover\hspec-discover.exe
splitmix               > Installing library in C:\Users\schwe\AppData\Roaming\stack\snapshots\0bf3cf80\lib\x86_64-windows-ghc-9.4.8\splitmix-0.1.0.5-7l6HL2Ks2U1EQtiqxLd141
colour                 > [12 of 14] Compiling Data.Colour
splitmix               > Registering library for splitmix-0.1.0.5..
colour                 > [13 of 14] Compiling Data.Colour.Names
HUnit                  > build with ghc-9.4.8
HUnit                  > Preprocessing library for HUnit-1.6.2.0..
HUnit                  > Building library for HUnit-1.6.2.0..
HUnit                  > [1 of 6] Compiling Paths_HUnit
random                 > configure
HUnit                  > [2 of 6] Compiling Test.HUnit.Lang
hspec-discover         > copy/register
hspec-discover         > Installing library in C:\Users\schwe\AppData\Roaming\stack\snapshots\0bf3cf80\lib\x86_64-windows-ghc-9.4.8\hspec-discover-2.10.10-9mQTsm0yW008y2RPwu9uwk
hspec-discover         > Installing executable hspec-discover in C:\Users\schwe\AppData\Roaming\stack\snapshots\0bf3cf80\bin
primitive              > [ 3 of 13] Compiling Data.Primitive.MVar
HUnit                  > [3 of 6] Compiling Test.HUnit.Base
colour                 > [14 of 14] Compiling Data.Colour.CIE
primitive              > [ 4 of 13] Compiling Data.Primitive.MachDeps
random                 > Configuring random-1.2.1.1...
primitive              > [ 5 of 13] Compiling Data.Primitive.Internal.Operations
primitive              > [ 6 of 13] Compiling Data.Primitive.MutVar
primitive              > [ 7 of 13] Compiling Data.Primitive.SmallArray
hspec-discover         > Registering library for hspec-discover-2.10.10..
colour                 > copy/register
colour                 > Installing library in C:\Users\schwe\AppData\Roaming\stack\snapshots\0bf3cf80\lib\x86_64-windows-ghc-9.4.8\colour-2.3.6-5Hdg5NZuPkoIAWxSFkdA0e
random                 > build with ghc-9.4.8
HUnit                  > [4 of 6] Compiling Test.HUnit.Terminal
random                 > Preprocessing library for random-1.2.1.1..
random                 > Building library for random-1.2.1.1..
colour                 > Registering library for colour-2.3.6..
HUnit                  > [5 of 6] Compiling Test.HUnit.Text
HUnit                  > [6 of 6] Compiling Test.HUnit
random                 > [1 of 4] Compiling System.Random.GFinite
prettyprinter          > [ 3 of 28] Compiling Prettyprinter.Render.String
prettyprinter          > [ 4 of 28] Compiling Data.Text.Prettyprint.Doc.Render.String
prettyprinter          > [ 5 of 28] Compiling Prettyprinter.Internal.Type
HUnit                  > copy/register
prettyprinter          > [ 6 of 28] Compiling Data.Text.Prettyprint.Doc.Internal.Type
HUnit                  > Installing library in C:\Users\schwe\AppData\Roaming\stack\snapshots\0bf3cf80\lib\x86_64-windows-ghc-9.4.8\HUnit-1.6.2.0-FtNBjuJzJ5m883vurrDbtq
prettyprinter          > [ 7 of 28] Compiling Prettyprinter.Internal.Debug
ansi-terminal-types    > configure
prettyprinter          > [ 8 of 28] Compiling Data.Text.Prettyprint.Doc.Internal.Debug
HUnit                  > Registering library for HUnit-1.6.2.0..
prettyprinter          > [ 9 of 28] Compiling Data.Text.Prettyprint.Doc.Internal
prettyprinter          > [10 of 28] Compiling Data.Text.Prettyprint.Doc.Render.Util.Panic
prettyprinter          > [11 of 28] Compiling Prettyprinter.Render.Util.StackMachine
prettyprinter          > [12 of 28] Compiling Data.Text.Prettyprint.Doc.Render.Util.StackMachine
ansi-terminal-types    > Configuring ansi-terminal-types-0.11.5...
prettyprinter          > [13 of 28] Compiling Prettyprinter.Symbols.Ascii
hspec-expectations     > configure
prettyprinter          > [14 of 28] Compiling Prettyprinter
primitive              > [ 8 of 13] Compiling Data.Primitive.Types
prettyprinter          > [15 of 28] Compiling Prettyprinter.Render.Util.SimpleDocTree
ansi-terminal-types    > build with ghc-9.4.8
ansi-terminal-types    > Preprocessing library for ansi-terminal-types-0.11.5..
ansi-terminal-types    > Building library for ansi-terminal-types-0.11.5..
hspec-expectations     > Configuring hspec-expectations-0.8.2...
ansi-terminal-types    > [1 of 1] Compiling System.Console.ANSI.Types
primitive              > [ 9 of 13] Compiling Data.Primitive.ByteArray
prettyprinter          > [16 of 28] Compiling Data.Text.Prettyprint.Doc.Render.Util.SimpleDocTree
prettyprinter          > [17 of 28] Compiling Prettyprinter.Render.Tutorials.TreeRenderingTutorial
hspec-expectations     > build with ghc-9.4.8
hspec-expectations     > Preprocessing library for hspec-expectations-0.8.2..
hspec-expectations     > Building library for hspec-expectations-0.8.2..
random                 > [2 of 4] Compiling System.Random.Internal
primitive              > [10 of 13] Compiling Data.Primitive.PrimArray
hspec-expectations     > [1 of 4] Compiling Paths_hspec_expectations
hspec-expectations     > [2 of 4] Compiling Test.Hspec.Expectations.Contrib
hspec-expectations     > [3 of 4] Compiling Test.Hspec.Expectations.Matcher
prettyprinter          > [18 of 28] Compiling Data.Text.Prettyprint.Doc.Render.Tutorials.TreeRenderingTutorial
prettyprinter          > [19 of 28] Compiling Prettyprinter.Render.Tutorials.StackMachineTutorial
hspec-expectations     > [4 of 4] Compiling Test.Hspec.Expectations
hspec-expectations     > copy/register
hspec-expectations     > Installing library in C:\Users\schwe\AppData\Roaming\stack\snapshots\0bf3cf80\lib\x86_64-windows-ghc-9.4.8\hspec-expectations-0.8.2-9IwwP0TQmbx3mq97hV30im
hspec-expectations     > Registering library for hspec-expectations-0.8.2..
prettyprinter          > [20 of 28] Compiling Data.Text.Prettyprint.Doc.Render.Tutorials.StackMachineTutorial
prettyprinter          >
prettyprinter          > src\Data\Text\Prettyprint\Doc\Render\Tutorials\StackMachineTutorial.hs:7:1: warning: [-Wdeprecations]
prettyprinter          >     Module `Prettyprinter.Render.Tutorials.StackMachineTutorial' is deprecated:
prettyprinter          >       "Writing your own stack machine is probably more efficient and customizable; also consider using »renderSimplyDecorated(A)« instead"
prettyprinter          >   |
prettyprinter          > 7 | import Prettyprinter.Render.Tutorials.StackMachineTutorial
prettyprinter          >   | ^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^
prettyprinter          > [21 of 28] Compiling Prettyprinter.Render.Text
ansi-terminal-types    > copy/register
ansi-terminal-types    > Installing library in C:\Users\schwe\AppData\Roaming\stack\snapshots\0bf3cf80\lib\x86_64-windows-ghc-9.4.8\ansi-terminal-types-0.11.5-94tBGyEfSQUHisdUb8P9op
prettyprinter          > [22 of 28] Compiling Data.Text.Prettyprint.Doc.Render.Text
prettyprinter          > [23 of 28] Compiling Data.Text.Prettyprint.Doc
prettyprinter          > [24 of 28] Compiling Data.Text.Prettyprint.Doc.Symbols.Ascii
ansi-terminal-types    > Registering library for ansi-terminal-types-0.11.5..
prettyprinter          > [25 of 28] Compiling Prettyprinter.Symbols.Unicode
prettyprinter          > [26 of 28] Compiling Data.Text.Prettyprint.Doc.Symbols.Unicode
primitive              > [11 of 13] Compiling Data.Primitive.Ptr
prettyprinter          > [27 of 28] Compiling Prettyprinter.Util
primitive              > [12 of 13] Compiling Data.Primitive
prettyprinter          > [28 of 28] Compiling Data.Text.Prettyprint.Doc.Util
primitive              > [13 of 13] Compiling Data.Primitive.PrimVar
prettyprinter          > copy/register
prettyprinter          > Installing library in C:\Users\schwe\AppData\Roaming\stack\snapshots\0bf3cf80\lib\x86_64-windows-ghc-9.4.8\prettyprinter-1.7.1-E5xxqYOhcEq3UXGzGcv60h
ansi-terminal          > configure
prettyprinter          > Registering library for prettyprinter-1.7.1..
primitive              > copy/register
primitive              > Installing library in C:\Users\schwe\AppData\Roaming\stack\snapshots\0bf3cf80\lib\x86_64-windows-ghc-9.4.8\primitive-0.8.0.0-DOq08qccxJQDiY3mViZQFk
ansi-terminal          > Configuring ansi-terminal-0.11.5...
primitive              > Registering library for primitive-0.8.0.0..
ansi-terminal          > build with ghc-9.4.8
ansi-terminal          > Preprocessing library for ansi-terminal-0.11.5..
ansi-terminal          > Building library for ansi-terminal-0.11.5..
random                 > [3 of 4] Compiling System.Random
ansi-terminal          > [1 of 9] Compiling System.Console.ANSI.Codes
ansi-terminal          > [2 of 9] Compiling System.Console.ANSI.Unix
ansi-terminal          > [3 of 9] Compiling System.Console.ANSI.Windows.Emulator.Codes
ansi-terminal          > [4 of 9] Compiling System.Win32.Compat
haskell-lexer          > [6 of 6] Compiling Language.Haskell.Lexer
ansi-terminal          > [5 of 9] Compiling System.Console.ANSI.Windows.Foreign
haskell-lexer          > copy/register
haskell-lexer          > Installing library in C:\Users\schwe\AppData\Roaming\stack\snapshots\0bf3cf80\lib\x86_64-windows-ghc-9.4.8\haskell-lexer-1.1.1-Hpax8yMtr9r8FzGXIYjm8Z
haskell-lexer          > Registering library for haskell-lexer-1.1.1..
ansi-terminal          > [6 of 9] Compiling System.Console.ANSI.Windows.Detect
ansi-terminal          > [7 of 9] Compiling System.Console.ANSI.Windows.Emulator
random                 > [4 of 4] Compiling System.Random.Stateful
ansi-terminal          > [8 of 9] Compiling System.Console.ANSI.Windows
random                 > copy/register
random                 > Installing library in C:\Users\schwe\AppData\Roaming\stack\snapshots\0bf3cf80\lib\x86_64-windows-ghc-9.4.8\random-1.2.1.1-5A411GLqDA5B9LXzgGkW7g
ansi-terminal          > [9 of 9] Compiling System.Console.ANSI
random                 > Registering library for random-1.2.1.1..
ansi-terminal          > copy/register
ansi-terminal          > Installing library in C:\Users\schwe\AppData\Roaming\stack\snapshots\0bf3cf80\lib\x86_64-windows-ghc-9.4.8\ansi-terminal-0.11.5-KZbMdU6wjyG8VroO9pQPB9
ansi-terminal          > Registering library for ansi-terminal-0.11.5..
QuickCheck             > configure
tf-random              > configure
QuickCheck             > Configuring QuickCheck-2.14.3...
tf-random              > Configuring tf-random-0.5...
tf-random              > build with ghc-9.4.8
QuickCheck             > build with ghc-9.4.8
tf-random              > Preprocessing library for tf-random-0.5..
tf-random              > Building library for tf-random-0.5..
QuickCheck             > Preprocessing library for QuickCheck-2.14.3..
QuickCheck             > Building library for QuickCheck-2.14.3..
tf-random              > [1 of 4] Compiling System.Random.TF.Gen
tf-random              > [2 of 4] Compiling System.Random.TF.Init
QuickCheck             > [ 1 of 16] Compiling Test.QuickCheck.Exception
tf-random              >
tf-random              > src\System\Random\TF\Init.hs:94:5: warning: [-Wdeprecations]
tf-random              >     In the use of `bitSize'
tf-random              >     (imported from Data.Bits, but defined in GHC.Bits):
tf-random              >     Deprecated: "Use 'bitSizeMaybe' or 'finiteBitSize' instead"
tf-random              >    |
tf-random              > 94 |   | bitSize n > 64 = error "mkTFGen: case where size of Int > 64 not implemented"
tf-random              >    |     ^^^^^^^
tf-random              > [3 of 4] Compiling System.Random.TF
QuickCheck             > [ 2 of 16] Compiling Test.QuickCheck.Random
tf-random              > [4 of 4] Compiling System.Random.TF.Instances
QuickCheck             > [ 3 of 16] Compiling Test.QuickCheck.Gen
QuickCheck             > [ 4 of 16] Compiling Test.QuickCheck.Gen.Unsafe
tf-random              > copy/register
QuickCheck             > [ 5 of 16] Compiling Test.QuickCheck.Arbitrary
tf-random              > Installing library in C:\Users\schwe\AppData\Roaming\stack\snapshots\0bf3cf80\lib\x86_64-windows-ghc-9.4.8\tf-random-0.5-7CHpavukUwWHL3y8JTWIMF
tf-random              > Registering library for tf-random-0.5..
QuickCheck             > [ 6 of 16] Compiling Test.QuickCheck.Poly
QuickCheck             > [ 7 of 16] Compiling Test.QuickCheck.Modifiers
QuickCheck             > [ 8 of 16] Compiling Test.QuickCheck.Function
QuickCheck             > [ 9 of 16] Compiling Test.QuickCheck.Text
QuickCheck             > [10 of 16] Compiling Test.QuickCheck.State
QuickCheck             > [11 of 16] Compiling Test.QuickCheck.Property
QuickCheck             > [12 of 16] Compiling Test.QuickCheck.Test
QuickCheck             > [13 of 16] Compiling Test.QuickCheck.Monadic
QuickCheck             > [14 of 16] Compiling Test.QuickCheck.All
QuickCheck             > [15 of 16] Compiling Test.QuickCheck.Features
QuickCheck             > [16 of 16] Compiling Test.QuickCheck
QuickCheck             > copy/register
QuickCheck             > Installing library in C:\Users\schwe\AppData\Roaming\stack\snapshots\0bf3cf80\lib\x86_64-windows-ghc-9.4.8\QuickCheck-2.14.3-43t8QKFUdu3KdBTU6hmsrT
QuickCheck             > Registering library for QuickCheck-2.14.3..
quickcheck-io          > configure
quickcheck-io          > Configuring quickcheck-io-0.2.0...
quickcheck-io          > build with ghc-9.4.8
quickcheck-io          > Preprocessing library for quickcheck-io-0.2.0..
quickcheck-io          > Building library for quickcheck-io-0.2.0..
quickcheck-io          > [1 of 2] Compiling Paths_quickcheck_io
quickcheck-io          > [2 of 2] Compiling Test.QuickCheck.IO
quickcheck-io          > copy/register
quickcheck-io          > Installing library in C:\Users\schwe\AppData\Roaming\stack\snapshots\0bf3cf80\lib\x86_64-windows-ghc-9.4.8\quickcheck-io-0.2.0-AxhEGceFknIKJaq1Fa2Gjy
quickcheck-io          > Registering library for quickcheck-io-0.2.0..
hspec-core             > configure
hspec-core             > Configuring hspec-core-2.10.10...
hspec-core             > build with ghc-9.4.8
hspec-core             > Preprocessing library for hspec-core-2.10.10..
hspec-core             > Building library for hspec-core-2.10.10..
hspec-core             > [ 1 of 43] Compiling Control.Concurrent.Async
hspec-core             > [ 2 of 43] Compiling Data.Algorithm.Diff
hspec-core             > [ 3 of 43] Compiling Paths_hspec_core
hspec-core             > [ 4 of 43] Compiling Test.Hspec.Core.Compat
hspec-core             > [ 5 of 43] Compiling Test.Hspec.Core.Clock
hspec-core             > [ 6 of 43] Compiling NonEmpty
hspec-core             > [ 7 of 43] Compiling GetOpt.Declarative.Types
hspec-core             > [ 8 of 43] Compiling GetOpt.Declarative.Environment
hspec-core             > [ 9 of 43] Compiling Test.Hspec.Core.Example.Location
hspec-core             > [10 of 43] Compiling Test.Hspec.Core.Formatters.Diff
hspec-core             > [11 of 43] Compiling Test.Hspec.Core.Formatters.Pretty.Parser.Parser
hspec-core             > [12 of 43] Compiling Test.Hspec.Core.Formatters.Pretty.Parser
hspec-core             > [13 of 43] Compiling Test.Hspec.Core.Formatters.Pretty.Unicode
hspec-core             > [14 of 43] Compiling Test.Hspec.Core.Formatters.Pretty
hspec-core             > [15 of 43] Compiling Test.Hspec.Core.Formatters.V1.Free
hspec-core             > [16 of 43] Compiling Test.Hspec.Core.Runner.JobQueue
hspec-core             > [17 of 43] Compiling Test.Hspec.Core.Timer
hspec-core             > [18 of 43] Compiling Test.Hspec.Core.Util
hspec-core             > [19 of 43] Compiling Test.Hspec.Core.QuickCheckUtil
hspec-core             > [20 of 43] Compiling Test.Hspec.Core.Example
hspec-core             > [21 of 43] Compiling Test.Hspec.Core.Tree
hspec-core             > [22 of 43] Compiling Test.Hspec.Core.Shuffle
hspec-core             > [23 of 43] Compiling Test.Hspec.Core.Format
hspec-core             > [24 of 43] Compiling Test.Hspec.Core.Runner.Result
hspec-core             > [25 of 43] Compiling Test.Hspec.Core.Formatters.V1.Monad
hspec-core             > [26 of 43] Compiling Test.Hspec.Core.Formatters.Internal
hspec-core             > [27 of 43] Compiling Test.Hspec.Core.Formatters.V2
hspec-core             > [28 of 43] Compiling Test.Hspec.Core.Runner.PrintSlowSpecItems
hspec-core             > [29 of 43] Compiling Test.Hspec.Core.Formatters.V1
hspec-core             > [30 of 43] Compiling Test.Hspec.Core.Formatters
hspec-core             > [31 of 43] Compiling GetOpt.Declarative.Util
hspec-core             > [32 of 43] Compiling GetOpt.Declarative.Interpret
hspec-core             > [33 of 43] Compiling GetOpt.Declarative
hspec-core             > [34 of 43] Compiling Test.Hspec.Core.Config.Definition
hspec-core             > [35 of 43] Compiling Test.Hspec.Core.Spec.Monad
hspec-core             > [36 of 43] Compiling Test.Hspec.Core.Hooks
hspec-core             > [37 of 43] Compiling Test.Hspec.Core.Spec
hspec-core             > [38 of 43] Compiling Test.Hspec.Core.Runner.Eval
hspec-core             > [39 of 43] Compiling Test.Hspec.Core.QuickCheck
hspec-core             > [40 of 43] Compiling Test.Hspec.Core.FailureReport
hspec-core             > [41 of 43] Compiling Test.Hspec.Core.Config.Options
hspec-core             > [42 of 43] Compiling Test.Hspec.Core.Config
hspec-core             > [43 of 43] Compiling Test.Hspec.Core.Runner
hspec-core             > copy/register
hspec-core             > Installing library in C:\Users\schwe\AppData\Roaming\stack\snapshots\0bf3cf80\lib\x86_64-windows-ghc-9.4.8\hspec-core-2.10.10-Cedpp7G8SJZ7vC58v1MSaK
hspec-core             > Registering library for hspec-core-2.10.10..
hspec                  > configure
hspec                  > Configuring hspec-2.10.10...
hspec                  > build with ghc-9.4.8
hspec                  > Preprocessing library for hspec-2.10.10..
hspec                  > Building library for hspec-2.10.10..
hspec                  > [1 of 6] Compiling Paths_hspec
hspec                  > [2 of 6] Compiling Test.Hspec.Discover
hspec                  > [3 of 6] Compiling Test.Hspec.Formatters
hspec                  > [4 of 6] Compiling Test.Hspec.Runner
hspec                  > [5 of 6] Compiling Test.Hspec
hspec                  > [6 of 6] Compiling Test.Hspec.QuickCheck
hspec                  > copy/register
hspec                  > Installing library in C:\Users\schwe\AppData\Roaming\stack\snapshots\0bf3cf80\lib\x86_64-windows-ghc-9.4.8\hspec-2.10.10-JVI3EpKsaPeoO9fHVztrT
hspec                  > Registering library for hspec-2.10.10..
haskell-exercises-shmup> configure (lib + exe)
Configuring haskell-exercises-shmup-0.1.0.0...
haskell-exercises-shmup> build (lib + exe) with ghc-9.4.8
Preprocessing library for haskell-exercises-shmup-0.1.0.0..
Building library for haskell-exercises-shmup-0.1.0.0..
[ 1 of 21] Compiling BinarySearchTree.Internal
[ 2 of 21] Compiling BinarySearchTree
[ 3 of 21] Compiling BinarySearchTree.ExampleUsage
[ 4 of 21] Compiling Chapter_01
[ 5 of 21] Compiling Chapter_02
[ 6 of 21] Compiling Chapter_03
[ 7 of 21] Compiling Chapter_04
[ 8 of 21] Compiling Chapter_05
[ 9 of 21] Compiling Chapter_06
[10 of 21] Compiling Chapter_07
[11 of 21] Compiling Chapter_08
[12 of 21] Compiling Chapter_10
[13 of 21] Compiling Chapter_12
[14 of 21] Compiling Chapter_16
[15 of 21] Compiling LambdaCalculus.UnorderedSet
[16 of 21] Compiling LambdaCalculus.Set
[17 of 21] Compiling LambdaCalculus.Internal
[18 of 21] Compiling LambdaCalculus.Parsing
[19 of 21] Compiling LambdaCalculus
[20 of 21] Compiling LambdaCalculus.ExampleUsage
[21 of 21] Compiling Paths_haskell_exercises_shmup
Preprocessing executable 'haskell-exercises-shmup-exe' for haskell-exercises-shmup-0.1.0.0..
Building executable 'haskell-exercises-shmup-exe' for haskell-exercises-shmup-0.1.0.0..
[1 of 2] Compiling Main
[2 of 2] Compiling Paths_haskell_exercises_shmup
[3 of 3] Linking .stack-work\dist\f1a1ac53\build\haskell-exercises-shmup-exe\haskell-exercises-shmup-exe.exe
haskell-exercises-shmup> copy/register
Installing library in C:\Users\schwe\Documents\GitHub\Studium\FP\Haskell-Exercises-shmup-2025-FS-main\.stack-work\install\73b5788e\lib\x86_64-windows-ghc-9.4.8\haskell-exercises-shmup-0.1.0.0-DdNTMO5rK3w5478UCnmcjD
Installing executable haskell-exercises-shmup-exe in C:\Users\schwe\Documents\GitHub\Studium\FP\Haskell-Exercises-shmup-2025-FS-main\.stack-work\install\73b5788e\bin
Registering library for haskell-exercises-shmup-0.1.0.0..
Completed 20 action(s).

PS C:\Users\schwe\Documents\GitHub\Studium\FP\Haskell-Exercises-shmup-2025-FS-main> stack run
All good!

-}

-- Exercise 1.1 (*)
-- Look at the two ways to calculate `double (double 2)` on pages 3 and 4 in the book Programming in Haskell.
-- Give another possible way to calculate the result of `double (double 2)`. Use the same definition `double x = x + x` as in the book.

-- Complete the following block comment.
-- Note: Unlike the textbook, we will use the ‘==’ symbol instead of ‘=’ for equational reasoning to be consistent with the notation of equality used in Haskell, since ‘=’ in Haskell is used for definitions.

{-
double (double 2)
== {applying double to 2}
double (2+2)
== {applying double to 2+2}
(2+2) + (2+2)
== applying{arithmetic}
4 + 4
== applying{arithmetic}
8
-}

-- Exercise 1.2 (*)
-- Show that `sum [x] = x` for any number `x`. Use the following definition of `sum` stated on page 9 in the book "Programming in Haskell".

-- sum [] = 0
-- sum (n:ns) = n + sum ns

-- Note: In case you are wondering, the syntax `[x]` for a singleton list containing the element `x` is just syntactic sugar for, and equivalent to the list `(x:[])`. The list `[x,y,z]` is syntactic sugar for `(x:(y:(z:[])))`. This syntactic sugar is referred to as "list patterns", and will be explained in more detail in chapter 4.

-- Complete the following block comment.
{-
sum [x]
== {applying sum}
sum(x:xs)
== {applying sum to (x:xs), xs = []}
x + sum([])
== applying{sum([])}
x + 0
=={arithmetic}
x

-}

-- Exercise 1.3 (*)
-- Define a function myProduct that produces the product of a list of numbers, and show using your definition that myProduct [2,3,4] == 24.
-- Note: We use the name "myProduct" since the name product is already defined in the ghc Prelude.

myProduct :: (Num p) => [p] -> p
myProduct [] = 1
myProduct (n : ns) = n * myProduct ns
