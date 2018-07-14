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
package org.assertj.core.api.localdatetime;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatIllegalArgumentException;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.time.LocalDateTime;

import org.junit.Test;
import org.junit.experimental.theories.Theories;
import org.junit.experimental.theories.Theory;
import org.junit.runner.RunWith;

/**
 * Only test String based assertion (tests with {@link LocalDateTime} are already defined in assertj-core)
 * 
 * @author Joel Costigliola
 * @author Marcin Zajączkowski
 */
@RunWith(Theories.class)
public class LocalDateTimeAssert_isNotIn_Test extends LocalDateTimeAssertBaseTest {

  @Theory
  public void test_isNotIn_assertion(LocalDateTime referenceDate) {
    // WHEN
    assertThat(referenceDate).isNotIn(referenceDate.plusDays(1).toString(), referenceDate.plusDays(2).toString());
    // THEN
    assertThatThrownBy(() -> assertThat(referenceDate).isNotIn(referenceDate.toString(), referenceDate.plusDays(1).toString())).isInstanceOf(AssertionError.class);
  }

  @Test
  public void test_isNotIn_assertion_error_message() {
    thrown.expectAssertionError(
        "%nExpecting:%n <2000-01-05T03:00:05>%nnot to be in:%n <[2000-01-05T03:00:05, 2012-01-01T03:03:03]>%n");
    assertThat(LocalDateTime.of(2000, 1, 5, 3, 0, 5)).isNotIn(LocalDateTime.of(2000, 1, 5, 3, 0, 5).toString(),
                                                              LocalDateTime.of(2012, 1, 1, 3, 3, 3).toString());
  }

  @Test
  public void should_fail_if_dateTimes_as_string_array_parameter_is_null() {
    assertThatIllegalArgumentException().isThrownBy(() -> assertThat(LocalDateTime.now()).isNotIn((String[]) null))
                                        .withMessage("The given LocalDateTime array should not be null");
  }

  @Test
  public void should_fail_if_dateTimes_as_string_array_parameter_is_empty() {
    assertThatIllegalArgumentException().isThrownBy(() -> assertThat(LocalDateTime.now()).isNotIn(new String[0]))
                                        .withMessage("The given LocalDateTime array should not be empty");
  }

}
