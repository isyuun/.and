@echo OFF
cd /d "%~dp0"
cmd /c color 1A
REM start /d "..\Karaoke.TV.BTV\" .build.bat
cmd /c "..\Karaoke.TV.BTV\.build.bat"
cmd /c color 1B
REM start /d "..\Karaoke.TV.GTV\" .build.bat
cmd /c "..\Karaoke.TV.GTV\.build.bat"
cmd /c color 1C
REM start /d "..\Karaoke.TV.ATV\" .build.bat
cmd /c "..\Karaoke.TV.ATV\.build.bat"
cmd /c color 1D
REM start /d "..\Karaoke.TV.ATV.ST.COM\" .build.bat
REM cmd /c "..\Karaoke.TV.ATV.ST.COM\.build.bat"
cmd /c color 1E
cmd /c color 1F
cmd /c color 17
cmd /c color 1A
pause
