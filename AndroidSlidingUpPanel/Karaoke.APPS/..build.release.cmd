@echo OFF
cd /d "%~dp0"
cmd /c color 1A
REM start /d "..\Karaoke.APPS.KAR\" .build.release.bat
cmd /c "..\Karaoke.APPS.KAR\.build.release.bat"
cmd /c color 1B
REM start /d "..\Karaoke.APPS.S5\" .build.release.bat
cmd /c "..\Karaoke.APPS.S5\.build.release.bat"
REM cmd /c color 1C
REM start /d "..\Karaoke.APPS.JPOP\" .build.release.bat
REM cmd /c color 1D
REM start /d "..\Karaoke.APPS.KPOP\" .build.release.bat
REM cmd /c color 1E
REM start /d "..\Karaoke.APPS.ONSPOT\" .build.release.bat
REM cmd /c color 1F
REM start /d "..\Karaoke.APPS.NAVER\" .build.release.bat
REM cmd /c color 17
REM start /d "..\Karaoke.APPS.INDIA\" .build.release.bat
REM cmd /c color 1A
REM pause
