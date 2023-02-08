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
 * Copyright 2012-2023 the original author or authors.
 */
package org.assertj.core.api.double_;

import static java.lang.String.format;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;
import static org.mockito.Mockito.verify;

import org.assertj.core.api.DoubleAssert;
import org.assertj.core.api.DoubleAssertBaseTest;
import org.junit.jupiter.api.Test;

class DoubleAssert_isNotZero_Test extends DoubleAssertBaseTest {

  @Override
  protected DoubleAssert invoke_api_method() {
    return assertions.isNotZero();
  }

  @Override
  protected void verify_internal_effects() {
    verify(doubles).assertIsNotZero(getInfo(assertions), getActual(assertions));
  }

  @Test
  void should_pass_with_Double_negative_zero() {
    // GIVEN
    final Double negativeZero = -0.0;
    // THEN
    assertThat(negativeZero).isNotZero();
  }

  @Test
  void should_fail_with_primitive_negative_zero() {
    // GIVEN
    final double negativeZero = -0.0;

    // WHEN
    Throwable error = catchThrowable(() -> assertThat(negativeZero).isNotZero());

    // THEN
    assertThat(error).isInstanceOf(AssertionError.class)
                     .hasMessage(format("%nExpecting actual:%n  -0.0%nnot to be equal to:%n  0.0%n"));
  }

  @Test
  void should_fail_with_primitive_positive_zero() {
    // GIVEN
    final double positiveZero = 0.0;

    // WHEN
    Throwable error = catchThrowable(() -> assertThat(positiveZero).isNotZero());

    // THEN
    assertThat(error).isInstanceOf(AssertionError.class)
                     .hasMessage(format("%nExpecting actual:%n  0.0%nnot to be equal to:%n  0.0%n"));
  }

  @Test
  void should_fail_with_Double_positive_zero() {
    // GIVEN
    final Double positiveZero = 0.0;

    // WHEN
    Throwable error = catchThrowable(() -> assertThat(positiveZero).isNotZero());
    assertThat(error).isInstanceOf(AssertionError.class)
                     .hasMessage(format("%nExpecting actual:%n  0.0%nnot to be equal to:%n  0.0%n"));
  }

}
