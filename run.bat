@echo off

REM Racine du projet
cd /d "%~dp0"

REM Lancer le java
java -Dfile.encoding=UTF-8 -cp bin;lib/* polytext.App