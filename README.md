<img src="https://www.ga4gh.org/wp-content/themes/ga4gh-theme/gfx/GA-logo-horizontal-tag-RGB.svg" alt="GA4GH Logo" style="width: 400px;"/>

[![License](https://img.shields.io/badge/License-Apache%202.0-blue.svg?style=flat-square)](https://opensource.org/licenses/Apache-2.0)
[![Java 11+](https://img.shields.io/badge/java-11+-blue.svg?style=flat-square)](https://www.java.com)
[![Gradle 6.1+](https://img.shields.io/badge/gradle-6.1+-blue.svg?style=flat-square)](https://gradle.org/)
[![Travis (.org) branch](https://img.shields.io/travis/ga4gh/ga4gh-registry/master.svg?style=flat-square)](https://travis-ci.org/ga4gh/ga4gh-registry)
![Codecov](https://img.shields.io/codecov/c/github/ga4gh/ga4gh-registry?style=flat-square)

# GA4GH Planet API

## Using the API

See the [GA4GH Planet OpenAPI Docs]() for full developer documentation on the production API.

The production API is available at `https://registry.ga4gh.org/v1/`. Example HTTPS requests include:

* https://registry.ga4gh.org/v1/standards (view GA4GH standards)
* https://registry.ga4gh.org/v1/services (get registered web services based on canonical GA4GH service types)
* https://registry.ga4gh.org/v1/organizations (view organizations with registered services or implementations)


## Starting the test server

### Usage

Run Spring Boot application with externalized properies file:
```
./gradlew bootRun --args="--spring.config.location=file:./config/test/test.application.properties"
```

### Testing

To run all tests, execute:
```
./gradlew test
```

The test report directory will be available at `./build/reports/tests/test`. A local server can be started in that directory to view the report.

To generate the test coverage report, execute the following (after the above test report has been generated):

```
./gradlew jacocoTestReport
```

The coverage report directory will be available at `./build/reports/jacoco/test/html`. A local server can be started in the directory to view the report.

## Maintainers

* Jeremy Adams [jeremy.adams@ga4gh.org](mailto:jeremy.adams@ga4gh.org)

## Issues

Feature requests, issues, and bugs may be reported via the [Github issue tracker](https://github.com/ga4gh/ga4gh-registry/issues), or by emailing one of the above maintainers directly.