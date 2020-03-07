## Status
| Status | master | dev |
| --- | --- | --- |
| Build | [![Build Status](https://shaaart.visualstudio.com/pstorage/_apis/build/status/shaart.pstorage?branchName=master)](https://shaaart.visualstudio.com/pstorage/_build/latest?definitionId=1&branchName=master) | [![Build Status](https://shaaart.visualstudio.com/pstorage/_apis/build/status/shaart.pstorage?branchName=dev)](https://shaaart.visualstudio.com/pstorage/_build/latest?definitionId=1&branchName=dev) |
| Code quality | [![CodeFactor](https://www.codefactor.io/repository/github/shaart/pstorage/badge/master?s=01f316b05c9b3631acd487abdf9ae42c2bae56f5)](https://www.codefactor.io/repository/github/shaart/pstorage/overview/master) | [![CodeFactor](https://www.codefactor.io/repository/github/shaart/pstorage/badge/dev?s=01f316b05c9b3631acd487abdf9ae42c2bae56f5)](https://www.codefactor.io/repository/github/shaart/pstorage/overview/dev) |

## About
Password Storage desktop application.

# How to build
Build depends on current Java version.
- If JAVA_HOME references to JDK 8 - then build will use it.
- If JAVA_HOME references to JDK 11 - then build will include OpenJFX because JavaFX is not supported since Java 10.
- If JAVA_HOME references to JDK under 8 version - then build will be failed with "Unsupported operation" error.
```bash
export JAVA_HOME="<path to JDK 8 or 11>"
gradle build
```

## Technologies & Tools
| Title | Version |
|---|---|
| **Language** |
| Java | 8 |
| **Framework** |
| JavaFX  | 2.0 |
| OpenJFX  | 11.0.2 |
| Spring Boot | 2.1.6.RELEASE |
| **Database**
| Spring Data | 2.1.6.RELEASE |
| H2 | 1.4.199 |
| Liquibase | 3.7.0 |
|**Other**|
| Gradle | 5.4.1 |
| Lombok | 1.18.8 |
| Checkstyle plugin | 8.23 |
| OpenJFX plugin | 0.0.8 |


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
Thanks [habr.com/ruslanys](https://habr.com/ru/users/ruslanys/) for a guide JavaFX with Spring Boot: https://habr.com/ru/post/265511/
