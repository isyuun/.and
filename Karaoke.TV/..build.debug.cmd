@echo OFF
cd /d "%~dp0"
cmd /c color 8A
REM start /d "..\Karaoke.TV.BTV\" .build.debug.bat
cmd /c "..\Karaoke.TV.BTV\.build.debug.bat"
cmd /c color 8B
REM start /d "..\Karaoke.TV.GTV\" .build.debug.bat
cmd /c "..\Karaoke.TV.GTV\.build.debug.bat"
cmd /c color 8C
REM start /d "..\Karaoke.TV.ATV\" .build.debug.bat
cmd /c "..\Karaoke.TV.ATV\.build.debug.bat"
cmd /c color 8D
REM start /d "..\Karaoke.TV.ATV.ST.COM\" .build.debug.bat
REM cmd /c "..\Karaoke.TV.ATV.ST.COM\.build.debug.bat"
cmd /c color 8E
cmd /c color 8F
cmd /c color 87
cmd /c color 8A
REM pause
