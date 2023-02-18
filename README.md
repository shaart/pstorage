## Status

| Status | master | dev |
| --- | --- | --- |
| Build | [![Build Status](https://shaaart.visualstudio.com/pstorage/_apis/build/status/shaart.pstorage?branchName=master)](https://shaaart.visualstudio.com/pstorage/_build/latest?definitionId=1&branchName=master) | [![Build Status](https://shaaart.visualstudio.com/pstorage/_apis/build/status/shaart.pstorage?branchName=dev)](https://shaaart.visualstudio.com/pstorage/_build/latest?definitionId=1&branchName=dev) |
| Code quality | [![CodeFactor](https://www.codefactor.io/repository/github/shaart/pstorage/badge/master?s=01f316b05c9b3631acd487abdf9ae42c2bae56f5)](https://www.codefactor.io/repository/github/shaart/pstorage/overview/master) | [![CodeFactor](https://www.codefactor.io/repository/github/shaart/pstorage/badge/dev?s=01f316b05c9b3631acd487abdf9ae42c2bae56f5)](https://www.codefactor.io/repository/github/shaart/pstorage/overview/dev) |

## About

Offline password Storage desktop application that works from System Tray.

## Installation and run

- todo

## (For developers) How to build and run/debug locally

### Prepare env

- Install JDK 17
- Install JavaFX 17 SDK
    - Download JavaFX SDK - https://gluonhq.com/products/javafx/
    - Unpack
    - Go
      to `"Project Settings"` > `Libraries` > `"+"` > `Java` > `<path to unpacked JavaFX SDK /lib>` >
      choose all modules > apply
    - Go to `"Run Configuration"`
        - add or choose existing
        - VM options:
            - `--module-path <path to unpacked JavaFX SDK /lib> --add-modules=javafx.controls,javafx.fxml,javafx.swing`
- (Optional) Install SceneBuilder for fxml UI-based development
    - Download and install SceneBuilder: https://gluonhq.com/products/scene-builder/
    - In IDE go to `"Settings"` > `Languages & Frameworks` > `JavaFX` and specify path to
      SceneBuilder app

### Build

Build to `build/libs/`

```bash
./gradlew clean build
```

Run

```bash
java -jar build/libs/pstorage-0.0.4.jar
```

## Changelog

#### 2020-03-07

**Version 0.0.3**

- Minimize to tray on close
- Password's value and alias updating
- Password removing

#### 2019-08-24

- Java 11 supporting
- Build now depends on JAVA_HOME and Java version (8, 11+)
- Technologies table updated
- "How to build" section added
- System Tray error fixed on startup

#### 2019-08-23

- Logging configuration added
- Configuration file migrated from .properties to .yml
- Checkstyle added and configured to Google Style with HTML report
- Information about technologies added
- Changelog created

## Thanks

Thanks [habr.com/ruslanys](https://habr.com/ru/users/ruslanys/) for a guide JavaFX with Spring
Boot: https://habr.com/ru/post/265511/
