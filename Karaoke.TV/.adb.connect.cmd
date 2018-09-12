cd /d "%~dp0"
adb disconnect
cmd /c color 1A
cmd /c ".\.adb.connect.SKB.UHD.bat"
cmd /c .beep.bat
cmd /c color 1B
cmd /c ".\.adb.connect.SKB.STB.BHX-S300.bat"
cmd /c .beep.bat
cmd /c color 1C
cmd /c ".\.adb.connect.SKB.STB.BKO-S300.bat"
cmd /c .beep.bat
cmd /c color 1D
REM cmd /c ".\.adb.connect.SKT.BOX.bat"
REM cmd /c .beep.bat
cmd /c color 1E
cmd /c ".\.adb.connect.LGU+.TVG1.bat"
cmd /c .beep.bat
cmd /c color 1F
cmd /c ".\.adb.connect.LGU+.TVG2.bat"
cmd /c .beep.bat
cmd /c color 17
cmd /c color 1A
adb devices
pause
