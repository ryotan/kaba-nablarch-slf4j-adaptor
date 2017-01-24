# [WIP] kaba-nablarch-slf4j-adaptor

[![Apache License, Version 2.0, January 2004](https://img.shields.io/github/license/ryotan/kaba-nablarch-slf4j-adaptor.svg?style=flat)](http://www.apache.org/licenses/)
[![CircleCI](https://circleci.com/gh/ryotan/kaba-vault.svg?style=shield)](https://circleci.com/gh/ryotan/kaba-vault)
[![Codacy code quality](https://api.codacy.com/project/badge/grade/c2f6640f356144d082825ec1dfd99e7f)](https://www.codacy.com/app/ryotan/kaba-vault?utm_source=github.com&utm_medium=referral&utm_content=ryotan/kaba-vault&utm_campaign=Badge_Grade)
[![Codacy code coverage](https://api.codacy.com/project/badge/coverage/c2f6640f356144d082825ec1dfd99e7f)](https://www.codacy.com/app/ryotan/kaba-vault?utm_source=github.com&utm_medium=referral&utm_content=ryotan/kaba-vault&utm_campaign=Badge_Coverage)
[![Dependency Status](https://www.versioneye.com/user/projects/573791b7a0ca35004baf9553/badge.svg)](https://www.versioneye.com/user/projects/573791b7a0ca35004baf9553)

[SLF4J](https://www.slf4j.org/) logger adaptor for [Nablarch](https://github.com/nablarch/nablarch) logger.

## Problem

Nablarch does not provides log file writers which supports scheduled log file rotation or generation management. But sometimes, I need it (and unwillingly use `logrotate`).


## Difference of log formatter between Nablarch and Logback

Nablarch uses [BasicLogFormatter](https://nablarch.github.io/docs/5u7/javadoc/nablarch/core/log/basic/BasicLogFormatter.html) as the default log formatter.

`BasicLogFormatter` can output Nablarch specific information like `userId` or `executionId`.
Of course, SLF4J (or its implementation) never know such information, so kaba-nablarch-slf4j-adaptor adds Nablarch specific information to [MDC](https://logback.qos.ch/manual/mdc.html).

Here, describe the differences of placeholders of log formatter between Nablarch's `BasicLogFormatter`, and [Logback](https://logback.qos.ch/)([PatternLayout](https://logback.qos.ch/manual/layouts.html#ClassicPatternLayout)), the de facto standard SLF4J implementation.

|                           | Nablarch (BasicLogFormatter) | Logback (PatternLayout) | Logback w/ this lib    |
|:--------------------------|------------------------------|-------------------------|------------------------|
| Date                      | `$date$`                     | `%date`                 | same as PatternLayout  |
| Log Level                 | `$logLevel$`                 | `%level`                | same as PatternLayout  |
| Logger                    | `$loggerName$`               | `%logger`               | same as PatternLayout  |
| Log message               | `$message$`                  | `%message`              | same as PatternLayout  |
| Exception stacktrace      | `$stackTrace$`               | `%exception`            | same as PatternLayout  |
| Application boot process  | `$bootProcess$`              | -                       | `%X{bootProcess}`      |
| Processing system..?      | `$processingSystem$`         | -                       | `%X{processingSystem}` |
| Request ID                | `$requestId$`                | -                       | `%X{requestId}`        |
| Execution ID              | `$executionId$`              | -                       | `%X{executionId}`      |
| User ID                   | `$userId$`                   | -                       | `%X{userId}`           |
| Additional information    | `$information$`              | -                       | -                      |


## Usage

### Add Maven repository

Stable versions are hosted on [Bintray JCenter](https://jcenter.bintray.com),
and `SNAPSHOT` versions are hosted on [oss.jfrog.org](https://oss.jfrog.org/libs-snapshot).

If you want to use a stable version, you need to insert the following into your parent POM.

```xml
<repositories>
  <repository>
    <id>jcenter</id>
    <name>Bintray JCenter</name>
    <url>https://jcenter.bintray.com</url>
    <releases>
      <enabled>true</enabled>
    </releases>
    <snapshots>
      <enabled>false</enabled>
    </snapshots>
  </repository>
</repositories>
```

If you want to use s `SNAPSHOT` version, you need to insert the following into your parent POM.

```xml
<repositories>
  <repository>
    <id>jfrog-oss-snapshot</id>
    <name>JFrog OSS Snapshots</name>
    <url>https://oss.jfrog.org/libs-snapshot</url>
    <releases>
      <enabled>false</enabled>
    </releases>
    <snapshots>
      <enabled>true</enabled>
    </snapshots>
  </repository>
</repositories>
```

### Add to dependency

Add kaba-nablarch-slf4j-adaptor to dependencies in POM.

```xml
<dependency>
  <groupId>pw.itr0.kaba.nablarch</groupId>
  <artifactId>kaba-nablarch-slf4j-adaptor</artifactId>
  <version>0.1.0-SNAPSHOT</version>
</dependency>
```

## Example

Example `logback.xml` files are located in `example` directory.

## License

Copyright © 2017- [Ryo Tanaka](https://github.com/ryotan)

kaba-nablarch-slf4j-adaptor is licensed under the terms of the Apache License, Version 2.0. See [LICENSE](LICENSE) for more information.
