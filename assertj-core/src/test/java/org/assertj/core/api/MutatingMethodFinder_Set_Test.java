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
package org.assertj.core.api;

import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Streams;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.NavigableSet;
import java.util.Set;
import java.util.TreeSet;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.BDDAssertions.catchNullPointerException;
import static org.assertj.core.api.BDDAssertions.then;
import static org.mockito.Mockito.mock;

/** Tests finding mutating methods in sets. */
class MutatingMethodFinder_Set_Test extends MutatingMethodFinder_Collection_Test {
  @Test
  void should_not_allow_a_null_list() {
    // WHEN
    NullPointerException exception = catchNullPointerException(() -> finder.visitSet(null));
    // THEN
    then(exception).hasMessageContainingAll("target");
  }

  @MethodSource("org.assertj.core.api.MutatingMethodFinder_Collection_Test#collectionMethods")
  @ParameterizedTest
  void one_mutating_set_method(final String method, final int argumentCount) {
    Set<String> target = new HashSet<>();
    target.add("");
    testOneMutatingMethodInCollection(Set.class, target, method, argumentCount);
  }

  @ParameterizedTest
  @MethodSource("one_mutating_navigable_set_method_source")
  void one_mutating_navigable_set_method(final String method, final int argumentCount) {
    NavigableSet<String> target = new TreeSet<>();
    target.add("");
    testOneMutatingMethodInCollection(NavigableSet.class, target, method, argumentCount);
  }

  /** Mutating navigable set methods. */
  static Stream<Arguments> one_mutating_navigable_set_method_source() {
    Stream<Arguments> navigableSetMethods = Stream.of(Arguments.of("pollFirst", 0), Arguments.of("pollLast", 0));
    return Streams.concat(collectionMethods(), navigableSetMethods);
  }

  @Test
  void successful_iterator_remove_is_detected() {
    NavigableSet<String> target = new TreeSet<>();
    target.add("");
    testIterator(Set.class, target, "iterator", mock(Iterator.class), "remove");
  }

  @Test
  void successful_navigable_set_iterator_remove_is_detected() {
    NavigableSet<String> target = new TreeSet<>();
    target.add("a");
    testIterator(NavigableSet.class, target, "descendingIterator", mock(Iterator.class), "remove");
  }

  @ParameterizedTest(name = "{1}")
  @MethodSource("an_immutable_set_is_identified_source")
  void an_immutable_set_is_identified(final Set<String> set, final Class<?> setClass) {
    assertThat(finder.visitSet(set)).isEmpty();
  }

  static Stream<Arguments> an_immutable_set_is_identified_source() {
    return Stream.of(Collections.emptySet(),
                     Collections.singleton("a"),
                     ImmutableSet.of(),
                     ImmutableSet.of("a"))
                 .map(set -> Arguments.of(set, set.getClass()));
  }

  @ParameterizedTest(name = "{1}")
  @MethodSource("a_mutable_set_is_identified_source")
  void a_mutable_set_is_identified(final Set<String> set, final Class<?> setClass) {
    assertThat(finder.visitSet(set)).isNotEmpty();
  }

  static Stream<Arguments> a_mutable_set_is_identified_source() {
    return Stream.of(new HashSet<>(), new TreeSet<>())
                 .map(set -> Arguments.of(set, set.getClass()));
  }
}
