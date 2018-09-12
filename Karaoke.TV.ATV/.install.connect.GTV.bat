cd /d "%~dp0"
adb disconnect
echo LGU+.GTV.TVG2.UHD.ST940I-UP
call ..\Karaoke.TV\.adb.connect.LGU+.TVG2.bat
echo LGU+.GTV.TVG1.TI320-DU
call ..\Karaoke.TV\.adb.connect.LGU+.TVG1.bat
adb devices
pause
