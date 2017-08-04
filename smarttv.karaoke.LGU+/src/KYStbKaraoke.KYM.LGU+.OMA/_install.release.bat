cd /d "%~dp0"
set DEVICE_OPTION=
if not [%1]==[] set DEVICE_OPTION=-s %1
REM echo %DEVICE_OPTION%
REM pause
REM cmd /c _adb.disconnect.bat
REM adb connect %1
adb devices
REM cmd /c ant uninstall
REM cmd /c ant installr
cmd /c _install.uninstall.bat %1
for /r %%i in (*-release.apk) do (set filename=%%~nxi)
adb %DEVICE_OPTION% install -r ./%filename%
cmd /c _install.start.bat %1
REM pause
