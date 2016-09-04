/*
+++++++++++++++++++++++++++++++++++++++++++
title     Strman Public API               +
project   icecore-strman                  +
file      Strman.java                     +
version   0.3.0                           +
author    Arctic Ice Studio               +
email     development@arcticicestudio.com +
website   http://arcticicestudio.com      +
copyright Copyright (C) 2016              +
+++++++++++++++++++++++++++++++++++++++++++
*/
package com.arcticicestudio.icecore.strman;

import java.util.Arrays;
import java.util.Base64;
import java.util.Objects;
import java.util.Optional;
import java.util.Random;
import java.util.StringJoiner;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Stream;

import static java.util.stream.Collectors.joining;

/**
 * Manipulates- and modifies strings.
 * <p>
 *   Serves as the entry point to the
 *   <a href="https://bitbucket.org/arcticicestudio/icecore-strman">IceCore - Strman</a> public API.
 * </p>
 *
 * @author Arctic Ice Studio &lt;development@arcticicestudio.com&gt;
 * @see <a href="https://bitbucket.org/arcticicestudio/icecore-strman">IceCore - Strman</a>
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
   * Appends strings to a string.
   *
   * @param value the initial string
   * @param appends strings to be appended
   * @return the initial string with the appended strings
   */
  public static String append(final String value, final String... appends) {
    return appendArray(value, appends);
  }

  /**
   * Appends an array of strings to a string.
   *
   * @param value the initial string
   * @param appends an array of strings to be appended
   * @return the initial string with the appended strings
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
   * Gets the character at the specified index.
   *
   * <p>
   *   Takes care of negative indexes.
   *   The valid value of the index is between -(length-1) to (length-1).
   *   For values which don't fall under this range {@code Optional.empty} will be returned
   * </p>
   *
   * @param value the initial string
   * @param index the location
   * @return an {@link Optional} string if found, empty otherwise
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
   * Decodes a MIME base64 encoded string.
   *
   * @param value the string to decode
   * @return the decoded string
   */
  public static String base64Decode(final String value) {
    validate(value, NULL_STRING_PREDICATE, NULL_STRING_MSG_SUPPLIER);
    return new String(Base64.getDecoder().decode(value));
  }

  /**
   * Encodes a string with MIME base64.
   *
   * @param value the string to encode
   * @return the encoded string
   */
  public static String base64Encode(final String value) {
    validate(value, NULL_STRING_PREDICATE, NULL_STRING_MSG_SUPPLIER);
    return Base64.getEncoder().encodeToString(value.getBytes());
  }

  /**
   * Extracts a string between a given start- and end string.
   *
   * @param value the initial string
   * @param start the start string
   * @param end the end string
   * @return an array containing different parts between the start- and end strings
   */
  public static String[] between(final String value, final String start, final String end) {
    validate(value, NULL_STRING_PREDICATE, NULL_STRING_MSG_SUPPLIER);
    validate(start, NULL_STRING_PREDICATE, () -> "'start' should be not null.");
    validate(end, NULL_STRING_PREDICATE, () -> "'end' should be not null.");

    String[] parts = value.split(end);
    return Arrays.stream(parts).map(subPart -> subPart.substring(subPart.indexOf(start) + start.length())).toArray(String[]::new);
  }

  /**
   * Converts the first character of a string to upper case and the remaining to lower case.
   *
   * @param input the string to capitalize
   * @return the capitalized string
   * @throws IllegalArgumentException when input is {@code null}
   * @since 0.3.0
   */
  public static String capitalize(final String input) throws IllegalArgumentException {
    if (input == null) {
      throw new IllegalArgumentException("input can't be null");
    }
    if (input.length() == 0) {
      return "";
    }
    return head(input).toUpperCase() + tail(input).toLowerCase();
  }

  /**
   * Splits a string into characters.
   *
   * @param value the initial string
   * @return an array containing all string characters
   */
  public static String[] chars(final String value) {
    validate(value, NULL_STRING_PREDICATE, NULL_STRING_MSG_SUPPLIER);
    return value.split("");
  }

  /**
   * Replaces consecutive whitespace characters with a single space.
   *
   * @param value the initial string
   * @return the collapsed string
   */
  public static String collapseWhitespace(final String value) {
    validate(value, NULL_STRING_PREDICATE, NULL_STRING_MSG_SUPPLIER);
    return value.trim().replaceAll("\\s\\s+", " ");
  }

  /**
   * Verifies that the needle is contained in the string.
   *
   * <p>
   *   The search is case insensitive.
   * </p>
   *
   * @param value the string to search
   * @param needle the needle to find
   * @return {@code true} if found, {@code false} otherwise
   */
  public static boolean contains(final String value, final String needle) {
    return contains(value, needle, false);
  }

  /**
   * Verifies that the needle is contained in the the string.
   *
   * @param value the string to search
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
   * Verifies that all needles are contained in the string.
   *
   * <p>
   *   The search is case insensitive.
   * </p>
   *
   * @param value the string to search
   * @param needles the needles to find
   * @return {@code true} if all needles are found, {@code false} otherwise
   */
  public static boolean containsAll(final String value, final String[] needles) {
    validate(value, NULL_STRING_PREDICATE, NULL_STRING_MSG_SUPPLIER);
    return Arrays.stream(needles).allMatch(needle -> contains(value, needle, true));
  }

  /**
   * Verifies that all needles are contained in the string.
   *
   * @param value the string to search
   * @param needles the needles to find
   * @param caseSensitive the case sensitivity
   * @return {@code true} if all needles are found, {@code false} otherwise
    */
  public static boolean containsAll(final String value, final String[] needles, final boolean caseSensitive) {
    validate(value, NULL_STRING_PREDICATE, NULL_STRING_MSG_SUPPLIER);
    return Arrays.stream(needles).allMatch(needle -> contains(value, needle, caseSensitive));
  }

  /**
   * Verifies that one or more of needles are contained in the string.
   *
   * <p>
   *   This is case insensitive.
   * </p>
   *
   * @param value the string to search
   * @param needles the needles to find
   * @return {@code true} if any needle is found, {@code false} otherwise
   */
  public static boolean containsAny(final String value, final String[] needles) {
    return containsAny(value, needles, false);
  }

  /**
   * Verifies that one or more of needles are contained in the string.
   *
   * @param value the string to search
   * @param needles the needles to find
   * @param caseSensitive the case sensitivity
   * @return {@code true} if any needle is found, {@code false} otherwise
   */
  public static boolean containsAny(final String value, final String[] needles, final boolean caseSensitive) {
    validate(value, NULL_STRING_PREDICATE, NULL_STRING_MSG_SUPPLIER);
    return Arrays.stream(needles).anyMatch(needle -> contains(value, needle, caseSensitive));
  }

  /**
   * Counts the number of times a substring appears in the string.
   *
   * @param value the string to search
   * @param subStr the substring to find
   * @return the count of times the substring exists
   */
  public static long countSubstr(final String value, final String subStr) {
    return countSubstr(value, subStr, true, false);
  }

  /**
   * Counts the number of times a substring appears in the string.
   *
   * @param value the string to search
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
   * @param value the string to decode
   * @return the decoded string
   */
  public static String decodeBin(final String value) {
    return decode(value, 16, 2);
  }

  /**
   * Decodes a decimal unicode (5 digits) string,
   *
   * @param value the string to decode
   * @return the decoded string
   */
  public static String decodeDec(final String value) {
    return decode(value, 5, 10);
  }

  /**
   * Decodes a hexadecimal unicode (4 digits) string.
   *
   * @param value the string to decode
   * @return the decoded string
   */
  public static String decodeHex(final String value) {
    return decode(value, 4, 16);
  }

  /**
   * Encodes a string into the binary unicode (16 digits) format.
   *
   * @param value the string to encode
   * @return the encoded string in binary format
   */
  public static String encodeBin(final String value) {
    return encode(value, 16, 2);
  }

  /**
   * Encodes a string into the decimal unicode (5 digits) format.
   *
   * @param value the string to encode
   * @return the encoded string in decimal format
   */
  public static String encodeDec(final String value) {
    return encode(value, 5, 10);
  }

  /**
   * Encodes a string into the hexadecimal unicode (4 digits) format.
   *
   * @param value the string to encode
   * @return the encoded string in hexadecimal format
   */
  public static String encodeHex(final String value) {
    return encode(value, 4, 16);
  }

  /**
   * Tests if the string ends with the specified string.
   *
   * <p>
   *   The search is case sensitive.
   * </p>
   *
   * @param value the string to search
   * @param search the string to find
   * @return {@code true} if the search string is found, {@code false} otherwise
   */
  public static boolean endsWith(final String value, final String search) {
    validate(value, NULL_STRING_PREDICATE, NULL_STRING_MSG_SUPPLIER);
    return endsWith(value, search, value.length(), true);
  }

  /**
   * Tests if the string ends with the specified string.
   *
   * @param value the string to search
   * @param search the string to find
   * @param caseSensitive the case sensitivity
   * @return {@code true} if the search string is found, {@code false} otherwise
   */
  public static boolean endsWith(final String value, final String search, final boolean caseSensitive) {
    validate(value, NULL_STRING_PREDICATE, NULL_STRING_MSG_SUPPLIER);
    return endsWith(value, search, value.length(), caseSensitive);
  }

  /**
   * Tests if the string ends with the specified string.
   *
   * @param value the string to search
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
   * Ensures that the string begins with the specified prefix.
   *
   * <p>
   *   The prefix will be prepended if it doesn't exist.
   *   The search is case sensitive.
   * </p>
   *
   * @param value the string to search
   * @param prefix the prefix to find
   * @return the string with the specified prefix
   */
  public static String ensureLeft(final String value, final String prefix) {
    return ensureLeft(value, prefix, true);
  }

  /**
   * Ensures that the string begins with the specified prefix.
   *
   * <p>
   *   The prefix will be prepended if it doesn't exist.
   * </p>
   *
   * @param value the string to search
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
   * Ensures that the string ends with the specified suffix.
   *
   * <p>
   *   The suffix will be appended if it doesn't exist.
   *   The search is case sensitive.
   * </p>
   *
   * @param value the string to search
   * @param suffix the suffix to find
   * @return the string with the specified suffix
   */
  public static String ensureRight(final String value, final String suffix) {
    return ensureRight(value, suffix, true);
  }

  /**
   * Ensures that the string ends with the specified suffix.
   *
   * <p>
   *   The suffix will be appended if it doesn't exist.
   * </p>
   *
   * @param value the string to search
   * @param suffix the suffix to find
   * @param caseSensitive the case sensitivity
   * @return the string with the specified suffix
   */
  public static String ensureRight(final String value, final String suffix, boolean caseSensitive) {
    validate(value, NULL_STRING_PREDICATE, NULL_STRING_MSG_SUPPLIER);
    return endsWith(value, suffix, caseSensitive) ? value : append(value, suffix);
  }

  /**
   * Returns the first specified number of characters of the string.
   *
   * @param value the string to search
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
   * @param value the string to be formatted
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
   * Returns the first character of the string.
   *
   * @param value the string value
   * @return the first character
   */
  public static String head(final String value) {
    return first(value, 1);
  }

  /**
   * Returns the index of the first occurrence of the specified needle.
   *
   * <p>
   *   Returns a negative integer if the needle is not found.
   * </p>
   *
   * @param value the string to search
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
   * Inserts the specified substring into the string at the provided index.
   *
   * @param value the initial string
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
   * Verifies if the string consists of lower case characters.
   *
   * @param value the string to verify
   * @return {@code true} if the string consists of lower case characters, {@code false} otherwise
   */
  public static boolean isLowerCase(final String value) {
    validate(value, NULL_STRING_PREDICATE, NULL_STRING_MSG_SUPPLIER);
    for (int i = 0; i < value.length(); i++) {
      if (Character.isUpperCase(value.charAt(i))) {
        return false;
      }
    }
    return true;
  }

  /**
   * Checks whether the {@link Object} is of type {@link String}.
   *
   * @param value the object to check
   * @return {@code true} if the {@link Object} is of type {@link String}, {@code false} otherwise
   */
  public static boolean isString(final Object value) {
    if (Objects.isNull(value)) {
      throw new IllegalArgumentException("value can't be null");
    }
    return value instanceof String;
  }

  /**
   * Verifies if the string consists of upper case characters.
   *
   * @param value the string to verify
   * @return {@code true} if the string consists of upper case characters, {@code false} otherwise
   */
  public static boolean isUpperCase(final String value) {
    validate(value, NULL_STRING_PREDICATE, NULL_STRING_MSG_SUPPLIER);
    for (int i = 0; i < value.length(); i++) {
      if (Character.isLowerCase(value.charAt(i))) {
        return false;
      }
    }
    return true;
  }

  /**
   * Concatenates all the elements of the string array into a single string.
   * <p>
   *   The separator string is placed between elements in the resulting string.
   * </p>
   *
   * @param strings The input array to concatenate
   * @param separator The separator to use
   * @return the concatenated string
   * @throws IllegalArgumentException if separator is null
   * @since 0.3.0
   */
  public static String join(final String[] strings, final String separator) throws IllegalArgumentException {
    if (strings == null) {
      throw new IllegalArgumentException("Input array can't be null");
    }
    if (separator == null) {
      throw new IllegalArgumentException("separator can't be null");
    }
    StringJoiner joiner = new StringJoiner(separator);
    for (String el : strings) {
      joiner.add(el);
    }
    return joiner.toString();
  }

  /**
   * Returns the last specified number of characters of the string.
   *
   * @param value the string to search
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
   *   Returns a negative integer if the needle is not found.
   *   The search starts at the end of the specified string and is case sensitive.
   * </p>
   *
   * @param value the string to search
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
   *   Returns a negative integer if the needle is not found.
   *   The search starts at the end of the specified string.
   * </p>
   *
   * @param value the string to search
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
   *   Returns a negative integer if the needle is not found.
   * </p>
   *
   * @param value the string to search
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
   * @param value the initial string
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
   * Removes all spaces on left of the specified string.
   *
   * @param value the string to trim
   * @return the string without left border spaces
   */
  public static String leftTrim(final String value) {
    validate(value, NULL_STRING_PREDICATE, NULL_STRING_MSG_SUPPLIER);
    return value.replaceAll("^\\s+", "");
  }

  /**
   * Returns the length of the string.
   *
   * @param value the initial string
   * @return the length of the string
   */
  public static int length(final String value) {
    validate(value, NULL_STRING_PREDICATE, NULL_STRING_MSG_SUPPLIER);
    return value.length();
  }

  /**
   * Converts the first character of a string to lower case.
   *
   * @param input the string to convert
   * @return the converted string
   * @throws IllegalArgumentException if the input is null
   * @since 0.3.0
   */
  public static String lowerFirst(final String input) throws IllegalArgumentException {
    if (input == null) {
      throw new IllegalArgumentException("input can't be null");
    }
    if (input.length() == 0) {
      return "";
    }
    return head(input).toLowerCase() + tail(input);
  }

  /**
   * Prepends the specified strings to the string.
   *
   * @param value the initial string
   * @param prepends the strings to prepend
   * @return the string with the prepended strings
   */
  public static String prepend(final String value, final String... prepends) {
    return prependArray(value, prepends);
  }

  /**
   * Prepends the specified strings to the string.
   *
   * @param value the initial string
   * @param prepends the strings to prepend
   * @return the string with the prepended strings
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
   * Removes the specified prefix from the string.
   *
   * <p>
   *   The search is case sensitive.
   * </p>
   *
   * @param value the string to search
   * @param prefix the prefix to remove
   * @return the string without the specified prefix
   */
  public static String removeLeft(final String value, final String prefix) {
    return removeLeft(value, prefix, true);
  }

  /**
   * Removes the specified prefix from the string.
   *
   * @param value the string to search
   * @param prefix the prefix to remove
   * @param caseSensitive the case sensitivity
   * @return the string without the specified prefix
   */
  public static String removeLeft(final String value, final String prefix, final boolean caseSensitive) {
    validate(value, NULL_STRING_PREDICATE, NULL_STRING_MSG_SUPPLIER);
    validate(prefix, NULL_STRING_PREDICATE, NULL_STRING_MSG_SUPPLIER);
    if (caseSensitive) {
      return value.startsWith(prefix) ? value.substring(prefix.length()) : value;
    }
    return value.toLowerCase().startsWith(prefix.toLowerCase()) ? value.substring(prefix.length()) : value;
  }

  /**
   * Removes all non-word characters.
   *
   * @param value the initial string
   * @return the string without non-word characters
   */
  public static String removeNonWords(final String value) {
    validate(value, NULL_STRING_PREDICATE, NULL_STRING_MSG_SUPPLIER);
    return value.replaceAll("[^\\w]+", "");
  }

  /**
   * Removes the specified suffix from the string.
   *
   * <p>
   *   The search is case sensitive.
   * </p>
   *
   * @param value the string to search
   * @param suffix the suffix to remove
   * @return the string without the specified suffix
   */
  public static String removeRight(final String value, final String suffix) {
    return removeRight(value, suffix, true);
  }

  /**
   * Removes the specified suffix from the string.
   *
   * @param value the string to search
   * @param suffix the suffix to remove
   * @param caseSensitive the case sensitivity
   * @return the string without the specified suffix
   */
  public static String removeRight(final String value, final String suffix, final boolean caseSensitive) {
    validate(value, NULL_STRING_PREDICATE, NULL_STRING_MSG_SUPPLIER);
    validate(suffix, NULL_STRING_PREDICATE, NULL_STRING_MSG_SUPPLIER);
    return endsWith(value, suffix, caseSensitive) ? value.substring(0, value.toLowerCase().lastIndexOf(suffix.toLowerCase())) : value;
  }

  /**
   * Removes all spaces from the string.
   *
   * @param value the initial string
   * @return the string without spaces
   */
  public static String removeSpaces(final String value) {
    validate(value, NULL_STRING_PREDICATE, NULL_STRING_MSG_SUPPLIER);
    return value.replaceAll("\\s", "");
  }

  /**
   * Repeats a string with the given multiplier.
   *
   * @param value the string to repeat
   * @param multiplier the number of repeats
   * @return the repeated string
   */
  public static String repeat(final String value, final int multiplier) {
    validate(value, NULL_STRING_PREDICATE, NULL_STRING_MSG_SUPPLIER);
    return Stream.generate(() -> value).limit(multiplier).collect(joining());
  }

  /**
   * Replaces all occurrences of the specified search string with the given string.
   *
   * @param value the initial string
   * @param search the string to search
   * @param replaceValue the string to replace with the searched string
   * @param caseSensitive the case sensitivity
   * @return the string with the replaced strings
   */
  public static String replace(final String value, final String search, final String replaceValue, final boolean caseSensitive) {
    validate(value, NULL_STRING_PREDICATE, NULL_STRING_MSG_SUPPLIER);
    validate(search, NULL_STRING_PREDICATE, NULL_STRING_MSG_SUPPLIER);
    if (caseSensitive) {
      return value.replace(search, replaceValue);
    }
    return Pattern.compile(search, Pattern.CASE_INSENSITIVE).matcher(value).replaceAll(Matcher.quoteReplacement(replaceValue));
  }

  /**
   * Reverses the string.
   *
   * @param value the initial string
   * @return the reversed string
   */
  public static String reverse(final String value) {
    validate(value, NULL_STRING_PREDICATE, NULL_STRING_MSG_SUPPLIER);
    return new StringBuilder(value).reverse().toString();
  }

  /**
   * Returns a right-padded string of a given length.
   *
   * @param value the initial string
   * @param pad the padding string
   * @param length the length of the padding
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
   * Removes all spaces on the right of a string.
   *
   * @param value the string to trim
   * @return the string without right border spaces
   */
  public static String rightTrim(final String value) {
    validate(value, NULL_STRING_PREDICATE, NULL_STRING_MSG_SUPPLIER);
    return value.replaceAll("\\s+$", "");
  }

  /**
   * Randomly orders the characters of a string.
   *
   * @param value the initial string
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
   * Surrounds a string with the a prefix and suffix.
   *
   * <p>
   *   If the suffix is empty or {@code null} the prefix will be used.
   * </p>
   *
   * @param value the initial string
   * @param prefix the prefix string
   * @param suffix the suffix string
   * @return the string surrounded with the prefix- and suffix strings
   */
  public static String surround(final String value, final String prefix, final String suffix) {
    validate(value, NULL_STRING_PREDICATE, NULL_STRING_MSG_SUPPLIER);
    String optPrefix = Optional.ofNullable(prefix).orElse("");
    return append(optPrefix, value, Optional.ofNullable(suffix).orElse(optPrefix));
  }

  /**
   * Returns the tail of a string.
   *
   * @param value the initial string
   * @return the tail of the given string
   */
  public static String tail(final String value) {
    validate(value, NULL_STRING_PREDICATE, NULL_STRING_MSG_SUPPLIER);
    return last(value, value.length() - 1);
  }

  /**
   * Transforms a string into the "camelCase" spelling.
   *
   * @param value the string to be transformed
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
   * @param value the string to be transformed
   * @param chr the string to replace with
   * @return the decamelized string
   */
  public static String toDecamelize(final String value, final String chr) {
    String camelCasedString = toCamelCase(value);
    String[] words = camelCasedString.split("(?=\\p{Upper})");
    return Arrays.stream(words).map(String::toLowerCase).collect(joining(Optional.ofNullable(chr).orElse(" ")));
  }

  /**
   * Transforms a string into the "kebab-case" spelling.
   *
   * @param value the string to be transformed
   * @return the "kebab-case"-transformed string
   */
  public static String toKebabCase(final String value) {
    return toDecamelize(value, "-");
  }

  /**
   * Transforms a string into the "snake_case" spelling.
   *
   * @param value the string to be transformed
   * @return the "snake_case"-transformed string
   */
  public static String toSnakeCase(final String value) {
    return toDecamelize(value, "_");
  }

  /**
   * Transforms a string into the "StudlyCaps" spelling.
   *
   * @param value the string to be transformed
   * @return the "StudlyCaps"-transformed string
   */
  public static String toStudlyCase(final String value) {
    validate(value, NULL_STRING_PREDICATE, NULL_STRING_MSG_SUPPLIER);
    String[] words = collapseWhitespace(value.trim()).split("\\s*(_|-|\\s)\\s*");
    return Arrays.stream(words).filter(w -> !w.trim().isEmpty()).map(w -> head(w).toUpperCase() + tail(w)).collect(joining());
  }

  /**
   * Removes trailing whitespaces from a string.
   *
   * @param input the string to trim
   * @return the trimmed string
   * @since 0.4.0
   */
  public static Optional<String> trimEnd(final String input) {
    return Optional.ofNullable(input)
      .filter(v -> !v.isEmpty())
      .map(Strman::rightTrim);
  }

  /**
   * Removes trailing characters from a string.
   *
   * @param input the string to trim
   * @param chars the characters to trim
   * @return the trimmed string
   * @since 0.4.0
   */
  public static Optional<String> trimEnd(final String input, String... chars) {
    return Optional.ofNullable(input)
      .filter(v -> !v.isEmpty())
      .map(v -> {
        String pattern = String.format("[%s]+$", join(chars, "\\"));
        return v.replaceAll(pattern, "");
      });
  }

  /**
   * Unsecured truncation of a string, cutting the independent string of the required position.
   *
   * @param value the initial string
   * @param length the size of the truncated string
   * @param filler the string that will be added to the end
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
   * @param value the initial string
   * @param length the maximal size of the truncated string
   * @param filler the string that will be added to the end
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
   * @return an string array containing all words
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

  /**
   * Returns the ArcVer- and SemVer compatible version.
   *
   * @return the ArcVer and SemVer compatible version
   * @see <a href="https://github.com/arcticicestudio/arcver">ArcVer</a>
   * @see <a href="http://semver.org">SemVer</a>
   */
  public static String getVersion() {
    return "0.3.0";
  }
}
