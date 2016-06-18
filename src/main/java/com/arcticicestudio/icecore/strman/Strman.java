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
import java.util.Optional;
import java.util.StringJoiner;
import java.util.function.Predicate;
import java.util.function.Supplier;

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
