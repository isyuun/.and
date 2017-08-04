cd /d "%~dp0"
set DEVICE_OPTION=
if not [%1]==[] set DEVICE_OPTION=-s %1
REM echo %DEVICE_OPTION%
REM pause
REM cmd /c _adb.connect.bat
adb %DEVICE_OPTION% shell am start -a android.intent.action.MAIN -c android.intent.category.LAUNCHER -n kr.kymedia.karaoke.kpop.kar/kr.kymedia.karaoke.kpop.kar.home
REM pause
