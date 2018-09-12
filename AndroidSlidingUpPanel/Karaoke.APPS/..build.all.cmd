@echo OFF
cd /d "%~dp0"
@echo [시작시간:%DATE% %TIME%]
cmd /c color 0A
cmd /c ..build.clean.cmd
cmd /c ..build.release.cmd
REM cmd /c ..build.clean.cmd
cmd /c ..build.debug.cmd
cmd /c color 0A
@echo [종료시간:%DATE% %TIME%]
pause
