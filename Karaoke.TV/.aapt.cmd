cd /d "%~dp0"
cmd /c color 0A
cmd /c "..\Karaoke.TV.APP\.aapt.bat ..\Karaoke.TV.APP\Karaoke.TV.APP-release.apk"
cmd /c color 0B
cmd /c "..\Karaoke.TV.BTV\.aapt.bat ..\Karaoke.TV.BTV\Karaoke.TV.BTV-release.apk"
cmd /c color 0C
cmd /c "..\Karaoke.TV.GTV\.aapt.bat ..\Karaoke.TV.GTV\Karaoke.TV.GTV-release.apk"
cmd /c color 0D
cmd /c color 0E
cmd /c color 0F
cmd /c color 07
cmd /c color 0A
pause
