cd /d "%~dp0"
@echo OFF
REM ������������
set mydir="%~dp0"
SET mydir=%mydir:\=;%

for /F "tokens=* delims=;" %%i IN (%mydir%) DO call :FOLDER %%i
goto :BUILD

:FOLDER
if "%1"=="" (
    REM @echo %LAST%
    goto :EOF
)

set LAST=%1
SHIFT

goto :FOLDER

:BUILD
title %LAST%
@echo [������Ʈ��Ȯ��]
@echo %LAST%
@echo ON
REM pause
