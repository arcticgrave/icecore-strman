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
   * Appends an array of {@code String} to value.
   *
   * @param value The initial String
   * @param appends An array of strings to append
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
