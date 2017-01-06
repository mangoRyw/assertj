/**
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with
 * the License. You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on
 * an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the
 * specific language governing permissions and limitations under the License.
 *
 * Copyright 2012-2016 the original author or authors.
 */
package org.assertj.core.internal.bigintegers;

import org.assertj.core.api.AssertionInfo;
import org.assertj.core.internal.BigIntegers;
import org.assertj.core.internal.BigIntegersBaseTest;
import org.junit.Test;

import java.math.BigInteger;

import static java.math.BigInteger.ONE;
import static java.math.BigInteger.TEN;
import static org.assertj.core.error.ShouldBeLessOrEqual.shouldBeLessOrEqual;
import static org.assertj.core.test.TestData.someInfo;
import static org.assertj.core.test.TestFailures.failBecauseExpectedAssertionErrorWasNotThrown;
import static org.assertj.core.util.FailureMessages.actualIsNull;
import static org.mockito.Mockito.verify;


/**
 * Tests for <code>{@link BigIntegers#assertLessThanOrEqualTo(AssertionInfo, BigInteger, BigInteger)}</code>.
 * 
 * @author Joel Costigliola
 */
public class BigIntegers_assertLessThanOrEqualTo_Test extends BigIntegersBaseTest {

  @Test
  public void should_fail_if_actual_is_null() {
    thrown.expectAssertionError(actualIsNull());
    bigIntegers.assertLessThanOrEqualTo(someInfo(), null, ONE);
  }

  @Test
  public void should_pass_if_actual_is_less_than_other() {
    bigIntegers.assertLessThanOrEqualTo(someInfo(), ONE, TEN);
  }

  @Test
  public void should_pass_if_actual_is_equal_to_other() {
    bigIntegers.assertLessThanOrEqualTo(someInfo(), ONE, ONE);
  }

  @Test
  public void should_pass_if_actual_is_equal_to_other_by_comparison() {
    bigIntegers.assertLessThanOrEqualTo(someInfo(), ONE, new BigInteger("1"));
  }  
 
  @Test
  public void should_fail_if_actual_is_less_than_other() {
    AssertionInfo info = someInfo();
    try {
      bigIntegers.assertLessThanOrEqualTo(info, TEN, ONE);
    } catch (AssertionError e) {
      verify(failures).failure(info, shouldBeLessOrEqual(TEN, ONE));
      return;
    }
    failBecauseExpectedAssertionErrorWasNotThrown();
  }

  // ------------------------------------------------------------------------------------------------------------------
  // tests using a custom comparison strategy
  // ------------------------------------------------------------------------------------------------------------------

  @Test
  public void should_pass_if_actual_is_less_than_other_according_to_custom_comparison_strategy() {
    bigIntegersWithAbsValueComparisonStrategy.assertLessThanOrEqualTo(someInfo(), ONE, TEN.negate());
  }

  @Test
  public void should_pass_if_actual_is_equal_to_other_according_to_custom_comparison_strategy() {
    bigIntegersWithAbsValueComparisonStrategy.assertLessThanOrEqualTo(someInfo(), ONE.negate(), ONE);
  }

  @Test
  public void should_fail_if_actual_is_less_than_other_according_to_custom_comparison_strategy() {
    AssertionInfo info = someInfo();
    try {
      bigIntegersWithAbsValueComparisonStrategy.assertLessThanOrEqualTo(info, TEN.negate(), ONE);
    } catch (AssertionError e) {
      verify(failures).failure(info, shouldBeLessOrEqual(TEN.negate(), ONE, absValueComparisonStrategy));
      return;
    }
    failBecauseExpectedAssertionErrorWasNotThrown();
  }

}
