REM cmd /c _adb.connect.bat
@echo off
:START
REM adb shell com.google.android.youtube
adb shell dumpsys meminfo com.kumyoung.gtvkaraoke
timeout /T 1
goto :START
REM pause
