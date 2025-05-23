<img src="https://www.ga4gh.org/wp-content/themes/ga4gh/dist/assets/svg/logos/logo-full-color.svg" alt="GA4GH Logo" style="width: 400px;"/>

[![License](https://img.shields.io/badge/License-Apache%202.0-blue.svg?style=flat-square)](https://opensource.org/licenses/Apache-2.0)
[![Java 11+](https://img.shields.io/badge/java-11+-blue.svg?style=flat-square)](https://www.java.com)
[![Gradle 6.1+](https://img.shields.io/badge/gradle-6.1+-blue.svg?style=flat-square)](https://gradle.org/)
[![Travis (.org) branch](https://img.shields.io/travis/ga4gh/ga4gh-registry/master.svg?style=flat-square)](https://travis-ci.org/ga4gh/ga4gh-registry)
![Codecov](https://img.shields.io/codecov/c/github/ga4gh/ga4gh-registry?style=flat-square)

# GA4GH Planet API

## Using the API

See the [GA4GH Planet OpenAPI Docs](https://ga4gh.github.io/ga4gh-registry/docs/) for full developer documentation on the production API.

The production API is available at `https://registry.ga4gh.org/v1/`. Example HTTPS requests include:

* https://registry.ga4gh.org/v1/service-info (view the service info of the implementations)
* https://registry.ga4gh.org/v1/standards (view GA4GH standards)
* https://registry.ga4gh.org/v1/services (get registered web services based on canonical GA4GH service types)
* https://registry.ga4gh.org/v1/implementations (view the implementations of the GA4GH standards)
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

## Register an implementation

To register an implementation, please enter the information for the implementation being registered in the form of the json object below then send it to the GA4GH tech team email found in the maintainers section.

```
  {
    "id": "edu.caltech.rnaget",
    "name": "Caltech RNAget Reference Implementation",
    "type": {
      "group": "org.ga4gh",
      "artifact": "rnaget",
      "version": "1.0.0"
    },
    "organization": {
      "id": "edu.caltech",
      "name": "California Institute of Technology",
      "shortName": "Caltech",
      "url": "https://www.caltech.edu/"
    },
    "version": "1.0.0",
    "url": "https://felcat.caltech.edu/rnaget",
    "description": "RNAget implementation serving compliance test dataset",
    "contactUrl": "sau@caltech.edu",
    "documentationUrl": "https://felcat.caltech.edu/rnaget",
    "environment": "production"
  }
```

## Maintainers

* GA4GH Tech Team [ga4gh-tech-team@ga4gh.org](mailto:ga4gh-tech-team@ga4gh.org)

## Issues

Feature requests, issues, and bugs may be reported via the [Github issue tracker](https://github.com/ga4gh/ga4gh-registry/issues), or by emailing one of the above maintainers directly.

## Changelog

### v0.5.1
* Patched log4j dependencies to v2.16.0 to avoid [Log4j Vulnerability](https://www.cisa.gov/uscert/apache-log4j-vulnerability-guidance)