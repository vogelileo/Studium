@echo off
REM --- Initialize Conda ---
call "%USERPROFILE%\miniconda3\condabin\conda.bat"

REM --- Activate local environment ---
call conda activate "%~dp0env"

REM --- Start Jupyter Notebook ---
jupyter notebook
