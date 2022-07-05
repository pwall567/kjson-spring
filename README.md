# kjson-spring

[![Build Status](https://travis-ci.com/pwall567/kjson-spring.svg?branch=main)](https://travis-ci.com/github/pwall567/kjson-spring)
[![License: MIT](https://img.shields.io/badge/License-MIT-yellow.svg)](https://opensource.org/licenses/MIT)
[![Kotlin](https://img.shields.io/static/v1?label=Kotlin&message=v1.6.10&color=7f52ff&logo=kotlin&logoColor=7f52ff)](https://github.com/JetBrains/kotlin/releases/tag/v1.6.10)
[![Maven Central](https://img.shields.io/maven-central/v/io.kjson/kjson-spring?label=Maven%20Central)](https://search.maven.org/search?q=g:%22io.kjson%22%20AND%20a:%22kjson-spring%22)

Spring JSON message converter for kjson

## Background

Many users will seek to use the [`kjson`](https://github.com/pwall567/kjson) library in conjunction with the
[Spring Framework](https://spring.io/projects/spring-framework).
The `kjson-spring` library provides a JSON converter class that will use `kjson` as the serialization / deserialization
mechanism.

## Quick Start

To direct a Spring Boot application to use `kjson` for serialization and deserialization of HTTP(S) requests and
responses, include simply this library in the build, and then ensure that the `io.kjson.spring` package is included in
the component scan:
```kotlin
@ComponentScan("io.kjson.spring")
```

The `JSONConfig` to be used by the library may be provided in the usual Spring manner:
```kotlin
@Configuration
open class SpringAppConfig {

    @Bean open fun config(): JSONConfig {
        return JSONConfig {
            allowExtra = true
        }
    }

}
```
This example shows just the `allowExtra` option being set; any of the configuration options, including custom
serialization and deserialization, may be used.

## Dependency Specification

The latest version of the library is 3.2 (the version number of this library matches the version of `kjson` with which
it was built), and it may be obtained from the Maven Central repository.

### Maven
```xml
    <dependency>
      <groupId>io.kjson</groupId>
      <artifactId>kjson-spring</artifactId>
      <version>3.2</version>
    </dependency>
```
### Gradle
```groovy
    implementation 'io.kjson:kjson-spring:3.2'
```
### Gradle (kts)
```kotlin
    implementation("io.kjson:kjson-spring:3.2")
```

Peter Wall

2022-07-05
