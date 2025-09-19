@echo off
REM --- Initialize Conda (this loads conda commands into cmd.exe) ---
call "%USERPROFILE%\miniconda3\condabin\conda.bat"

REM --- Activate the local environment (in project folder) ---
call conda activate "%~dp0env"

REM --- Export environment to YAML file ---
conda env export > "%~dp0environment.yml"

echo.
echo [OK] Environment saved to environment.yml
pause