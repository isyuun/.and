cd /d "%~dp0"
set DEVICE_OPTION=
if not [%1]==[] set DEVICE_OPTION=-s %1
REM echo %DEVICE_OPTION%
REM pause
REM REM cmd /c _adb.connect.bat
REM cmd /c ant uninstall
adb %DEVICE_OPTION% uninstall kr.kymedia.karaoke.kpop.kar
REM pause
