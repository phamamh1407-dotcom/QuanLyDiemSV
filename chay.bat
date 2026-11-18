@echo off
chcp 65001 >nul
cd /d "%~dp0"
if not exist db.properties (
    copy db.properties.example db.properties >nul
    echo Da tao file db.properties. Hay mo file nay, sua password, roi chay lai chay.bat
    notepad db.properties
    pause
    exit /b
)
dir /b lib\*.jar >nul 2>nul
if errorlevel 1 (
    echo THIEU JDBC DRIVER: hay chep file mssql-jdbc-xxx.jar vao thu muc lib roi chay lai.
    pause
    exit /b
)
if exist out rmdir /s /q out
mkdir out
dir /s /b src\*.java > sources.txt
javac -encoding UTF-8 -cp "lib\*" -d out @sources.txt
if errorlevel 1 (
    echo BIEN DICH LOI! Xem thong bao phia tren.
    del sources.txt
    pause
    exit /b
)
del sources.txt
java -cp "out;lib\*" Main
pause
