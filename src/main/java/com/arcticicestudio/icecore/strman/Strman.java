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
import java.util.Objects;
import java.util.Optional;
import java.util.Random;
import java.util.StringJoiner;
import java.util.function.BiFunction;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Stream;

import static java.util.stream.Collectors.joining;

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
   * Decodes a binary unicode (16 digits) string.
   *
   * @param value the value to decode
   * @return the decoded string
   */
  public static String decodeBin(final String value) {
    return decode(value, 16, 2);
  }

  /**
   * Decodes a decimal unicode (5 digits) string,
   *
   * @param value the value to decode
   * @return the decoded string
   */
  public static String decodeDec(final String value) {
    return decode(value, 5, 10);
  }

  /**
   * Decodes a hexadecimal unicode (4 digits) string.
   *
   * @param value the value to decode
   * @return the decoded string
   */
  public static String decodeHex(final String value) {
    return decode(value, 4, 16);
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

  /**
   * Returns the index of the first occurrence of the specified needle.
   *
   * <p>
   *   Returns a negative integer if the value is not found.
   * </p>
   *
   * @param value the value to search
   * @param needle the needle to find
   * @param offset the offset to start searching from
   * @param caseSensitive the case sensitivity
   * @return the position of the first occurrence of the needle, negative integer if not found
   */
  public static int indexOf(final String value, final String needle, int offset, boolean caseSensitive) {
    validate(value, NULL_STRING_PREDICATE, NULL_STRING_MSG_SUPPLIER);
    if (caseSensitive) {
      return value.indexOf(needle, offset);
    }
    return value.toLowerCase().indexOf(needle.toLowerCase(), offset);
  }

  /**
   * Inserts the specified substring into the value at the provided index.
   *
   * @param value the input value
   * @param substr the substring to insert
   * @param index the index to insert the specified substring
   * @return the string with the inserted substring
   */
  public static String insert(final String value, final String substr, final int index) {
    validate(value, NULL_STRING_PREDICATE, NULL_STRING_MSG_SUPPLIER);
    validate(substr, NULL_STRING_PREDICATE, NULL_STRING_MSG_SUPPLIER);
    if (index > value.length()) {
      return value;
    }
    return append(value.substring(0, index), substr, value.substring(index));
  }

  /**
   * Verifies if the value consists of lower case characters.
   *
   * @param value the value to verify
   * @return {@code true} if the value consists of lower case characters, {@code false} otherwise
   */
  public static boolean isLowerCase(final String value) {
    validate(value, NULL_STRING_PREDICATE, NULL_STRING_MSG_SUPPLIER);
    return Objects.equals(value, value.toLowerCase());
  }

  /**
   * Checks whether the Object is a String.
   *
   * @param value the value to check
   * @return {@code true} if the Object is a String, {@code false} otherwise
   */
  public static boolean isString(final Object value) {
    if (Objects.isNull(value)) {
      throw new IllegalArgumentException("value can't be null");
    }
    return value instanceof String;
  }

  /**
   * Verifies if the value consists of upper case characters.
   *
   * @param value the value to verify
   * @return {@code true} if the value consists of upper case characters, {@code false} otherwise
   */
  public static boolean isUpperCase(final String value) {
    validate(value, NULL_STRING_PREDICATE, NULL_STRING_MSG_SUPPLIER);
    return Objects.equals(value, value.toUpperCase());
  }

  /**
   * Returns the last specified number of characters of the value.
   *
   * @param value the value to search
   * @param numberChars the number of characters to return
   * @return the last specified number of characters
   */
  public static String last(final String value, int numberChars) {
    validate(value, NULL_STRING_PREDICATE, NULL_STRING_MSG_SUPPLIER);
    if (numberChars > value.length()) {
      return value;
    }
    return value.substring(value.length() - numberChars);
  }

  /**
   * Returns the index of the last occurrence of the specified needle.
   *
   * <p>
   *   Returns a negative integer if the value is not found.
   *   The search runs backwards and is case sensitive.
   * </p>
   *
   * @param value the value to search
   * @param needle the needle to find
   * @return the position of the last occurrence of the needle, negative integer if not found
   */
  public static int lastIndexOf(final String value, final String needle) {
    validate(value, NULL_STRING_PREDICATE, NULL_STRING_MSG_SUPPLIER);
    return lastIndexOf(value, needle, value.length(), true);
  }

  /**
   * Returns the index of the last occurrence of the specified needle.
   *
   * <p>
   *   Returns a negative integer if the value is not found.
   *   The search runs backwards.
   * </p>
   *
   * @param value the value to search
   * @param needle the needle to find
   * @param caseSensitive the case sensitivity
   * @return the position of the last occurrence of the needle, negative integer if not found
   */
  public static int lastIndexOf(final String value, final String needle, boolean caseSensitive) {
    validate(value, NULL_STRING_PREDICATE, NULL_STRING_MSG_SUPPLIER);
    return lastIndexOf(value, needle, value.length(), caseSensitive);
  }

  /**
   * Returns the index of the last occurrence of the specified needle searching backwards from the offset.
   *
   * <p>
   *   Returns a negative integer if the value is not found.
   * </p>
   *
   * @param value the value to search
   * @param needle the needle to find
   * @param offset the index to start the search from
   * @param caseSensitive the case sensitivity
   * @return the position of the last occurrence of the needle, negative integer if not found
   */
  public static int lastIndexOf(final String value, final String needle, final int offset, final boolean caseSensitive) {
    validate(value, NULL_STRING_PREDICATE, NULL_STRING_MSG_SUPPLIER);
    validate(needle, NULL_STRING_PREDICATE, NULL_STRING_MSG_SUPPLIER);
    if (caseSensitive) {
      return value.lastIndexOf(needle, offset);
    }
    return value.toLowerCase().lastIndexOf(needle.toLowerCase(), offset);
  }

  /**
   * Returns a left-padded string of a given length.
   *
   * @param value the input value
   * @param pad the padding value
   * @param length the length of padding
   * @return the left-padded string
   */
  public static String leftPad(final String value, final String pad, final int length) {
    validate(value, NULL_STRING_PREDICATE, NULL_STRING_MSG_SUPPLIER);
    validate(pad, NULL_STRING_PREDICATE, NULL_STRING_MSG_SUPPLIER);
    if (value.length() > length) {
      return value;
    }
    return append(repeat(pad, length - value.length()), value);
  }

  /**
   * Removes all spaces on left of the specified value.
   *
   * @param value the value to trim
   * @return the string without left border spaces
   */
  public static String leftTrim(final String value) {
    validate(value, NULL_STRING_PREDICATE, NULL_STRING_MSG_SUPPLIER);
    return value.replaceAll("^\\s+", "");
  }

  /**
   * Returns the length of the value.
   *
   * @param value the input value
   * @return the length of the value
   */
  public static int length(final String value) {
    validate(value, NULL_STRING_PREDICATE, NULL_STRING_MSG_SUPPLIER);
    return value.length();
  }

  /**
   * Prepends the specified strings to the value.
   *
   * @param value the input value
   * @param prepends the strings to prepend
   * @return the value with the prepended strings
   */
  public static String prepend(final String value, final String... prepends) {
    return prependArray(value, prepends);
  }

  /**
   * Prepends the specified strings to the value.
   *
   * @param value the input value
   * @param prepends the strings to prepend
   * @return the value with the prepended strings
   */
  public static String prependArray(final String value, final String[] prepends) {
    validate(value, NULL_STRING_PREDICATE, NULL_STRING_MSG_SUPPLIER);
    if (prepends == null || prepends.length == 0) {
      return value;
    }
    StringJoiner joiner = new StringJoiner("");
    for (String prepend : prepends) {
      joiner.add(prepend);
    }
    return joiner.toString() + value;
  }

  /**
   * Removes empty strings from the string array.
   *
   * @param strings the string array to be cleaned
   * @return the string array without empty strings
   */
  public static String[] removeEmptyStrings(String[] strings) {
    if (Objects.isNull(strings)) {
      throw new IllegalArgumentException("Input array should not be null");
    }
    return Arrays.stream(strings).filter(str -> str != null && !str.trim().isEmpty()).toArray(String[]::new);
  }

  /**
   * Removes the specified prefix from the value.
   *
   * <p>
   *   The search is case sensitive.
   * </p>
   *
   * @param value the value to search
   * @param prefix the prefix to remove
   * @return the value without the specified prefix
   */
  public static String removeLeft(final String value, final String prefix) {
    return removeLeft(value, prefix, true);
  }

  /**
   * Removes the specified prefix from the value.
   *
   * @param value the value to search
   * @param prefix the prefix to remove
   * @param caseSensitive the case sensitivity
   * @return the value without the specified prefix
   */
  public static String removeLeft(final String value, final String prefix, final boolean caseSensitive) {
    validate(value, NULL_STRING_PREDICATE, NULL_STRING_MSG_SUPPLIER);
    validate(prefix, NULL_STRING_PREDICATE, NULL_STRING_MSG_SUPPLIER);
    BiFunction<String, String, String> fx = (f, s) -> f.startsWith(s) ? f.replaceFirst(s, "") : f;
    if (caseSensitive) {
      return fx.apply(value, prefix);
    }
    return fx.apply(value.toLowerCase(), prefix.toLowerCase());
  }

  /**
   * Removes all non-word characters.
   *
   * @param value the input value
   * @return the value without non-word characters
   */
  public static String removeNonWords(final String value) {
    validate(value, NULL_STRING_PREDICATE, NULL_STRING_MSG_SUPPLIER);
    return value.replaceAll("[^\\w]+", "");
  }

  /**
   * Removes the specified suffix from the value.
   *
   * <p>
   *   The search is case sensitive.
   * </p>
   *
   * @param value the value to search
   * @param suffix the suffix to remove
   * @return the value without the specified suffix
   */
  public static String removeRight(final String value, final String suffix) {
    return removeRight(value, suffix, true);
  }

  /**
   * Removes the specified suffix from the value.
   *
   * @param value the value to search
   * @param suffix the suffix to remove
   * @param caseSensitive the case sensitivity
   * @return the value without the specified suffix
   */
  public static String removeRight(final String value, final String suffix, final boolean caseSensitive) {
    validate(value, NULL_STRING_PREDICATE, NULL_STRING_MSG_SUPPLIER);
    validate(suffix, NULL_STRING_PREDICATE, NULL_STRING_MSG_SUPPLIER);
    return endsWith(value, suffix, caseSensitive) ? value.substring(0, value.toLowerCase().lastIndexOf(suffix.toLowerCase())) : value;
  }

  /**
   * Removes all spaces from the value.
   *
   * @param value the input value
   * @return the value without spaces
   */
  public static String removeSpaces(final String value) {
    validate(value, NULL_STRING_PREDICATE, NULL_STRING_MSG_SUPPLIER);
    return value.replaceAll("\\s", "");
  }

  /**
   * Repeats a string with the given multiplier.
   *
   * @param value the value to repeat
   * @param multiplier the number of repeats
   * @return the repeated string
   */
  public static String repeat(final String value, final int multiplier) {
    validate(value, NULL_STRING_PREDICATE, NULL_STRING_MSG_SUPPLIER);
    return Stream.generate(() -> value).limit(multiplier).collect(joining());
  }

  /**
   * Replaces all occurrences of the specified search value with the given value.
   *
   * @param value the input value
   * @param search the value to search
   * @param replaceValue the value to replace with the searched value
   * @param caseSensitive the case sensitivity
   * @return the value with the replaced values
   */
  public static String replace(final String value, final String search, final String replaceValue, final boolean caseSensitive) {
    validate(value, NULL_STRING_PREDICATE, NULL_STRING_MSG_SUPPLIER);
    validate(search, NULL_STRING_PREDICATE, NULL_STRING_MSG_SUPPLIER);
    if (caseSensitive) {
      return value.replace(search, replaceValue);
    }
    return value.toLowerCase().replace(search.toLowerCase(), replaceValue);
  }

  /**
   * Reverses the value.
   *
   * @param value the input value
   * @return the reversed value
   */
  public static String reverse(final String value) {
    validate(value, NULL_STRING_PREDICATE, NULL_STRING_MSG_SUPPLIER);
    return new StringBuilder(value).reverse().toString();
  }

  /**
   * Returns a right-padded string of a given length.
   *
   * @param value the input value
   * @param pad the padding value
   * @param length the length of padding
   * @return the right-padded string
   */
  public static String rightPad(final String value, String pad, final int length) {
    validate(value, NULL_STRING_PREDICATE, NULL_STRING_MSG_SUPPLIER);
    if (value.length() > length) {
      return value;
    }
    return append(value, repeat(pad, length - value.length()));
  }

  /**
   * Removes all spaces on the right of the specified value.
   *
   * @param value the value to trim
   * @return the string without right border spaces
   */
  public static String rightTrim(final String value) {
    validate(value, NULL_STRING_PREDICATE, NULL_STRING_MSG_SUPPLIER);
    return value.replaceAll("\\s+$", "");
  }

  /**
   * Randomly orders the characters of the given string.
   *
   * @param value the input string
   * @return the shuffled string
   */
  public static String shuffle(final String value) {
    validate(value, NULL_STRING_PREDICATE, NULL_STRING_MSG_SUPPLIER);
    String[] chars = chars(value);
    Random random = new Random();
    for (int i = 0; i < chars.length; i++) {
      int r = random.nextInt(chars.length);
      String tmp = chars[i];
      chars[i] = chars[r];
      chars[r] = tmp;
    }
    return Arrays.stream(chars).collect(joining());
  }

  /**
   * Surrounds a string with the given prefix and suffix.
   *
   * <p>
   *   If the suffix is empty or {@code null} the prefix will be used.
   * </p>
   *
   * @param value the input value
   * @param prefix the prefix
   * @param suffix the suffix
   * @return the string surrounded with the prefix and suffix
   */
  public static String surround(final String value, final String prefix, final String suffix) {
    validate(value, NULL_STRING_PREDICATE, NULL_STRING_MSG_SUPPLIER);
    String optPrefix = Optional.ofNullable(prefix).orElse("");
    return append(optPrefix, value, Optional.ofNullable(suffix).orElse(optPrefix));
  }

  /**
   * Returns the tail of a string.
   *
   * @param value the input string
   * @return the tail of the given string
   */
  public static String tail(final String value) {
    validate(value, NULL_STRING_PREDICATE, NULL_STRING_MSG_SUPPLIER);
    return last(value, value.length() - 1);
  }

  /**
   * Transforms a string into the "camelCase" spelling.
   *
   * @param value the value to be transformed
   * @return the "camelCase"-transformed string
   */
  public static String toCamelCase(final String value) {
    validate(value, NULL_STRING_PREDICATE, NULL_STRING_MSG_SUPPLIER);
    String str = toStudlyCase(value);
    return str.substring(0, 1).toLowerCase() + str.substring(1);
  }

  /**
   * Transforms a string into the decamelized form.
   *
   * @param value the value to be transformed
   * @param chr the string to replace with
   * @return the decamelized string
   */
  public static String toDecamelize(final String value, final String chr) {
    String camelCasedString = toCamelCase(value);
    String[] words = camelCasedString.split("(?=\\p{Upper})");
    return Arrays.stream(words).map(w -> w.toLowerCase()).collect(joining(Optional.ofNullable(chr).orElse(" ")));
  }

  /**
   * Transforms a string into the "kebab-case" spelling.
   *
   * @param value the value to be transformed
   * @return the "kebab-case"-transformed string
   */
  public static String toKebabCase(final String value) {
    return toDecamelize(value, "-");
  }

  /**
   * Transforms a string into the "snake_case" spelling.
   *
   * @param value the value to be transformed
   * @return the "snake_case"-transformed string
   */
  public static String toSnakeCase(final String value) {
    return toDecamelize(value, "_");
  }

  /**
   * Transforms a string into the "StudlyCaps" spelling.
   *
   * @param value the value to be transformed
   * @return the "StudlyCaps"-transformed string
   */
  public static String toStudlyCase(final String value) {
    validate(value, NULL_STRING_PREDICATE, NULL_STRING_MSG_SUPPLIER);
    String[] words = collapseWhitespace(value.trim()).split("\\s*(_|-|\\s)\\s*");
    return Arrays.stream(words).filter(w -> !w.trim().isEmpty()).map(w -> head(w).toUpperCase() + tail(w)).collect(joining());
  }

  /**
   * Unsecured truncation of the given string , cutting the independent string of the required position.
   *
   * @param value the input string
   * @param length the size of the truncated string
   * @param filler the value that will be added to the end
   * @return the unsecured truncated string
   */
  public static String truncate(final String value, final int length, final String filler) {
    validate(value, NULL_STRING_PREDICATE, NULL_STRING_MSG_SUPPLIER);
    if (length == 0) {
      return "";
    }
    if (length >= value.length()) {
      return value;
    }
    return append(value.substring(0, length - filler.length()), filler);
  }

  /**
   * Securely truncates the string, not cutting a word in half.
   *
   * <p>
   *   Always returns the last full word.
   * </p>
   *
   * @param value the input string
   * @param length the maximal size of the truncated string
   * @param filler the value that will be added to the end
   * @return the securely truncated string
   */
  public static String truncateSafe(final String value, final int length, final String filler) {
    validate(value, NULL_STRING_PREDICATE, NULL_STRING_MSG_SUPPLIER);
    if (length == 0) {
      return "";
    }
    if (length >= value.length()) {
      return value;
    }

    String[] words = words(value);
    StringJoiner result = new StringJoiner(" ");
    int spaceCount = 0;
    for (String word : words) {
      if (result.length() + word.length() + filler.length() + spaceCount > length) {
        break;
      } else {
        result.add(word);
        spaceCount++;
      }
    }
    return append(result.toString(), filler);
  }

  /**
   * Splits a string to words.
   *
   * @param value the string to split
   * @return the array of words
   */
  public static String[] words(final String value) {
    validate(value, NULL_STRING_PREDICATE, NULL_STRING_MSG_SUPPLIER);
    return value.split("\\W+");
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

  private static String decode(final String value, final int digits, final int radix) {
    validate(value, NULL_STRING_PREDICATE, NULL_STRING_MSG_SUPPLIER);
    return Arrays
      .stream(value.split("(?<=\\G.{" + digits + "})"))
      .map(data -> String.valueOf(Character.toChars(Integer.parseInt(data, radix))))
      .collect(joining());
  }

  private static String encode(final String value, final int digits, final int radix) {
    validate(value, NULL_STRING_PREDICATE, NULL_STRING_MSG_SUPPLIER);
    return value.chars().mapToObj(ch -> leftPad(Integer.toString(ch, radix), "0", digits)).collect(joining());
  }

  private static void validate(String value, Predicate<String> predicate, final Supplier<String> supplier) {
    if (predicate.test(value)) {
      throw new IllegalArgumentException(supplier.get());
    }
  }
}
