/*
+++++++++++++++++++++++++++++++++++++++++++++++
title     String Manipulation Public API Test +
project   icecore-strman                      +
file      StrmanTest.java                     +
version                                       +
author    Arctic Ice Studio                   +
email     development@arcticicestudio.com     +
website   http://arcticicestudio.com          +
copyright Copyright (C) 2016                  +
created   2016-06-18 14:37 UTC+0200           +
modified  2016-06-18 14:39 UTC+0200           +
+++++++++++++++++++++++++++++++++++++++++++++++

[Description]
Tests the "IceCore - String Manipulation" public API class "Strman".

[Copyright]
Copyright (C) 2016 Arctic Ice Studio <development@arcticicestudio.com>

[References]
Java 8 API Documentation
  (https://docs.oracle.com/javase/8/docs/api/)
Arctic Versioning Specification (ArcVer)
  (http://specs.arcticicestudio.com/arcver)
*/

package com.arcticicestudio.icecore.strman;

import org.junit.Test;

import static com.arcticicestudio.icecore.strman.Strman.append;
import static com.arcticicestudio.icecore.strman.Strman.appendArray;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;

/**
 * Tests the <a href="https://bitbucket.org/arcticicestudio/icecore-strman">IceCore - String Manipulation</a>
 * public API class {@link Strman}.
 *
 * @author Arctic Ice Studio &lt;development@arcticicestudio.com&gt;
 * @see <a href="https://bitbucket.org/arcticicestudio/icecore-strman">IceCore - String Manipulation</a>
 */
public class StrmanTest {

  @Test
  public void append_shouldAppendStringsToEndOfValue() throws Exception {
    assertThat(append("y", "o", "g", "u", "r", "t"), equalTo("yogurt"));
    assertThat(append("yogurt"), equalTo("yogurt"));
    assertThat(append("", "yogurt"), equalTo("yogurt"));
  }

  @Test(expected = IllegalArgumentException.class)
  public void append_shouldThrowIllegalArgumentExceptionWhenValueIsNull() throws Exception {
    append(null);
  }

  @Test
  public void appendArray_shouldAppendStringArrayToEndOfValue() throws Exception {
    assertThat(appendArray("y", new String[]{"o", "g", "u", "r", "t"}), equalTo("yogurt"));
    assertThat(appendArray("yogurt", new String[]{}), equalTo("yogurt"));
    assertThat(appendArray("", new String[]{"yogurt"}), equalTo("yogurt"));
  }
}
