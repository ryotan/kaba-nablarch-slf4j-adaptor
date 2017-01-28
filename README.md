# [WIP] kaba-nablarch-slf4j-adaptor

[![Apache License, Version 2.0, January 2004](https://img.shields.io/github/license/ryotan/kaba-nablarch-slf4j-adaptor.svg?style=flat)](http://www.apache.org/licenses/)
[![CircleCI](https://circleci.com/gh/ryotan/kaba-nablarch-slf4j-adaptor.svg?style=shield)](https://circleci.com/gh/ryotan/kaba-nablarch-slf4j-adaptor)
[![Codacy code quality](https://api.codacy.com/project/badge/grade/efb68232f2ff4302bae27935b897467d)](https://www.codacy.com?utm_source=github.com&utm_medium=referral&utm_content=ryotan/kaba-nablarch-slf4j-adaptor&utm_campaign=Badge_Grade)
[![Codacy code coverage](https://api.codacy.com/project/badge/coverage/efb68232f2ff4302bae27935b897467d)](https://www.codacy.com/app/ryotan/kaba-vault?utm_source=github.com&utm_medium=referral&utm_content=ryotan/kaba-vault&utm_campaign=Badge_Coverage)
[![Dependency Status](https://www.versioneye.com/user/projects/5888e09ec64626004e05780e/badge.svg)](https://www.versioneye.com/user/projects/573791b7a0ca35004baf9553)

[SLF4J](https://www.slf4j.org/) logger adaptor for [Nablarch](https://github.com/nablarch/nablarch) logger.

## Problem

Nablarch does not provides log file writers which supports scheduled log file rotation or generation management. But sometimes, I need it (and unwillingly use `logrotate`).


## Include Nablarch specific information in MDC

Nablarch uses [BasicLogFormatter](https://nablarch.github.io/docs/5u7/javadoc/nablarch/core/log/basic/BasicLogFormatter.html) as the default log formatter.

`BasicLogFormatter` can output Nablarch specific information like `userId` or `executionId`.
Of course, SLF4J (or its implementation) never know such information, For such information, you can use  `MDCInsertingHandler`.

Create handlers like [MDCInsertingServletFilter](https://logback.qos.ch/xref/ch/qos/logback/classic/helpers/MDCInsertingServletFilter.html) and put Nablarch specific information to [MDC](https://logback.qos.ch/manual/mdc.html) there.
These handlers should add Nablarch specific information to `MDC` on forward path and clean it on return path.

> **Note**: Since handlers remove information from MDC on return path, these information could not be logged at handlers above it.
>   If you are tracing errors occurred at such handlers, use alternative information such as listed below.
>   * URL
>   * ThreadName 
>   * `NABLARCH_SID`

An implementation of handler will look like below.

```java
public class MDCInsertingHandler<REQ, RES> implements Handler<REQ, RES> {
    private List<MDCAttribute<REQ>> attributes = Collections.emptyList();

    @Override
    public RES handle(REQ req, ExecutionContext context) {
        try {
            insertMDC(req, context);
            return context.handleNext(req);
        } finally {
            cleanUpMDC();
        }
    }

    private void insertMDC(REQ req, ExecutionContext context) {
        attributes.forEach(attr -> MDC.put(attr.key(), attr.value(req, context)));
    }

    private void cleanUpMDC() {
        attributes.forEach(attr -> MDC.remove(attr.key()));
    }

    public void setMDCAttributes(List<MDCAttribute<REQ>> attributes) {
        this.attributes = attributes;
    }
}
```

For servlet applications, also you can create `Filter` to add HTTP request specific information to `MDC`. See manual page([English](https://logback.qos.ch/manual/mdc.html#autoMDC), [Japanese](https://logback.qos.ch/manual/mdc_ja.html#autoMDC)) for more information.
FYI, kaba-nablarch-slf4j-adaptor provides simple example `Filter`([`KabaMDCInsertingServletFilter`](/src/main/java/pw/itr0/kaba/nablarch/slf4j/filter/KabaMDCInsertingServletFilter.java)).


## Place holders of Nablarch's `BasicLogFormatter` and Logback's `PatternLayout`

Here, describe the differences of placeholders of log formatter between Nablarch's `BasicLogFormatter`, and [Logback](https://logback.qos.ch/)([PatternLayout](https://logback.qos.ch/manual/layouts.html#ClassicPatternLayout)), the de facto standard SLF4J implementation.

|                           | Nablarch (BasicLogFormatter) | Logback (PatternLayout conversion)     |
|:--------------------------|------------------------------|----------------------------------------|
| Date                      | `$date$`                     | `%date`                                |
| Log Level                 | `$logLevel$`                 | `%level`                               |
| Logger                    | `$loggerName$`               | `%logger`                              |
| Log message               | `$message$`                  | `%message`                             |
| Exception stacktrace      | `$stackTrace$`               | `%exception`                           |
| Application boot process  | `$bootProcess$`              | `%property{nablarch.bootProcess}`      |
| Processing system         | `$processingSystem$`         | `%property{nablarch.processingSystem}` |

Since Logback provides many conversion words ([English](https://logback.qos.ch/manual/layouts.html#ClassicPatternLayout), [Japanese](https://logback.qos.ch/manual/layouts_ja.html#ClassicPatternLayout)), use those words effectively.


## Restrictions

### `FATAL` level log is outputted as `ERROR` level

Since SLF4J does not provide `FATAL` log level, Nablarch `FATAL` log level is outputted as SLF4J `ERROR` level.

### Not fully supported APIs

Because of the differences between `nablarch.core.log.Logger` interface and `org.slf4j.Logger` interface , the following APIs are not fully supported.

| Nablarch Logger API                               | What's not supported                     |
|---------------------------------------------------|------------------------------------------|
| `Logger#logFatal(String, Throwable, Object...)`   | Third argument (`Object...`) is ignored. |
| `Logger#logError(String, Throwable, Object...)`   | Third argument (`Object...`) is ignored. |
| `Logger#logWarn(String, Throwable, Object...)`    | Third argument (`Object...`) is ignored. |
| `Logger#logInfo(String, Throwable, Object...)`    | Third argument (`Object...`) is ignored. |
| `Logger#logTrace(String, Throwable, Object...)`   | Third argument (`Object...`) is ignored. |


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

### Configure Nablarch logger



## Example

Example `logback.xml` files are located in `example` directory.

## License

Copyright © 2017- [Ryo Tanaka](https://github.com/ryotan)

kaba-nablarch-slf4j-adaptor is licensed under the terms of the Apache License, Version 2.0. See [LICENSE](LICENSE) for more information.
