# Exit script on failure
set -e


export BOOTSTRAP_HASKELL_NONINTERACTIVE=1
export BOOTSTRAP_HASKELL_ADJUST_BASHRC=1
export BOOTSTRAP_HASKELL_INSTALL_HLS=1
export BOOTSTRAP_HASKELL_GHC_VERSION="9.4.8"


# System requirements (according question in the setup)
sudo apt-get update && sudo apt-get install -y --no-install-recommends ca-certificates build-essential curl libffi-dev libffi8ubuntu1 libgmp-dev libgmp10 libncurses-dev libncurses5 libtinfo5


# Setup (interactive)
echo "*** Start GHCup Setup ***"
curl --proto '=https' --tlsv1.2 -sSf https://get-ghcup.haskell.org | sh
echo "*** End GHCup Setup ***"
