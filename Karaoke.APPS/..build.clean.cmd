@echo OFF
cd /d "%~dp0"
cmd /c color 0A
REM start /d "..\Karaoke.APPS.KAR\" .build.clean.bat
cmd /c "..\Karaoke.APPS.KAR\.build.clean.bat"
cmd /c color 0B
REM start /d "..\Karaoke.APPS.S5\" .build.clean.bat
cmd /c "..\Karaoke.APPS.S5\.build.clean.bat"
REM cmd /c color 0C
REM start /d "..\Karaoke.APPS.JPOP\" .build.clean.bat
REM cmd /c color 0D
REM start /d "..\Karaoke.APPS.KPOP\" .build.clean.bat
REM cmd /c color 0E
REM start /d "..\Karaoke.APPS.ONSPOT\" .build.clean.bat
REM cmd /c color 0F
REM start /d "..\Karaoke.APPS.NAVER\" .build.clean.bat
REM cmd /c color 07
REM start /d "..\Karaoke.APPS.INDIA\" .build.clean.bat
REM cmd /c color 0A
REM pause
