@echo off
adb tcpip 5555
call ..\Karaoke.TV\.adb.connect._IP.bat "%~n0"
echo %DEVICE_IP%
adb connect %DEVICE_IP%
adb devices
