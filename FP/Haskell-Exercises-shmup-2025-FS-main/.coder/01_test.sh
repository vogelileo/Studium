# Exit script on failure
set -e

# Source the GHCup env to update PATH
. $HOME/.ghcup/env

# Show Version
echo "*** Show Installed Versions ***"
ghcup list -c installed 
ghci --version

# for FP FS-2024 should be:
# GHC == 9.4.8
# Cabal == 3.10.2.1
# HLS == 2.5.0.0
# Stack == 2.13.1
# GHCup == 0.1.20.0

echo "*** Successful Setup - All good ***"!
echo "Try execute 'stack build && stack run' now"
