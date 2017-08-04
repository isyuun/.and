cd /d "%~dp0"
cmd /c color 0A
cmd /c "..\Karaoke.APPS.KAR\.aapt.bat ..\Karaoke.APPS.KAR\Karaoke.APPS.KAR-release.apk"
cmd /c color 0B
cmd /c "..\Karaoke.APPS.S5\.aapt.bat ..\Karaoke.APPS.S5\Karaoke.APPS.S5-release.apk"
cmd /c color 0C
cmd /c "..\Karaoke.APPS.JPOP\.aapt.bat ..\Karaoke.APPS.JPOP\Karaoke.APPS.JPOP-release.apk"
cmd /c color 0D
cmd /c "..\Karaoke.APPS.KPOP\.aapt.bat ..\Karaoke.APPS.KPOP\Karaoke.KPOP-release.apk"
REM cmd /c color 0E
REM cmd /c "..\Karaoke.APPS.ONSPOT\.aapt.bat ..\Karaoke.APPS.ONSPOT\Karaoke.APPS.ONSPOT-release.apk"
REM cmd /c color 0F
REM cmd /c "..\Karaoke.APPS.NAVER\.aapt.bat ..\Karaoke.APPS.NAVER\Karaoke.APPS.NAVER-release.apk"
REM cmd /c color 07
REM cmd /c "..\Karaoke.APPS.INDIA\.aapt.bat ..\Karaoke.APPS.INDIA\Karaoke.APPS.INDIA-release.apk"
cmd /c color 0A
pause
