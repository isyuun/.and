@echo off
cd /d "%~dp0"
REM adb disconnect
call ..\Karaoke.TV\.adb.connect.NEXUS.PLAYER.bat
cmd /c _install.release.bat %DEVICE_IP%:5555
REM pause
