# OpenWallet aOS App
The aOS application of the OpenWallet. Supports Android 10 or later.

## Background
OpenWallet is a mobile application that serves as an open platform for digital assets.

## Getting Started
### Summary
This project uses gradle as the construction tool, uses kotlin as the main development language, 
and is based on MVVM mode, a single Activity architecture application.

### Configure the compilation environment
1. java8 or above
2. gradle7.x version
3. Android Studio Dolphin adb above
4. At least Android API level 32 needs to be installed

<br/>

## Project Structure

    .
    ├── LICENSE
    ├── Readme.md
    ├── app
    │   ├── build.gradle
    │   ├── proguard-rules.pro                              # obfuscation configs
    │   └── src
    │       ├── main                                        # app source files
    │       └── test
    ├── build.gradle
    ├── gradle
    │   └── wrapper
    │       ├── gradle-wrapper.jar
    │       └── gradle-wrapper.properties
    ├── gradle.properties
    ├── gradlew
    ├── gradlew.bat
    ├── jacoco-new.gradle
    ├── jacoco-report.gradle                                # jacoco testing report
    ├── jacoco.gradle
    ├── mobile-ci.json
    └── settings.gradle

## Packaging
### Build Flavors
BuildFlavor include uat and product, buildType include debug and release type.

The debug build will get the debug signature.
Uat release will get the uat signature, product release will not get signature.
