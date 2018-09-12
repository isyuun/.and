cd /d "%~dp0"
echo LGU+.GTV.TVG2.UHD.ST940I-UP
adb disconnect
call ..\Karaoke.TV\.adb.connect.LGU+.TVG2.bat
cmd /c _install.uninstall.bat %DEVICE_IP%:5555
echo LGU+.GTV.TVG1.TI320-DU
adb disconnect
call ..\Karaoke.TV\.adb.connect.LGU+.TVG1.bat
cmd /c _install.uninstall.bat %DEVICE_IP%:5555
echo DISCONNECT
adb disconnect
adb devices
pause
