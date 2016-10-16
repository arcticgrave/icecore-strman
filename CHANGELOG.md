<p align="center"><img src="https://bitbucket.org/arcticicestudio/icecore-strman/raw/develop/src/main/assets/icecore-strman-logo-banner.svg"/></p>

<p align="center"><img src="https://bitbucket.org/favicon.ico" width=24 height=24/> <a href="https://bitbucket.org/arcticicestudio/icecore-strman/downloads"><img src="https://img.shields.io/badge/release-0.2.0-blue.svg"/></a> <a href="https://bitbucket.org/arcticicestudio/icecore-strman/downloads"><img src="https://img.shields.io/badge/pre--release---_-blue.svg"/></a> <img src="http://central.sonatype.org/favicon.ico" width=24 height=24/> <a href="http://search.maven.org/#search%7Cgav%7C1%7Cg%3A%22com.arcticicestudio%22%20AND%20a%3A%22icecore-strman%22"><img src="https://img.shields.io/maven-central/v/com.arcticicestudio/icecore-strman.svg"/></a> <img src="https://oss.sonatype.org/favicon.ico"/> <a href="https://oss.sonatype.org/content/repositories/snapshots/com/arcticicestudio/icecore-strman"><img src="https://img.shields.io/badge/snapshot----blue.svg"/></a> <img src="https://bintray.com/favicon.ico" width=24 height=24/> <a href='https://bintray.com/arcticicestudio/IceCore/icecore-strman/_latestVersion'><img src='https://api.bintray.com/packages/arcticicestudio/IceCore/icecore-strman/images/download.svg'></a></p>

---

# 0.3.0 (2016-08-20)
This version introduces some new API methods, migrates the project to [Apache Maven](https://maven.apache.org) and improves the documentation.

## Features
### API
Implemented new methods:  

**capitalize**  
Converts the first character of a string to upper case and the remaining to lower case.  
> <u>`+ capitalize(String) : String {exceptions=IllegalArgumentException}`</u>  
```java
String str = capitalize("yOGURT") // "Yogurt"
```

**join**  
Concatenates all the elements of the string array into a single string.  
The separator string is placed between elements in the resulting string.  
> <u>`+ join(String[], String) : String {exceptions=IllegalArgumentException}`</u>  
```java
String[] array = {"yogurt", "coconut", "chocolate"}
String str = join(strArray, "-") // "yogurt-coconut-chocolate"
```

**lowerFirst**  
Converts the first character of a string to lower case.   
> <u>`+ lowerFirst(String) : String {exceptions=IllegalArgumentException}`</u>  
```java
String str = lowerFirst("Yogurt") // "yogurt"
```

## Improvements
### Build Tool
  - Migrated to [Apache Maven](https://maven.apache.org) based on the [`glacier-apache-maven@0.4.0`](https://github.com/arcticicestudio/glacier-apache-maven) project skeletons

### Documentation
  - All documentations have been restyled and modernized
  - Added a "Getting started" section to the `README`
  - Optimized Java source code comments and removed overhead

**JavaDoc**  
  - Changed invalid HTML-formatted JavaDoc
  - Adjusted line length from `80` to full `120` line length
  - Added missing JavaDoc for internal JUnit test classes

# 0.2.0 (2016-06-28)
## Improvements
### API
#### Performance
  - Improved the case comparision performance and reduced the string creation.
  The `+ isLowerCase(String) : boolean` and `+ isUpperCase(String) : boolean` methods created unnecessary strings while comparing. Changed the logic to compare each single character and return `false` as soon as the case doesn't match.

# 0.1.1 (2016-06-28)
## Bug Fixes
### API
  - Fixed two methods returning an invalid case sensitivity output due to lower case convertation:

| Class | Method |
| ----- | ------ |
| `com.arcticicestudio.icecore.strman.Strman` | `+ removeLeft(String, String, boolean) : String` |
| `com.arcticicestudio.icecore.strman.Strman` | `+ replace(String, String, String, boolean) : String` |

# 0.1.0 (2016-06-18) - Public API
## Features
### API
  - Implemented public API classes:

| Class | Description |
| ----- | ----------- |
| `com.arcticicestudio.icecore.strman.Strman` | Manipulates- and modifies strings. Serves as the entry point to the "IceCore - String Manipulation" public API. |

- Implemented public API methods:

| Class | Method | Description |
| ----- | ------ | ----------- |
| `com.arcticicestudio.icecore.strman.Strman` | `+ append(String, String...) : String` | Appends strings to a string. |
| `com.arcticicestudio.icecore.strman.Strman` | `+ appendArray(String, String[]) : String` | Appends an array of strings to a string. |
| `com.arcticicestudio.icecore.strman.Strman` | `+ at(String, int) : Optional<String>` | Gets the character at the specified index. Takes care of negative indexes. The valid value of the index is between *-(length-1)* to *(length-1)*. For values which don't fall under this range `Optional.empty` will be returned. |
| `com.arcticicestudio.icecore.strman.Strman` | `+ base64Decode(String) : String` | Decodes a MIME base64 encoded string. |
| `com.arcticicestudio.icecore.strman.Strman` | `+ base64Encode(String) : String` | Encodes a string with MIME base64. |
| `com.arcticicestudio.icecore.strman.Strman` | `+ between(String, String, String) : String[]` | Extracts a string between a given start- and end string. |
| `com.arcticicestudio.icecore.strman.Strman` | `+ chars(String) : String[]` | Splits a string into characters. |
| `com.arcticicestudio.icecore.strman.Strman` | `+ collapseWhitespace(String) : String` | Replaces consecutive whitespace characters with a single space. |
| `com.arcticicestudio.icecore.strman.Strman` | `+ contains(String, String) : boolean` | Verifies that the needle is contained in the string. The search is case insensitive. |
| `com.arcticicestudio.icecore.strman.Strman` | `+ contains(String, String, boolean) : boolean` | Verifies that the needle is contained in the string. |
| `com.arcticicestudio.icecore.strman.Strman` | `+ containsAll(String, String[]) : boolean` | Verifies that all needles are contained in the string. The search is case insensitive. |
| `com.arcticicestudio.icecore.strman.Strman` | `+ containsAll(String, String[], boolean) : boolean` | Verifies that all needles are contained in the string. This is case insensitive. |
| `com.arcticicestudio.icecore.strman.Strman` | `+ containsAny(String, String[], boolean) : boolean` | Verifies that one or more of needles are contained in the string. |
| `com.arcticicestudio.icecore.strman.Strman` | `+ countSubstr(String, String) : long` | Counts the number of times a substring appears in the string. |
| `com.arcticicestudio.icecore.strman.Strman` | `+ countSubstr(String, String, boolean, boolean) : long` | Counts the number of times a substring appears in the string. |
| `com.arcticicestudio.icecore.strman.Strman` | `+ decodeBin(String) : String` | Decodes a binary unicode (16 digits) string. |
| `com.arcticicestudio.icecore.strman.Strman` | `+ decodeDec(String) : String` | Decodes a decimal unicode (5 digits) string. |
| `com.arcticicestudio.icecore.strman.Strman` | `+ decodeHex(String) : String` | Decodes a hexadecimal unicode (4 digits) string. |
| `com.arcticicestudio.icecore.strman.Strman` | `+ encodeBin(String) : String` | Encodes a string into the binary unicode (16 digits) format. |
| `com.arcticicestudio.icecore.strman.Strman` | `+ encodeDec(String) : String` | Encodes a string into the decimal unicode (5 digits) format. |
| `com.arcticicestudio.icecore.strman.Strman` | `+ encodeHex(String) : String` | Encodes a string into the hexadecimal unicode (4 digits) format. |
| `com.arcticicestudio.icecore.strman.Strman` | `+ endsWith(String, String) : boolean` | Tests if the string ends with the specified string. The search is case sensitive. |
| `com.arcticicestudio.icecore.strman.Strman` | `+ endsWith(String, String, boolean) : boolean` | Tests if the string ends with the specified string. |
| `com.arcticicestudio.icecore.strman.Strman` | `+ endsWith(String, String, int, boolean) : boolean` | Tests if the string ends with the specified string. |
| `com.arcticicestudio.icecore.strman.Strman` | `+ ensureLeft(String, String) : String` | Ensures that the string begins with the specified prefix. The prefix will be prepended if it doesn't exist. The search is case sensitive. |
| `com.arcticicestudio.icecore.strman.Strman` | `+ ensureLeft(String, String, boolean) : String` | Ensures that the string begins with the specified prefix. The prefix will be prepended if it doesn't exist. |
| `com.arcticicestudio.icecore.strman.Strman` | `+ ensureRight(String, String) : String` | Ensures that the string ends with the specified suffix. The suffix will be prepended if it doesn't exist. The search is case sensitive. |
| `com.arcticicestudio.icecore.strman.Strman` | `+ ensureRight(String, String, boolean) : String` | Ensures that the string ends with the specified suffix. The suffix will be prepended if it doesn't exist. |
| `com.arcticicestudio.icecore.strman.Strman` | `+ first(String, int) : String` | Returns the first specified number of characters of the string. |
| `com.arcticicestudio.icecore.strman.Strman` | `+ format(String, String...) : String` | Formats a string using the specified parameters. |
| `com.arcticicestudio.icecore.strman.Strman` | `+ head(String) : String` | Returns the first character of the string. |
| `com.arcticicestudio.icecore.strman.Strman` | `+ indexOf(String, String, int, boolean) : int` | Returns the index of the first occurrence of the specified needle. Returns a negative integer if the needle is not found. |
| `com.arcticicestudio.icecore.strman.Strman` | `+ insert(String, String, int) : String` | Inserts the specified substring into the string at the provided index. |
| `com.arcticicestudio.icecore.strman.Strman` | `+ isLowerCase(String) : boolean` | Verifies if the string consists of lower case characters. |
| `com.arcticicestudio.icecore.strman.Strman` | `+ isString(Object) : boolean` | Checks whether the `Object` is of type `String`. |
| `com.arcticicestudio.icecore.strman.Strman` | `+ isUpperCase(String) : boolean` | Verifies if the string consists of upper case characters. |
| `com.arcticicestudio.icecore.strman.Strman` | `+ last(String, int) : String` | Returns the last specified number of characters of the string. |
| `com.arcticicestudio.icecore.strman.Strman` | `+ lastIndexOf(String, String) : int` | Returns the index of the last occurrence of the specified needle. Returns a negative integer if the needle is not found. The search starts at the end of the specified string and is case sensitive. |
| `com.arcticicestudio.icecore.strman.Strman` | `+ lastIndexOf(String, String, boolean) : int` | Returns the index of the last occurrence of the specified needle. Returns a negative integer if the needle is not found. The search starts at the end of the specified string. |
| `com.arcticicestudio.icecore.strman.Strman` | `+ lastIndexOf(String, String, int, boolean) : int` | Returns the index of the last occurrence of the specified needle searching backwards from the offset. Returns a negative integer if the needle is not found. |
| `com.arcticicestudio.icecore.strman.Strman` | `+ leftPad(String, String, int) : String` | Returns a left-padded string of a given length. |
| `com.arcticicestudio.icecore.strman.Strman` | `+ leftTrim(String) : String` | Removes all spaces on left of the specified string. |
| `com.arcticicestudio.icecore.strman.Strman` | `+ length(String) : int` | Returns the length of the string. |
| `com.arcticicestudio.icecore.strman.Strman` | `+ prepend(String, String...) : String` | Prepends the specified strings to the string. |
| `com.arcticicestudio.icecore.strman.Strman` | `+ prependArray(String, String[]) : String` | Prepends the specified strings to the string. |
| `com.arcticicestudio.icecore.strman.Strman` | `+ removeEmptyStrings(String[]) : String[]` | Removes empty strings from the string array. |
| `com.arcticicestudio.icecore.strman.Strman` | `+ removeLeft(String, String) : String` | Removes the specified prefix from the string. The search is case sensitive. |
| `com.arcticicestudio.icecore.strman.Strman` | `+ removeLeft(String, String, boolean) : String` | Removes the specified prefix from the string. |
| `com.arcticicestudio.icecore.strman.Strman` | `+ removeNonWords(String) : String` | Removes all non-word characters. |
| `com.arcticicestudio.icecore.strman.Strman` | `+ removeRight(String, String) : String` | Removes the specified suffix from the string. The search is case sensitive. |
| `com.arcticicestudio.icecore.strman.Strman` | `+ removeRight(String, String, boolean) : String` | Removes the specified suffix from the string. |
| `com.arcticicestudio.icecore.strman.Strman` | `+ removeSpaces(String) : String` | Removes all spaces from the string. |
| `com.arcticicestudio.icecore.strman.Strman` | `+ repeat(String, int) : String` | Repeats a string with the given multiplier. |
| `com.arcticicestudio.icecore.strman.Strman` | `+ replace(String, String, String, boolean) : String` | Replaces all occurrences of the specified search string with the given string. |
| `com.arcticicestudio.icecore.strman.Strman` | `+ reverse(String) : String` | Reverses the string. |
| `com.arcticicestudio.icecore.strman.Strman` | `+ rightPad(String, String, int) : String` | Returns a right-padded string of a given length. |
| `com.arcticicestudio.icecore.strman.Strman` | `+ rightTrim(String) : String` | Removes all spaces on the right of a string. |
| `com.arcticicestudio.icecore.strman.Strman` | `+ shuffle(String) : String` | Randomly orders the characters of a string. |
| `com.arcticicestudio.icecore.strman.Strman` | `+ surround(String, String, String) : String` | Surrounds a string with the a prefix and suffix. |
| `com.arcticicestudio.icecore.strman.Strman` | `+ tail(String) : String` | Returns the tail of a string. |
| `com.arcticicestudio.icecore.strman.Strman` | `+ toCamelCase(String) : String` | Transforms a string into the "camelCase" spelling. |
| `com.arcticicestudio.icecore.strman.Strman` | `+ toDecamelize(String, String) : String` | Transforms a string into the decamelized form. |
| `com.arcticicestudio.icecore.strman.Strman` | `+ toKebabCase(String) : String` | Transforms a string into the "kebab-case" spelling. |
| `com.arcticicestudio.icecore.strman.Strman` | `+ toSnakeCase(String) : String` | Transforms a string into the "snake_case" spelling. |
| `com.arcticicestudio.icecore.strman.Strman` | `+ toStudlyCase(String) : String` | Transforms a string into the "StudlyCaps" spelling. |
| `com.arcticicestudio.icecore.strman.Strman` | `+ truncate(String, int, String) : String` | Unsecured truncation of a string, cutting the independent string of the required position. |
| `com.arcticicestudio.icecore.strman.Strman` | `+ truncateSafe(String, int, String) : String` | Securely truncates the string, not cutting a word in half. Always returns the last full word. |
| `com.arcticicestudio.icecore.strman.Strman` | `+ words(String) : String[]` | Splits a string to words. |
| `com.arcticicestudio.icecore.strman.Strman` | `+ getVersion() : String` | Returns the ArcVer- and SemVer compatible version. |

## Tests
  - Implemented public API test classes:

| Class | Description |
| ----- | ----------- |
| `com.arcticicestudio.icecore.strman.StrmanTest` | Tests the "IceCore - String Manipulation" public API class `Strman`. |

# 0.0.0 (2016-06-18) - Project Initialization
