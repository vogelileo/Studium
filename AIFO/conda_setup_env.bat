@echo off
REM ----------------------------------------
REM Setup or open local Conda environment and start Jupyter
REM ----------------------------------------

REM Path to Conda bootstrap
set CONDA_BOOTSTRAP=%USERPROFILE%\miniconda3\condabin\conda.bat

REM Check if conda.bat exists
if not exist "%CONDA_BOOTSTRAP%" (
    echo ERROR: Could not find conda.bat at %CONDA_BOOTSTRAP%
    echo Make sure Miniconda is installed in the default location.
    pause
    exit /b 1
)

REM Initialize Conda
call "%CONDA_BOOTSTRAP%"

REM Path to local environment
set ENV_PATH=%~dp0env

REM Check if local env exists
if not exist "%ENV_PATH%" (
    echo ----------------------------------------
    echo Local environment not found, creating from environment.yml...
    echo ----------------------------------------
    conda env create --prefix "%ENV_PATH%" -f "%~dp0environment.yml"
) else (
    echo ----------------------------------------
    echo Local environment exists, skipping creation
    echo ----------------------------------------
)

REM Activate environment
call conda activate "%ENV_PATH%"

REM Start Jupyter Notebook
echo ----------------------------------------
echo Starting Jupyter Notebook...
echo ----------------------------------------
jupyter notebook
