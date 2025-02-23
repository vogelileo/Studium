# Haskell Shoot 'em up

Welcome to the Haskell Shoot 'em up!

![Space Invaders](images/SpaceInvaders-Gameplay.gif)

Use this to quickly get used to the Haskell language and tool ecosystem.  Please read this readme until the end before starting.  

Note: This repo contains (in some cases modified) exercises from ”Programming in Haskell” by Graham Hutton <http://www.cs.nott.ac.uk/~pszgmh/pih.html>. Some questions have also been modified so that they can be solved using a computer (by, for instance resolving name clashes with the standard Haskell prelude).

# Usage (using local clones)

1. Clone this repo onto your local machine using the command `git clone <repo URL>`. You may alternatively download an archive containing the repo using the download button on the main project page on GitLab in case you have problems using Git.  
2. Use `stack build` to build the project to make sure that everything compiles before starting.  
3. All exercises can be found within the `src` directory. Start solving the "Shoot 'em up" from chapter 1 at `src/Chapter_01.hs`. Replace occurrences of `undefined` or `???` with your solutions. Make sure to use the templates and names provided. 
4. The relative level of difficulty for each exercise has been specified using stars in parentheses (&ast;) after the exercise number. The greater the number of stars, the greater the relative difficulty.
5. Use `stack test` to run the included automated tests and take pleasure from seeing them change color from red to green.  
6. Once you decide to work on the next chapter, uncomment the relevant lines in `test/Spec.hs` to enable its automated tests.  


Some helpful hints and commands:
- Build the project: `stack build`
- Run all tests: `stack test`
- Run tests in auto-refresh mode: `stack test --file-watch`
- Only run tests for a particular chapter: `stack test --test-arguments='--match "Chapter_X"'`
- A more robust alternative to uncommenting tests in order to skip them: Mark them as pending by changing the relevant `describe` or `it` to `xdescribe` or `xit` respectively. 
