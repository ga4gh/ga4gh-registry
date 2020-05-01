<img src="https://www.ga4gh.org/wp-content/themes/ga4gh-theme/gfx/GA-logo-horizontal-tag-RGB.svg" alt="GA4GH Logo" style="width: 400px;"/>

[![License](https://img.shields.io/badge/License-Apache%202.0-blue.svg?style=flat-square)](https://opensource.org/licenses/Apache-2.0)
[![Java 11+](https://img.shields.io/badge/java-11+-blue.svg?style=flat-square)](https://www.java.com)
[![Gradle 6.1+](https://img.shields.io/badge/gradle-6.1+-blue.svg?style=flat-square)](https://gradle.org/)
[![Travis (.org) branch](https://img.shields.io/travis/ga4gh/ga4gh-registry/master.svg?style=flat-square)](https://travis-ci.org/ga4gh/ga4gh-registry)
![Codecov](https://img.shields.io/codecov/c/github/ga4gh/ga4gh-registry?style=flat-square)

# GA4GH Standards and Implementations Registry

## Usage

Run Spring Boot application with externalized properies file:
```
./gradlew bootRun --args="--spring.config.location=file:./config/test/test.application.properties"
```

Execute tests:
```
./gradlew test
```
