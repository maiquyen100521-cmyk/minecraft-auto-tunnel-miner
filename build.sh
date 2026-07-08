#!/bin/bash
cd "$(dirname "$0")"
if [ ! -f gradle/wrapper/gradle-wrapper.jar ]; then
    echo "Downloading gradle-wrapper.jar..."
    wget https://services.gradle.org/distributions/gradle-8.6-bin.zip -O gradle-8.6-bin.zip
    unzip -q gradle-8.6-bin.zip
    mkdir -p gradle/wrapper
    cp gradle-8.6/lib/gradle-8.6/lib/gradle-wrapper.jar gradle/wrapper/gradle-wrapper.jar
    rm -rf gradle-8.6 gradle-8.6-bin.zip
fi
./gradlew "$@"
