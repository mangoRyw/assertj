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
 * Copyright 2012-2022 the original author or authors.
 */
package org.assertj.core.error.stacktraceelement;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.error.stacktraceelement.ShouldHaveClassName.shouldHaveClassName;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EmptySource;
import org.junit.jupiter.params.provider.ValueSource;

/**
 * Tests for {@link ShouldHaveClassName#shouldHaveClassName(StackTraceElement, String)}.
 *
 * @author Ashley Scopes
 */
class ShouldHaveClassName_Test {

  @EmptySource
  @ValueSource(strings = {"org.example.EggsAndSpam", "com.bigcorp.inc.FooBarBaz"})
  @ParameterizedTest
  void should_create_expected_representation_for_expected(String expectedClassName) {
    // GIVEN
    StackTraceElement frame = new StackTraceElement("org.example.Foo", "bar", "Foo.java", 690);
    // WHEN
    String message = shouldHaveClassName(frame, expectedClassName).create();
    // THEN
    assertThat(message).isEqualTo(String.join(
      "\n",
      "",
      "Expecting stack trace element:",
      "  org.example.Foo.bar(Foo.java:690)",
      "to have class name",
      "  \"" + expectedClassName + "\"",
      "but it was actually",
      "  \"org.example.Foo\""
    ));
  }
}
