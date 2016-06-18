/*
+++++++++++++++++++++++++++++++++++++++++++
title     String Manipulation             +
project   icecore-strman                  +
file      Strman.java                     +
version                                   +
author    Arctic Ice Studio               +
email     development@arcticicestudio.com +
website   http://arcticicestudio.com      +
copyright Copyright (C) 2016              +
created   2016-06-18 09:54 UTC+0200       +
modified  2016-06-18 09:57 UTC+0200       +
+++++++++++++++++++++++++++++++++++++++++++

[Description]
Manipulates- and modifies strings.
Serves as the entry point to the "IceCore - String Manipulation" public API.

[Copyright]
Copyright (C) 2016 Arctic Ice Studio <development@arcticicestudio.com>

[References]
Java 8 API Documentation
  (https://docs.oracle.com/javase/8/docs/api/)
Arctic Versioning Specification (ArcVer)
  (http://specs.arcticicestudio.com/arcver)
*/
package com.arcticicestudio.icecore.strman;

import java.util.Arrays;
import java.util.Base64;
import java.util.Optional;
import java.util.StringJoiner;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Manipulates- and modifies strings.
 *
 * <p>
 *   Serves as the entry point to the
 *   <a href="https://bitbucket.org/arcticicestudio/icecore-strman">IceCore - String Manipulation</a> public API.
 * </p>
 *
 * @author Arctic Ice Studio &lt;development@arcticicestudio.com&gt;
 * @see <a href="https://bitbucket.org/arcticicestudio/icecore-strman">IceCore - String Manipulation</a>
 * @since 0.1.0
 */
public abstract class Strman {

  private static final Predicate<String> NULL_STRING_PREDICATE = str -> str == null;
  private static final Supplier<String> NULL_STRING_MSG_SUPPLIER = () -> "'value' should be not null.";

  /*
   * Avoid class instantiation.
   */
  private Strman() {}

  /**
   * Appends Strings to value.
   *
   * @param value the initial String
   * @param appends an array of strings to append
   * @return The full String
   */
  public static String append(final String value, final String... appends) {
    return appendArray(value, appends);
  }

  /**
   * Appends an array of {@code String} to value.
   *
   * @param value the initial String
   * @param appends an array of strings to append
   * @return the full String
   */
  public static String appendArray(final String value, final String[] appends) {
    validate(value, NULL_STRING_PREDICATE, NULL_STRING_MSG_SUPPLIER);
    if (appends == null || appends.length == 0) {
      return value;
    }
    StringJoiner joiner = new StringJoiner("");
    for (String append : appends) {
      joiner.add(append);
    }
    return value + joiner.toString();
  }

  /**
   * Gets the character at index.
   *
   * <p>
   *   This method will take care of negative indexes.
   *   The valid value of index is between -(length-1) to (length-1).
   *   For values which don't fall under this range {@code Optional.empty} will be returned
   * </p>
   *
   * @param value the input value
   * @param index the location
   * @return an {@link Optional} String if found else empty
   */
  public static Optional<String> at(final String value, int index) {
    if (value == null || value.isEmpty()) {
      return Optional.empty();
    }
    int length = value.length();
    if (index < 0) {
      index = length + index;
    }
    return (index < length && index >= 0) ? Optional.of(String.valueOf(value.charAt(index))) : Optional.empty();
  }

  /**
   * Decodes data encoded with MIME base64.
   *
   * @param value the data to decode
   * @return the decoded data
   */
  public static String base64Decode(final String value) {
    validate(value, NULL_STRING_PREDICATE, NULL_STRING_MSG_SUPPLIER);
    return new String(Base64.getDecoder().decode(value));
  }

  /**
   * Encodes data with MIME base64.
   *
   * @param value the data to encode
   * @return the encoded String
   */
  public static String base64Encode(final String value) {
    validate(value, NULL_STRING_PREDICATE, NULL_STRING_MSG_SUPPLIER);
    return Base64.getEncoder().encodeToString(value.getBytes());
  }

  /**
   * Returns an array with strings between start and end.
   *
   * @param value the input
   * @param start the start
   * @param end the end
   * @return an array containing different parts between start and end
   */
  public static String[] between(final String value, final String start, final String end) {
    validate(value, NULL_STRING_PREDICATE, NULL_STRING_MSG_SUPPLIER);
    validate(start, NULL_STRING_PREDICATE, () -> "'start' should be not null.");
    validate(end, NULL_STRING_PREDICATE, () -> "'end' should be not null.");

    String[] parts = value.split(end);
    return Arrays.stream(parts).map(subPart -> subPart.substring(subPart.indexOf(start) + start.length())).toArray(String[]::new);
  }

  /**
   * Returns a String array consisting of the characters in the String.
   *
   * @param value the input
   * @return an character array
   */
  public static String[] chars(final String value) {
    validate(value, NULL_STRING_PREDICATE, NULL_STRING_MSG_SUPPLIER);
    return value.split("");
  }

  /**
   * Replaces consecutive whitespace characters with a single space.
   *
   * @param value the input String
   * @return the collapsed String
   */
  public static String collapseWhitespace(final String value) {
    validate(value, NULL_STRING_PREDICATE, NULL_STRING_MSG_SUPPLIER);
    return value.trim().replaceAll("\\s\\s+", " ");
  }

  /**
   * Verifies that the needle is contained in the value.
   *
   * <p>
   *   The search is case insensitive.
   * </p>
   *
   * @param value the value to search
   * @param needle the needle to find
   * @return {@code true} if found, {@code false} otherwise
   */
  public static boolean contains(final String value, final String needle) {
    return contains(value, needle, false);
  }

  /**
   * Verifies that the needle is contained in the the value.
   *
   * @param value the value to search
   * @param needle the needle to find
   * @param caseSensitive the case sensitivity
   * @return {@code true} if found, {@code false} otherwise
   */
  public static boolean contains(final String value, final String needle, final boolean caseSensitive) {
    validate(value, NULL_STRING_PREDICATE, NULL_STRING_MSG_SUPPLIER);
    if (caseSensitive) {
      return value.contains(needle);
    }
    return value.toLowerCase().contains(needle.toLowerCase());
  }

  /**
   * Verifies that all needles are contained in the value.
   *
   * <p>
   *   The search is case insensitive.
   * </p>
   *
   * @param value the input String to search
   * @param needles the needles to find
   * @return {@code true} if all needles are found, {@code false} otherwise
   */
  public static boolean containsAll(final String value, final String[] needles) {
    validate(value, NULL_STRING_PREDICATE, NULL_STRING_MSG_SUPPLIER);
    return Arrays.stream(needles).allMatch(needle -> contains(value, needle, true));
  }

  /**
   * Verifies that all needles are contained in the value.
   *
   * @param value the value to search
   * @param needles the needles to find
   * @param caseSensitive the case sensitivity
   * @return {@code true} if all needles are found, {@code false} otherwise
    */
  public static boolean containsAll(final String value, final String[] needles, final boolean caseSensitive) {
    validate(value, NULL_STRING_PREDICATE, NULL_STRING_MSG_SUPPLIER);
    return Arrays.stream(needles).allMatch(needle -> contains(value, needle, caseSensitive));
  }

  /**
   * Verifies that one or more of needles are contained in the value.
   *
   * <p>
   *   This is case insensitive.
   * </p>
   *
   * @param value the value to search
   * @param needles the needles to find
   * @return {@code true} if any needle is found, {@code false} otherwise
   */
  public static boolean containsAny(final String value, final String[] needles) {
    return containsAny(value, needles, false);
  }

  /**
   * Verifies that one or more of needles are contained in the value.
   *
   * @param value the value to search
   * @param needles the needles to find
   * @param caseSensitive the case sensitivity
   * @return {@code true} if any needle is found, {@code false} otherwise
   */
  public static boolean containsAny(final String value, final String[] needles, final boolean caseSensitive) {
    validate(value, NULL_STRING_PREDICATE, NULL_STRING_MSG_SUPPLIER);
    return Arrays.stream(needles).anyMatch(needle -> contains(value, needle, caseSensitive));
  }

  /**
   * Counts the number of times a substring appears in the value.
   *
   * @param value the value to search
   * @param subStr the substring to find
   * @return the count of times the substring exists
   */
  public static long countSubstr(final String value, final String subStr) {
    return countSubstr(value, subStr, true, false);
  }

  /**
   * Counts the number of times a substring appears in the value.
   *
   * @param value the value to search
   * @param subStr the substring to find
   * @param caseSensitive the case sensitivity
   * @param allowOverlapping the overlapping behavior
   * @return the count of times the substring exists
   */
  public static long countSubstr(final String value, final String subStr, final boolean caseSensitive, boolean allowOverlapping) {
    validate(value, NULL_STRING_PREDICATE, NULL_STRING_MSG_SUPPLIER);
    return countSubstr(caseSensitive ? value : value.toLowerCase(), caseSensitive ? subStr : subStr.toLowerCase(), allowOverlapping, 0L);
  }

  /**
   * Tests if the value ends with the specified string.
   *
   * <p>
   *   The search is case sensitive.
   * </p>
   *
   * @param value the value to search
   * @param search the string to find
   * @return {@code true} if the search string is found, {@code false} otherwise
   */
  public static boolean endsWith(final String value, final String search) {
    validate(value, NULL_STRING_PREDICATE, NULL_STRING_MSG_SUPPLIER);
    return endsWith(value, search, value.length(), true);
  }

  /**
   * Tests if the value ends with the specified string.
   *
   * @param value the value to search
   * @param search the string to find
   * @param caseSensitive the case sensitivity
   * @return {@code true} if the search string is found, {@code false} otherwise
   */
  public static boolean endsWith(final String value, final String search, final boolean caseSensitive) {
    validate(value, NULL_STRING_PREDICATE, NULL_STRING_MSG_SUPPLIER);
    return endsWith(value, search, value.length(), caseSensitive);
  }

  /**
   * Tests if the value ends with the specified string.
   *
   * @param value the value to search
   * @param search the string to find
   * @param position the position to be searched up to
   * @param caseSensitive the case sensitivity
   * @return @return {@code true} if the search string is found, {@code false} otherwise
   */
  public static boolean endsWith(final String value, final String search, final int position, final boolean caseSensitive) {
    validate(value, NULL_STRING_PREDICATE, NULL_STRING_MSG_SUPPLIER);
    int remainingLength = position - search.length();
    if (caseSensitive) {
      return value.indexOf(search, remainingLength) > -1;
    }
    return value.toLowerCase().indexOf(search.toLowerCase(), remainingLength) > -1;
  }

  /**
   * Ensures that the value begins with the specified prefix.
   *
   * <p>
   *   The prefix will be prepended if it doesn't exist.
   *   The search is case sensitive.
   * </p>
   *
   * @param value the value to search
   * @param prefix the prefix to find
   * @return the string with the specified prefix
   */
  public static String ensureLeft(final String value, final String prefix) {
    return ensureLeft(value, prefix, true);
  }

  /**
   * Ensures that the value begins with the specified prefix.
   *
   * <p>
   *   The prefix will be prepended if it doesn't exist.
   * </p>
   *
   * @param value the value to search
   * @param prefix the prefix to find
   * @param caseSensitive the case sensitivity
   * @return the string with the specified prefix
   */
  public static String ensureLeft(final String value, final String prefix, final boolean caseSensitive) {
    validate(value, NULL_STRING_PREDICATE, NULL_STRING_MSG_SUPPLIER);
    if (caseSensitive) {
      return value.startsWith(prefix) ? value : prefix + value;
    }
    String lowCaseValue = value.toLowerCase();
    String lowCasePrefix = prefix.toLowerCase();
    return lowCaseValue.startsWith(lowCasePrefix) ? value : prefix + value;
  }

  /**
   * Ensures that the value ends with the specified suffix.
   *
   * <p>
   *   The suffix will be appended if it doesn't exist.
   *   The search is case sensitive.
   * </p>
   *
   * @param value the value to search
   * @param suffix the suffix to find
   * @return the string with the specified suffix
   */
  public static String ensureRight(final String value, final String suffix) {
    return ensureRight(value, suffix, true);
  }

  /**
   * Ensures that the value ends with the specified suffix.
   *
   * <p>
   *   The suffix will be appended if it doesn't exist.
   * </p>
   *
   * @param value the value to search
   * @param suffix the suffix to find
   * @param caseSensitive the case sensitivity
   * @return the string with the specified suffix
   */
  public static String ensureRight(final String value, final String suffix, boolean caseSensitive) {
    validate(value, NULL_STRING_PREDICATE, NULL_STRING_MSG_SUPPLIER);
    return endsWith(value, suffix, caseSensitive) ? value : append(value, suffix);
  }

  /**
   * Returns the first specified number of characters of the value.
   *
   * @param value the value to search
   * @param numberChars the number of characters to return
   * @return the first specified number of characters
   */
  public static String first(final String value, final int numberChars) {
    validate(value, NULL_STRING_PREDICATE, NULL_STRING_MSG_SUPPLIER);
    return value.substring(0, numberChars);
  }

  /**
   * Formats a string using the specified parameters.
   *
   * @param value the value to be formatted
   * @param params the parameters to be described in the string
   * @return the formatted string
   */
  public static String format(final String value, String... params) {
    validate(value, NULL_STRING_PREDICATE, NULL_STRING_MSG_SUPPLIER);
    Pattern p = Pattern.compile("\\{(\\w+)\\}");
    Matcher m = p.matcher(value);
    String result = value;
    while (m.find()) {
      int paramNumber = Integer.parseInt(m.group(1));
      if (params == null || paramNumber >= params.length) {
        throw new IllegalArgumentException("params does not have value for " + m.group());
      }
      result = result.replace(m.group(), params[paramNumber]);
    }
    return result;
  }

  /**
   * Returns the first character of the value.
   *
   * @param value the input value
   * @return the first character
   */
  public static String head(final String value) {
    return first(value, 1);
  }

  private static void validate(String value, Predicate<String> predicate, final Supplier<String> supplier) {
    if (predicate.test(value)) {
      throw new IllegalArgumentException(supplier.get());
    }
  }

  private static long countSubstr(String value, String subStr, boolean allowOverlapping, long count) {
    int position = value.indexOf(subStr);
    if (position == -1) {
      return count;
    }
    int offset;
    if (!allowOverlapping) {
      offset = position + subStr.length();
    } else {
      offset = position + 1;
    }
    return countSubstr(value.substring(offset), subStr, allowOverlapping, ++count);
  }
}
