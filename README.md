# kjson-spring

[![Build Status](https://travis-ci.com/pwall567/kjson-spring.svg?branch=main)](https://travis-ci.com/github/pwall567/kjson-spring)
[![License: MIT](https://img.shields.io/badge/License-MIT-yellow.svg)](https://opensource.org/licenses/MIT)
[![Kotlin](https://img.shields.io/static/v1?label=Kotlin&message=v1.6.10&color=7f52ff&logo=kotlin&logoColor=7f52ff)](https://github.com/JetBrains/kotlin/releases/tag/v1.6.10)
[![Maven Central](https://img.shields.io/maven-central/v/io.kjson/kjson-spring?label=Maven%20Central)](https://search.maven.org/search?q=g:%22io.kjson%22%20AND%20a:%22kjson-spring%22)

Spring JSON message converter for kjson

## Background

Many users of the [`kjson`](https://github.com/pwall567/kjson) library will seek to use the library in conjunction with
the [Spring Framework](https://spring.io/projects/spring-framework).
The `kjson-spring` library provides a JSON converter class that will use `kjson` as the serialization / deserialization
mechanism.

## Quick Start

To direct a Spring Boot application to use `kjson` for serialization and deserialization of HTTP(S) requests and
responses, simply include the `kjson-spring` library in the build, and then ensure that the `io.kjson.spring` package is
included in the Spring component scan:
```kotlin
@ComponentScan("io.kjson.spring")
```
or:
```kotlin
@ComponentScan(basePackageClasses = [JSONSpring::class])
```
or, when using Spring XML configuration:
```xml
    <context:component-scan base-package="io.kjson.spring"/>
```
Note that the default Spring behaviour is to scan the package in which the `@ComponentScan`
(or `@SpringBootApplication`) occurs.
When one or more packages or classes are specified on a `@ComponentScan`, only the specified package(s) will be scanned;
to include the current package it must be specified explicitly.

## Configuration

The `kjson` serialization and deserialization functions all take an optional
[`JSONConfig`](https://github.com/pwall567/kjson/blob/main/USERGUIDE.md#configuration) object.
The `JSONConfig` to be used by the `kjson-spring` library may be provided in the usual Spring manner:
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
If no `JSONConfig` is provided the `JSONConfig.defaultConfig` will be used.

## Client REST Calls

Client REST calls using the `RestTemplate` class can also make use of the `kjson` serialization and deserialization
functions.
When the `RestTemplate` is obtained from the default `RestTemplateBuilder`, it will be configured with all of the
`HttpMessageConverter` instances that it locates in the component scan &ndash; that will include the `kjson`
converter if the component scan is configured as described above.

The following line will get the default `RestTemplateBuilder`:
```kotlin
    @Autowired lateinit var restTemplateBuilder: RestTemplateBuilder
```
And then, the following will get a `RestTemplate` will the converters configured in the `RestTemplateBuilder`:
```kotlin
        val restTemplate = restTemplateBuilder.build()
```

Alternatively, the `RestTemplate` may be constructed with the converter specified explicitly:
```kotlin
    @Autowired lateinit var jsonSpring: JSONSpring
```
and:
```kotlin
        val restTemplate = RestTemplate(listOf(jsonSpring))
```

## Dependency Specification

The latest version of the library is 3.2 (the version number of this library matches the version of `kjson` with which
it was built), and it may be obtained from the Maven Central repository.

This version was built using version 5.3.20 of Spring, and version 2.7.0 of Spring Boot.

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

2022-08-01
