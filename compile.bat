@echo off
setlocal enabledelayedexpansion

REM Racine du projet
cd /d "%~dp0"

REM Vide le fichier compile.list
type nul > compile.list

REM Parcourt tous les fichiers .java sous src et ajoute le chemin relatif
for /r src %%f in (*.java) do (
    set "full=%%f"
    set "rel=!full:*src\=src\!"
    echo !rel! >> compile.list
)

REM Compile avec java
javac -cp lib/* @option.list @compile.list