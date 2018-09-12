@echo OFF
cd /d "%~dp0"
cmd /c color 0A
REM start /d "..\Karaoke.TV.BTV\" .build.clean.bat
cmd /c "..\Karaoke.TV.BTV\.build.clean.bat"
cmd /c color 0B
REM start /d "..\Karaoke.TV.GTV\" .build.clean.bat
cmd /c "..\Karaoke.TV.GTV\.build.clean.bat"
cmd /c color 0C
REM start /d "..\Karaoke.TV.ATV\" .build.clean.bat
cmd /c "..\Karaoke.TV.ATV\.build.clean.bat"
cmd /c color 0D
REM start /d "..\Karaoke.TV.ATV.ST.COM\" .build.clean.bat
REM cmd /c "..\Karaoke.TV.ATV.ST.COM\.build.clean.bat"
cmd /c color 0E
cmd /c color 0F
cmd /c color 07
cmd /c color 0A
REM pause
