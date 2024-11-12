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
 * Copyright 2012-2024 the original author or authors.
 */
package org.assertj.core.api.member;

import org.assertj.core.api.MemberAssert;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Field;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.BDDAssertions.then;
import static org.assertj.core.error.MemberModifierShouldBe.shouldBePackagePrivate;
import static org.assertj.core.error.ShouldNotBeNull.shouldNotBeNull;
import static org.assertj.core.util.AssertionsUtil.expectAssertionError;

/**
 * Tests for <code>{@link MemberAssert#isPackagePrivate()}</code>.
 *
 * @author William Bakker
 */
class MemberAssert_isPackagePrivate_Test {
  Object packagePrivateField = new Object();
  private Object notPackagePrivateField = new Object();

  @Test
  void should_fail_if_actual_is_null() {
    // GIVEN
    Field actual = null;
    // WHEN
    AssertionError assertionError = expectAssertionError(() -> assertThat(actual).isPackagePrivate());
    // THEN
    then(assertionError).hasMessage(shouldNotBeNull().create());
  }

  @Test
  void should_fail_if_actual_is_not_packagePrivate() {
    // GIVEN
    Field actual = getField("notPackagePrivateField");
    // WHEN
    AssertionError assertionError = expectAssertionError(() -> assertThat(actual).isPackagePrivate());
    // THEN
    then(assertionError).hasMessage(shouldBePackagePrivate(actual).create());
  }

  @Test
  void should_pass_if_actual_is_packagePrivate() {
    // GIVEN
    Field actual = getField("packagePrivateField");
    // WHEN/THEN
    assertThat(actual).isPackagePrivate();
  }

  private static Field getField(String name) {
    try {
      return MemberAssert_isPackagePrivate_Test.class.getDeclaredField(name);
    } catch (NoSuchFieldException exception) {
      throw new RuntimeException("failed to get field", exception);
    }
  }

}
