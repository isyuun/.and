@echo OFF
cd /d "%~dp0"
cmd /c color 8A
REM start /d "..\Karaoke.APPS.KAR\" .build.debug.bat
cmd /c "..\Karaoke.APPS.KAR\.build.debug.bat"
cmd /c color 8B
REM start /d "..\Karaoke.APPS.S5\" .build.debug.bat
cmd /c  "..\Karaoke.APPS.S5\.build.debug.bat"
REM cmd /c color 8C
REM start /d "..\Karaoke.APPS.JPOP\" .build.debug.bat
REM cmd /c color 8D
REM start /d "..\Karaoke.APPS.KPOP\" .build.debug.bat
REM cmd /c color 8E
REM start /d "..\Karaoke.APPS.ONSPOT\" .build.debug.bat
REM cmd /c color 8F
REM start /d "..\Karaoke.APPS.NAVER\" .build.debug.bat
REM cmd /c color 87
REM start /d "..\Karaoke.APPS.INDIA\" .build.debug.bat
REM cmd /c color 8A
REM pause
