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
 * Copyright 2012-2018 the original author or authors.
 */
package org.assertj.core.internal.classes;

import org.assertj.core.api.AbstractClassAssert;
import org.assertj.core.internal.ClassesBaseTest;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.assertj.core.error.ShouldBeAbstract.shouldBeAbstract;
import static org.assertj.core.test.TestData.someInfo;
import static org.assertj.core.util.FailureMessages.actualIsNull;

class Classes_assertIsAbstract extends ClassesBaseTest {

  @Test
  void should_fail_if_actual_is_null() {
    actual = null;
    assertThatExceptionOfType(AssertionError.class).isThrownBy(() -> classes.assertIsAbstract(someInfo(), actual))
      .withMessage(actualIsNull());
  }

  @Test
  void should_pass_if_actual_is_abstract() {
    actual = AbstractClassAssert.class;
    classes.assertIsAbstract(someInfo(), actual);
  }

  @Test()
  void should_fail_if_actual_is_not_abstract() {
    actual = Classes_assertIsAbstract.class;
    assertThatExceptionOfType(AssertionError.class).isThrownBy(() -> classes.assertIsAbstract(someInfo(), actual))
      .withMessage(shouldBeAbstract(actual).create());
  }
}
