@echo OFF
cd /d "%~dp0"
@echo [���۽ð�:%DATE% %TIME%]
cmd /c color 0A
cmd /c ..build.clean.cmd
cmd /c ..build.release.cmd
REM cmd /c ..build.clean.cmd
cmd /c ..build.debug.cmd
cmd /c color 0A
@echo [����ð�:%DATE% %TIME%]
pause
