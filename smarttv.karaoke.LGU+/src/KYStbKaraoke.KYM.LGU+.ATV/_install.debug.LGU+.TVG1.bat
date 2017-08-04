@echo off
cd /d "%~dp0"
REM adb disconnect
call ..\Karaoke.TV\.adb.connect.LGU+.TVG1.bat
cmd /c _install.debug.bat %DEVICE_IP%
REM pause
