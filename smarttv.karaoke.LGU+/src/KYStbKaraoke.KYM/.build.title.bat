cd /d "%~dp0"
@echo OFF
REM 폴더명가져오리
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
@echo [프로젝트명확인]
@echo %LAST%
@echo ON
REM pause
