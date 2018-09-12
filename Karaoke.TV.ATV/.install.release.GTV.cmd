cd /d "%~dp0"
adb disconnect
cmd /c .install.connect.GTV.bat
cmd /c color 1A
cmd /c ".\_install.release.LGU+.TVG2.bat"
cmd /c .beep.bat
cmd /c color 1B
cmd /c ".\_install.release.LGU+.TVG1.bat"
cmd /c .beep.bat
cmd /c color 1C
cmd /c color 1D
cmd /c color 1E
cmd /c color 1F
cmd /c color 17
cmd /c color 1A
adb devices
pause
