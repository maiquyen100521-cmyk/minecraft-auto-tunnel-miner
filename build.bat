@echo off
cd /d "%~dp0"
if not exist gradle\wrapper\gradle-wrapper.jar (
    echo Downloading gradle-wrapper.jar...
    powershell -Command "(New-Object Net.WebClient).DownloadFile('https://services.gradle.org/distributions/gradle-8.6-bin.zip', 'gradle-8.6-bin.zip')"
    powershell -Command "Expand-Archive -Path 'gradle-8.6-bin.zip' -DestinationPath '.'"
    mkdir gradle\wrapper 2>nul
    copy gradle-8.6\lib\gradle-8.6\lib\gradle-wrapper.jar gradle\wrapper\gradle-wrapper.jar
    rmdir /s /q gradle-8.6
    del gradle-8.6-bin.zip
)
gradlew.bat %*
