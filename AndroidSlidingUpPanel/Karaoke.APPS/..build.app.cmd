@echo OFF
cd /d "%~dp0"
cmd /c color 1A
REM start /d "..\Karaoke.APPS.KAR\" .build.bat
cmd /c "..\Karaoke.APPS.KAR\.build.bat"
cmd /c color 1B
REM start /d "..\Karaoke.APPS.S5\" .build.bat
cmd /c "..\Karaoke.APPS.S5\.build.bat"
REM cmd /c color 1C
REM start /d "..\Karaoke.APPS.JPOP\" .build.bat
REM cmd /c color 1D
REM start /d "..\Karaoke.APPS.KPOP\" .build.bat
REM cmd /c color 1E
REM start /d "..\Karaoke.APPS.ONSPOT\" .build.bat
REM cmd /c color 1F
REM start /d "..\Karaoke.APPS.NAVER\" .build.bat
REM cmd /c color 17
REM start /d "..\Karaoke.APPS.INDIA\" .build.bat
REM cmd /c color 1A
REM pause
