![](https://bitbucket.org/arcticicestudio/icecore-strman/raw/develop/src/main/assets/media/icecore-strman-banner.png)

<img src="https://bitbucket.org/favicon.ico" width=24 height=24/> [![release](https://img.shields.io/badge/release-v0.3.0-blue.svg)](https://bitbucket.org/arcticicestudio/icecore-strman/downloads) [![pre-release](https://img.shields.io/badge/pre--release---_-blue.svg)](https://bitbucket.org/arcticicestudio/icecore-strman/downloads) [![issues](https://img.shields.io/bitbucket/issues-raw/arcticicestudio/icecore-strman.svg?maxAge=86400)](https://bitbucket.org/arcticicestudio/icecore-strman/issues)

Lightweight module library as part of the [IceCore](https://bitbucket.org/arcticicestudio/icecore) engine to manipulate- and modify strings.

## Getting started
### Setup
To use icecore-strman it must be available on your classpath.  
You can use it as a dependency for your favorite build tool or [download the latest version](https://bitbucket.org/arcticicestudio/icecore-strman/downloads).

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

### Build
Build and install icecore-strman into your local repository without GPG signing:  
```
mvn clean install
```

Signed artifacts may be build by using the `sign-gpg` profile with a provided `gpg.keyname` property:
```
mvn clean install -Dgpg.keyname=YourGPGKeyId
```

## Development
[![](https://img.shields.io/badge/Changelog-v0.3.0-blue.svg)](https://bitbucket.org/arcticicestudio/icecore-strman/raw/v0.3.0/CHANGELOG.md)

### Workflow
This project follows the [gitflow](http://nvie.com/posts/a-successful-git-branching-model) branching model.

### Specifications
This project follows the [Arctic Versioning Specification](https://github.com/arcticicestudio/arcver).

### Contribution
Please report issues/bugs, feature requests and suggestions for improvements to the [issue tracker](https://bitbucket.org/arcticicestudio/icecore-strman/issues).

---

<img src="http://arcticicestudio.com/favicon.ico" width=16 height=16/> Copyright &copy; 2016 Arctic Ice Studio

[![GPL-3.0](http://www.gnu.org/graphics/gplv3-88x31.png)](http://www.gnu.org/licenses/gpl.txt)
