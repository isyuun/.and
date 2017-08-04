cd /d "%~dp0"
jad -o -r -sjava -d%1.src %1/**/*.class
pause