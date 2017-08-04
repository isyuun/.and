cd /d "%~dp0"
start tortoiseproc /blockpathadjustments /path:./soundtouch /command:checkout /url:http://svn.code.sf.net/p/soundtouch/code/trunk
start tortoiseproc /blockpathadjustments /path:%~dp0 /command:checkout /url:svn://svn.mobileon.co.kr/android/trunk/src/android-async-http*svn://svn.mobileon.co.kr/android/trunk/src/ffmpeg*svn://svn.mobileon.co.kr/android/trunk/src/ffmpeg-tutorial*svn://svn.mobileon.co.kr/android/trunk/src/.svn.dev.cmd*
REM PAUSE