@echo off
cd /d "%~dp0"
echo SKB.BTV.BHX-UH200
adb disconnect
call ..\Karaoke.TV\.adb.connect.SKB.UHD.BHX-UH200.bat
cmd /c _install.uninstall.bat %DEVICE_IP%:5555
echo SKB.BTV.BHX-S300
adb disconnect
call ..\Karaoke.TV\.adb.connect.SKB.STB.BHX-S300.bat
cmd /c _install.uninstall.bat %DEVICE_IP%:5555
echo SKB.BTV.BKO-S300
adb disconnect
call ..\Karaoke.TV\.adb.connect.SKB.STB.BKO-S300.bat
cmd /c _install.uninstall.bat %DEVICE_IP%:5555
echo SKT.BOX.BDS-S100
adb disconnect
call ..\Karaoke.TV\.adb.connect.SKB.STB.BKO-S200.bat
cmd /c _install.uninstall.bat %DEVICE_IP%:5555
adb devices
pause
