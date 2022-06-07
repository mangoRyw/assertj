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
package org.assertj.core.internal.iterables;

import static java.util.Collections.emptyList;
import static java.util.stream.Collectors.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.assertj.core.api.Assertions.assertThatNullPointerException;
import static org.assertj.core.api.Assertions.catchThrowable;
import static org.assertj.core.error.ShouldContainExactly.shouldContainExactly;
import static org.assertj.core.error.ShouldContainExactly.shouldContainExactlyWithIndexes;
import static org.assertj.core.internal.ErrorMessages.valuesToLookForIsNull;
import static org.assertj.core.internal.iterables.SinglyIterableFactory.createSinglyIterable;
import static org.assertj.core.test.ObjectArrays.emptyArray;
import static org.assertj.core.test.TestData.someInfo;
import static org.assertj.core.util.Arrays.array;
import static org.assertj.core.util.Arrays.asList;
import static org.assertj.core.util.FailureMessages.actualIsNull;
import static org.assertj.core.util.Lists.newArrayList;
import static org.mockito.Mockito.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.IntStream;

import org.assertj.core.api.AssertionInfo;
import org.assertj.core.configuration.Configuration;
import org.assertj.core.internal.IndexedDiff;
import org.assertj.core.internal.Iterables;
import org.assertj.core.internal.IterablesBaseTest;
import org.junit.jupiter.api.Test;

/**
 * Tests for <code>{@link Iterables#assertContainsExactly(AssertionInfo, Iterable, Object[])}</code>.
 *
 * @author Joel Costigliola
 */
class Iterables_assertContainsExactly_Test extends IterablesBaseTest {

  @Test
  void should_pass_if_actual_contains_exactly_given_values() {
    iterables.assertContainsExactly(someInfo(), actual, array("Luke", "Yoda", "Leia"));
  }

  @Test
  void should_pass_if_nonrestartable_actual_contains_exactly_given_values() {
    iterables.assertContainsExactly(someInfo(), createSinglyIterable(actual), array("Luke", "Yoda", "Leia"));
  }

  @Test
  void should_pass_if_actual_contains_given_values_exactly_with_null_elements() {
    iterables.assertContainsExactly(someInfo(), actual, array("Luke", "Yoda", "Leia"));
    actual.add(null);
    iterables.assertContainsExactly(someInfo(), actual, array("Luke", "Yoda", "Leia", null));
  }

  @Test
  void should_pass_if_actual_and_given_values_are_empty() {
    actual.clear();
    iterables.assertContainsExactly(someInfo(), actual, array());
  }

  @Test
  void should_fail_if_array_of_values_to_look_for_is_empty_and_actual_is_not() {
    assertThatExceptionOfType(AssertionError.class).isThrownBy(() -> iterables.assertContainsExactly(someInfo(), actual,
                                                                                                     emptyArray()));
  }

  @Test
  void should_throw_error_if_array_of_values_to_look_for_is_null() {
    assertThatNullPointerException().isThrownBy(() -> iterables.assertContainsExactly(someInfo(), emptyList(), null))
                                    .withMessage(valuesToLookForIsNull());
  }

  @Test
  void should_fail_if_actual_is_null() {
    assertThatExceptionOfType(AssertionError.class).isThrownBy(() -> iterables.assertContainsExactly(someInfo(), null,
                                                                                                     array("Yoda")))
                                                   .withMessage(actualIsNull());
  }

  @Test
  void should_fail_if_actual_does_not_contain_given_values_exactly() {
    AssertionInfo info = someInfo();
    Object[] expected = { "Luke", "Yoda", "Han" };

    Throwable error = catchThrowable(() -> iterables.assertContainsExactly(info, actual, expected));

    assertThat(error).isInstanceOf(AssertionError.class);
    List<String> notFound = newArrayList("Han");
    List<String> notExpected = newArrayList("Leia");
    verify(failures).failure(info, shouldContainExactly(actual, asList(expected), notFound, notExpected));
  }

  @Test
  void should_fail_if_actual_contains_all_given_values_in_different_order() {
    AssertionInfo info = someInfo();
    Object[] expected = { "Luke", "Leia", "Yoda" };
    ArrayList<IndexedDiff> indexDiffs = newArrayList(new IndexedDiff("Yoda", "Leia", 1),
                                                       new IndexedDiff("Leia", "Yoda", 2));
    Throwable error = catchThrowable(() -> iterables.assertContainsExactly(info, actual, expected));

    assertThat(error).isInstanceOf(AssertionError.class);
    verify(failures).failure(info, shouldContainExactlyWithIndexes(actual, newArrayList(expected), indexDiffs));
  }

  @Test
  void should_fail_if_actual_contains_all_given_values_but_size_differ() {
    AssertionInfo info = someInfo();
    actual = newArrayList("Luke", "Leia", "Luke");
    Object[] expected = { "Luke", "Leia" };

    Throwable error = catchThrowable(() -> iterables.assertContainsExactly(info, actual, expected));

    assertThat(error).isInstanceOf(AssertionError.class);
    verify(failures).failure(info, shouldContainExactly(actual, asList(expected),
                                                        newArrayList(), newArrayList("Luke")));
  }

  // ------------------------------------------------------------------------------------------------------------------
  // tests using a custom comparison strategy
  // ------------------------------------------------------------------------------------------------------------------

  @Test
  void should_pass_if_actual_contains_given_values_exactly_according_to_custom_comparison_strategy() {
    iterablesWithCaseInsensitiveComparisonStrategy.assertContainsExactly(someInfo(), actual,
                                                                         array("LUKE", "YODA", "Leia"));
  }

  @Test
  void should_fail_if_actual_does_not_contain_given_values_exactly_according_to_custom_comparison_strategy() {
    AssertionInfo info = someInfo();
    Object[] expected = { "Luke", "Yoda", "Han" };

    Throwable error = catchThrowable(() -> iterablesWithCaseInsensitiveComparisonStrategy.assertContainsExactly(info, actual,
                                                                                                                expected));

    assertThat(error).isInstanceOf(AssertionError.class);
    verify(failures).failure(info, shouldContainExactly(actual, asList(expected),
                                                        newArrayList("Han"), newArrayList("Leia"),
                                                        comparisonStrategy));
  }

  @Test
  void should_fail_if_actual_contains_all_given_values_in_different_order_according_to_custom_comparison_strategy() {
    AssertionInfo info = someInfo();
    Object[] expected = { "Luke", "Leia", "Yoda" };
    ArrayList<IndexedDiff> indexDiffs = newArrayList(new IndexedDiff("Yoda", "Leia", 1),
                                                     new IndexedDiff("Leia", "Yoda", 2));

    Throwable error = catchThrowable(() -> iterablesWithCaseInsensitiveComparisonStrategy.assertContainsExactly(info, actual,
                                                                                                                expected));

    assertThat(error).isInstanceOf(AssertionError.class);
    verify(failures).failure(info, shouldContainExactlyWithIndexes(actual, newArrayList(expected), indexDiffs,
                                                                   comparisonStrategy));
  }

  @Test
  void should_fail_if_actual_contains_all_given_values_but_size_differ_according_to_custom_comparison_strategy() {
    AssertionInfo info = someInfo();
    actual = newArrayList("Luke", "Leia", "Luke");
    Object[] expected = { "LUKE", "Leia" };

    Throwable error = catchThrowable(() -> iterablesWithCaseInsensitiveComparisonStrategy.assertContainsExactly(info, actual,
                                                                                                                expected));

    assertThat(error).isInstanceOf(AssertionError.class);
    verify(failures).failure(info, shouldContainExactly(actual, asList(expected),
                                                        newArrayList(), newArrayList("Luke"),
                                                        comparisonStrategy));
  }

  @Test
  void should_fail_if_order_does_not_match_and_total_printed_indexes_should_be_equal_to_max_elements_for_printing() {
    AssertionInfo info = someInfo();
    int maxIndex = Configuration.MAX_INDICES_FOR_PRINTING - 1;
    List<Integer> actual = IntStream.rangeClosed(0, Configuration.MAX_INDICES_FOR_PRINTING)
                                        .boxed().collect(toList());
    Collections.shuffle(actual);
    Object[] expected = IntStream.rangeClosed(0, Configuration.MAX_INDICES_FOR_PRINTING).boxed().toArray();

    Throwable error = catchThrowable(() -> iterables.assertContainsExactly(info, actual, expected));

    assertThat(error).isInstanceOf(AssertionError.class)
                     .hasMessageContaining("index " + maxIndex)
                     .hasMessageNotContaining("index " + maxIndex + 1);
  }
}
