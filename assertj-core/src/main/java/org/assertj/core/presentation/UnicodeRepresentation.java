/*
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with
 * the License. You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on
 * an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the
 * specific language governing permissions and limitations under the License.
 *
 * Copyright 2012-2025 the original author or authors.
 */
package org.assertj.core.presentation;

import java.util.Formatter;

/**
 * Unicode object representation instead of standard java representation.
 *
 * @author Mariusz Smykula
 */
public class UnicodeRepresentation extends StandardRepresentation {

  public static final UnicodeRepresentation UNICODE_REPRESENTATION = new UnicodeRepresentation();

  /**
   * Returns hexadecimal the {@code toString} representation of the given String or Character.
   *
   * @param object the given object.
   * @return the {@code toString} representation of the given object.
   */
  @Override
  public String toStringOf(Object object) {
    if (hasCustomFormatterFor(object)) return customFormat(object);
    if (object instanceof String) return toStringOf((String) object);
    if (object instanceof Character) return toStringOf((Character) object);
    return super.toStringOf(object);
  }

  @Override
  protected String toStringOf(Character string) {
    return escapeUnicode(string.toString());
  }

  @Override
  protected String toStringOf(CharSequence string) {
    return escapeUnicode(string);
  }

  @Override
  protected String toStringOf(String string) {
    return escapeUnicode(string);
  }

  private static String escapeUnicode(CharSequence input) {
    StringBuilder b = new StringBuilder(input.length());
    Formatter formatter = new Formatter(b);
    for (int i = 0; i < input.length(); i++) {
      char c = input.charAt(i);
      if (c < 128) {
        b.append(c);
      } else {
        formatter.format("\\u%04x", (int) c);
      }
    }
    formatter.close();
    return b.toString();
  }
}
