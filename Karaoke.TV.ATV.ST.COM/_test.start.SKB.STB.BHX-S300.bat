@echo off
cd /d "%~dp0"
REM adb disconnect
call ..\Karaoke.TV\.adb.connect.SKB.STB.BHX-S300.bat
cmd /c _test.start.bat %DEVICE_IP%:5555
