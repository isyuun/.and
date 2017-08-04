@echo off
cd /d "%~dp0"
REM adb disconnect
call ..\Karaoke.TV\.adb.connect.SKB.STB.BKO-S300.bat
cmd /c _install.start.bat %DEVICE_IP%:5555
REM pause
