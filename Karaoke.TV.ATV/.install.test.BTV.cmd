cd /d "%~dp0"
adb disconnect
cmd /c .install.connect.BTV.bat
cmd /c color 1A
start /d ".\" _test.start.SKB.UHD.BHX-UH200.bat
cmd /c .beep.bat
cmd /c color 1B
start /d ".\" _test.start.SKB.STB.BHX-S300.bat
cmd /c .beep.bat
cmd /c color 1C
start /d ".\" _test.start.SKB.STB.BKO-S300.bat
cmd /c .beep.bat
cmd /c color 1D
start /d ".\" _test.start.SKB.STB.BKO-S200.bat
cmd /c .beep.bat
cmd /c color 1E
cmd /c color 1F
cmd /c color 17
cmd /c color 1A
adb devices
pause
