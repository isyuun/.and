@echo off
cd /d "%~dp0"
REM adb disconnect
call ..\Karaoke.TV\.adb.connect.SKB.STB.BKO-S200.bat
cmd /c _install.start.bat %DEVICE_IP%
REM pause
