/*
 * Copyright © 2024 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.assertj.core.internal.comparables;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.assertj.core.api.Assertions.catchThrowable;
import static org.assertj.core.error.ShouldBeLessOrEqual.shouldBeLessOrEqual;
import static org.assertj.core.testkit.TestData.someInfo;
import static org.assertj.core.util.FailureMessages.actualIsNull;
import static org.mockito.Mockito.verify;

import java.math.BigDecimal;
import org.assertj.core.api.AssertionInfo;
import org.assertj.core.internal.Comparables;
import org.assertj.core.internal.ComparablesBaseTest;
import org.junit.jupiter.api.Test;

/**
 * Tests for <code>{@link Comparables#assertLessThanOrEqualTo(AssertionInfo, Comparable, Comparable)}</code>.
 * 
 * @author Alex Ruiz
 * @author Joel Costigliola
 */
class Comparables_assertLessThanOrEqualTo_Test extends ComparablesBaseTest {

  @Test
  void should_fail_if_actual_is_null() {
    assertThatExceptionOfType(AssertionError.class).isThrownBy(() -> comparables.assertLessThanOrEqualTo(someInfo(), null, 8))
                                                   .withMessage(actualIsNull());
  }

  @Test
  void should_pass_if_actual_is_less_than_other() {
    comparables.assertLessThanOrEqualTo(someInfo(), 6, 8);
  }

  @Test
  void should_pass_if_actual_is_equal_to_other() {
    comparables.assertLessThanOrEqualTo(someInfo(), 6, 6);
    comparables.assertLessThanOrEqualTo(someInfo(), BigDecimal.TEN, BigDecimal.TEN);
    comparables.assertLessThanOrEqualTo(someInfo(), BigDecimal.TEN, new BigDecimal("10.000"));
    comparables.assertLessThanOrEqualTo(someInfo(), new BigDecimal("10.0"), new BigDecimal("10.000"));
  }

  @Test
  void should_fail_if_actual_is_greater_than_other() {
    AssertionInfo info = someInfo();

    Throwable error = catchThrowable(() -> comparables.assertLessThanOrEqualTo(info, 8, 6));

    assertThat(error).isInstanceOf(AssertionError.class);
    verify(failures).failure(info, shouldBeLessOrEqual(8, 6));
  }

  // ------------------------------------------------------------------------------------------------------------------
  // tests using a custom comparison strategy
  // ------------------------------------------------------------------------------------------------------------------

  @Test
  void should_pass_if_actual_is_less_than_other_according_to_custom_comparison_strategy() {
    comparablesWithCustomComparisonStrategy.assertLessThanOrEqualTo(someInfo(), -6, 8);
  }

  @Test
  void should_pass_if_actual_is_equal_to_other_according_to_custom_comparison_strategy() {
    comparablesWithCustomComparisonStrategy.assertLessThanOrEqualTo(someInfo(), -6, 6);
  }

  @Test
  void should_fail_if_actual_is_greater_than_other_according_to_custom_comparison_strategy() {
    AssertionInfo info = someInfo();

    Throwable error = catchThrowable(() -> comparablesWithCustomComparisonStrategy.assertLessThanOrEqualTo(info, -8, 6));

    assertThat(error).isInstanceOf(AssertionError.class);
    verify(failures).failure(info, shouldBeLessOrEqual(-8, 6, customComparisonStrategy));
  }

}
