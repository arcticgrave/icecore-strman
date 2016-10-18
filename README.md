<p align="center"><img src="https://cdn.rawgit.com/arcticicestudio/icecore-strman/develop/src/main/assets/icecore-strman-logo-banner.svg"/></p>

<p align="center"><img src="https://cdn.travis-ci.org/images/favicon-c566132d45ab1a9bcae64d8d90e4378a.svg" width=24 height=24/> <a href="https://travis-ci.org/arcticicestudio/icecore-strman"><img src="https://img.shields.io/travis/arcticicestudio/icecore-strman/develop.svg"/></a> <img src="https://circleci.com/favicon.ico" width=24 height=24/> <a href="https://circleci.com/bb/arcticicestudio/icecore-strman"><img src="https://circleci.com/bb/arcticicestudio/icecore-strman.svg?style=shield&circle-token=3e284de9a416e0121ed536d1b6809b7c27d778d0"/></a> <img src="https://codecov.io/favicon.ico" width=24 height=24/> <a href="https://codecov.io/gh/arcticicestudio/icecore-strman"><img src="https://codecov.io/gh/arcticicestudio/icecore-strman/branch/develop/graph/badge.svg"/></a> <img src="https://assets-cdn.github.com/favicon.ico" width=24 height=24/> <a href="https://github.com/arcticicestudio/icecore-strman/releases/latest"><img src="https://img.shields.io/github/release/arcticicestudio/icecore-strman.svg"/></a> <a href="https://github.com/arcticicestudio/icecore-strman/releases/latest"><img src="https://img.shields.io/badge/pre--release---_-blue.svg"/></a> <img src="http://central.sonatype.org/favicon.ico" width=24 height=24/> <a href="http://search.maven.org/#search%7Cgav%7C1%7Cg%3A%22com.arcticicestudio%22%20AND%20a%3A%22icecore-strman%22"><img src="https://img.shields.io/maven-central/v/com.arcticicestudio/icecore-strman.svg"/></a> <img src="https://oss.sonatype.org/favicon.ico"/> <a href="https://oss.sonatype.org/content/repositories/snapshots/com/arcticicestudio/icecore-strman"><img src="https://img.shields.io/badge/snapshot----blue.svg"/></a> <img src="https://bintray.com/favicon.ico" width=24 height=24/> <a href='https://bintray.com/arcticicestudio/IceCore/icecore-strman/_latestVersion'><img src='https://api.bintray.com/packages/arcticicestudio/IceCore/icecore-strman/images/download.svg'></a></p>

<p align="center">A lightweight library to manipulate- and modify strings.</p>

---

## Getting started
### Setup
To use icecore-strman it must be available on your classpath.  
You can use it as a dependency for your favorite build tool or [download the latest version](https://github.com/arcticicestudio/icecore-strman/releases/latest).

<img src="http://apache.org/favicons/favicon.ico" width=16 height=16/> <a href="https://maven.apache.org">Apache Maven</a>
```xml
<dependency>
  <groupId>com.arcticicestudio</groupId>
  <artifactId>icecore-strman</artifactId>
  <version>0.3.0</version>
</dependency>
```

<img src="https://gradle.org/wp-content/uploads/fbrfg/favicon.ico" width=16 height=16/> <a href="https://gradle.org">Gradle</a>
```java
compile(group: 'com.arcticicestudio', name: 'icecore-strman', version: '0.3.0')
```

<img src="http://apache.org/favicons/favicon.ico" width=16 height=16/> <a href="https://ant.apache.org/ivy">Apache Ivy</a>
```xml
<dependency org="com.arcticicestudio" name="icecore-strman" rev="0.3.0" />
```

Development snapshots are available via [OSS Sonatype](https://oss.sonatype.org/content/repositories/snapshots/com/arcticicestudio/icecore-strman).

### Build
Build and install icecore-strman into your local repository without GPG signing:  
```
mvn clean install
```

Signed artifacts may be build by using the `sign-gpg` profile with a provided `gpg.keyname` property:
```
mvn clean install -Dgpg.keyname=YourGPGKeyId
```

Continuous integration builds are running at [Travis CI](https://travis-ci.org/arcticicestudio/icecore-strman) and [Circle CI](https://circleci.com/bb/arcticicestudio/icecore-strman).

## Development
[![](https://img.shields.io/badge/Changelog-0.3.0-blue.svg)](https://github.com/arcticicestudio/icecore-strman/blob/v0.3.0/CHANGELOG.md) [![](https://img.shields.io/badge/Workflow-gitflow_Branching_Model-blue.svg)](http://nvie.com/posts/a-successful-git-branching-model) [![](https://img.shields.io/badge/Versioning-ArcVer_0.8.0-blue.svg)](https://github.com/arcticicestudio/arcver)

### Contribution
Please report issues/bugs, feature requests and suggestions for improvements to the [issue tracker](https://github.com/arcticicestudio/icecore-strman/issues).

## Credits
Inspired by the 
  - <a href="https://github.com/shekhargulati/strman-java"><img src="https://www.java.com/favicon.ico" width=16 height=16/> strman-java</a>
  - <a href="https://github.com/dleitee/strman"><img src="https://www.javascript.com/images/favicon.ico" width=16 height=16/> strman</a>
  - <a href="https://github.com/danielstjules/Stringy"><img src="http://php.net/favicon.ico" width=16 height=16/> Stringy</a>

implementations.

---

<p align="center"> <img src="http://arcticicestudio.com/favicon.ico" width=16 height=16/> Copyright &copy; 2016 Arctic Ice Studio</p>

<p align="center"><a href="http://www.apache.org/licenses/LICENSE-2.0"><img src="https://img.shields.io/badge/License-Apache_2.0-blue.svg"/></a></p>
