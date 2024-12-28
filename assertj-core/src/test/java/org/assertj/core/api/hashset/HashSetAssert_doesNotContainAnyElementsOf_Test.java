package org.assertj.core.api.hashset;

import static java.time.Instant.EPOCH;
import static java.util.Arrays.asList;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatNoException;
import static org.mockito.Mockito.verify;

import java.util.Date;
import java.util.HashSet;

import org.assertj.core.api.HashSetAssert;
import org.assertj.core.api.HashSetAssertBaseTest;

class HashSetAssert_doesNotContainAnyElementsOf_Test extends HashSetAssertBaseTest {
  @Override
  protected HashSetAssert<Object> invoke_api_method() {
    return assertions.doesNotContainAnyElementsOf(asList(someValues));
  }

  @Override
  protected void verify_internal_effects() {
    verify(iterables).assertDoesNotContainAnyElementsOf(getInfo(assertions), getActual(assertions), asList(someValues));
  }

  @HashSetFactory.Test
  void should_pass(HashSetFactory hashSetFactory) {
    // GIVEN
    HashSet<String> hasSet = hashSetFactory.createWith("Yoda", "Luke", "Han");
    // WHEN/THEN
    assertThatNoException().isThrownBy(() -> assertThat(hasSet).doesNotContainAnyElementsOf(asList("Qui-Gon", "Jar Jar")));
  }

  @HashSetFactory.Test
  void should_pass_for_elements_with_changed_hashCode(HashSetFactory hashSetFactory) {
    // GIVEN
    Date first = Date.from(EPOCH.plusSeconds(1));
    Date second = Date.from(EPOCH.plusSeconds(2));
    HashSet<Date> dates = hashSetFactory.createWith(first, second);
    // WHEN
    first.setTime(3_000);
    Date asOriginalFirst = Date.from(EPOCH.plusSeconds(1));
    // THEN
    assertThatNoException().isThrownBy(() -> assertThat(dates).doesNotContainAnyElementsOf(asList(first, asOriginalFirst)));
  }
}
