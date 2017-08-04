cd /d "%~dp0"
adb disconnect
echo SKB.BTV.BHX-UH200
call ..\Karaoke.TV\.adb.connect.SKB.UHD.BHX-UH200.bat
echo SKB.BTV.BHX-S300
call ..\Karaoke.TV\.adb.connect.SKB.STB.BHX-S300.bat
echo SKB.BTV.BKO-S300
call ..\Karaoke.TV\.adb.connect.SKB.STB.BKO-S300.bat
echo SKT.BOX.BDS-S100
call ..\Karaoke.TV\.adb.connect.SKB.STB.BKO-S200.bat
adb devices
pause
