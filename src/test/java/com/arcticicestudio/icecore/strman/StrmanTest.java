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

import java.util.Arrays;
import java.util.Optional;

import static com.arcticicestudio.icecore.strman.Strman.*;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.collection.IsArrayContainingInOrder.arrayContaining;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

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

  @Test(expected = IllegalArgumentException.class)
  public void appendArray_ShouldThrowIllegalArgumentExceptionWhenValueIsNull() throws Exception {
    appendArray(null, new String[]{});
  }

  @Test
  public void at_shouldFindCharacterAtIndex() throws Exception {
    assertThat(at("yogurt", 0), equalTo(Optional.of("y")));
    assertThat(at("yogurt", 1), equalTo(Optional.of("o")));
    assertThat(at("yogurt", -1), equalTo(Optional.of("t")));
    assertThat(at("yogurt", -2), equalTo(Optional.of("r")));
    assertThat(at("yogurt", 10), equalTo(Optional.empty()));
    assertThat(at("yogurt", -10), equalTo(Optional.empty()));
  }

  @Test
  public void between_shouldReturnArrayWithStringsBetweenStartAndEnd() throws Exception {
    assertThat(between("[abc][def]", "[", "]"), arrayContaining("abc", "def"));
    assertThat(between("<span>foo</span>", "<span>", "</span>"), arrayContaining("foo"));
    assertThat(between("<span>foo</span><span>bar</span>", "<span>", "</span>"), arrayContaining("foo", "bar"));
  }

  @Test
  public void between_shouldReturnFullStringWhenStartAndEndDoesNotExist() throws Exception {
    assertThat(between("[abc][def]", "{", "}"), arrayContaining("[abc][def]"));
    assertThat(between("", "{", "}"), arrayContaining(""));
  }

  @Test
  public void chars_shouldReturnAllCharactersInString() throws Exception {
    final String yogurt = "yogurt";
    assertThat(chars(yogurt), equalTo(new String[]{"y", "o", "g", "u", "r", "t"}));
  }

  @Test
  public void collapseWhitespace_shouldReplaceConsecutiveWhitespaceWithSingleSpace() throws Exception {
    String[] fixture = {
      "yo    gurt",
      "     yo     gurt    ",
      " yo     gurt   ",
      "    yo     gurt "
    };
    Arrays.stream(fixture).forEach(el -> assertThat(collapseWhitespace(el), equalTo("yo gurt")));
  }

  @Test
  public void collapseWhitespace_shouldReplaceConsecutiveWhitespaceBetweenMultipleStrings() throws Exception {
    String input = " yo      gurt      coco     nut    ";
    assertThat(collapseWhitespace(input), equalTo("yo gurt coco nut"));
  }

  @Test
  public void containsWithCaseSensitiveFalse_shouldReturnTrueWhenStringContainsNeedle() throws Exception {
    String[] fixture = {
      "yo gurt",
      "gurt yo",
      "yogurt",
      "yo"
    };

    Arrays.stream(fixture).forEach(el -> assertTrue(contains(el, "YO")));
  }

  @Test
  public void containsWithCaseSensitiveTrue_shouldReturnTrueWhenStringContainsNeedle() throws Exception {
    String[] fixture = {
      "yo gurt",
      "gurt yo",
      "yogurt",
      "yo"
    };

    Arrays.stream(fixture).forEach(el -> assertFalse(contains(el, "YO", true)));
  }

  @Test
  public void containsAll_shouldReturnTrueOnlyWhenAllNeedlesAreContainedInValue() throws Exception {
    String[] fixture = {
      "yo gurt",
      "gurt yo",
      "yogurt"
    };

    Arrays.stream(fixture).forEach(el -> assertTrue(containsAll(el, new String[]{"yo", "gurt"})));
  }
}
