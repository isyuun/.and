REM cmd /c _adb.connect.bat
@echo off
:START
REM adb shell com.google.android.youtube
adb shell dumpsys meminfo kr.kymedia.kykaraoke.tv.atv.st.com
timeout /T 1
goto :START
REM pause
