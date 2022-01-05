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
 * Copyright 2012-2021 the original author or authors.
 */
package org.assertj.core.api;

import static java.util.function.UnaryOperator.identity;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.NavigableSet;
import java.util.Set;

import org.assertj.core.annotations.Beta;
import org.assertj.core.internal.Immutables;
import org.assertj.core.util.VisibleForTesting;

/**
 * Base class for all implementations of assertions for {@link Collection}s.
 * @param <SELF> the "self" type of this assertion class. Please read &quot;<a href="http://bit.ly/1IZIRcY"
 *          target="_blank">Emulating 'self types' using Java Generics to simplify fluent API implementation</a>&quot;
 *          for more details.
 * @param <ACTUAL> the type of the "actual" value.
 * @param <ELEMENT> the type of elements of the "actual" value.
 * @param <ELEMENT_ASSERT> used for navigational assertions to return the right assert type.
 *
 * @since 3.21.0
 */
//@format:off
public abstract class AbstractCollectionAssert<SELF extends AbstractCollectionAssert<SELF, ACTUAL, ELEMENT, ELEMENT_ASSERT>,
                                               ACTUAL extends Collection<? extends ELEMENT>,
                                               ELEMENT,
                                               ELEMENT_ASSERT extends AbstractAssert<ELEMENT_ASSERT, ELEMENT>>
    extends AbstractIterableAssert<SELF, ACTUAL, ELEMENT, ELEMENT_ASSERT> {
//@format:on

  @VisibleForTesting
  Immutables immutables = Immutables.instance();

  protected AbstractCollectionAssert(ACTUAL actual, Class<?> selfType) {
    super(actual, selfType);
  }

  /**
   * Verifies that the actual collection is unmodifiable, i.e., throws an {@link UnsupportedOperationException} with
   * any attempt to modify the collection.
   * <p>
   * Example:
   * <pre><code class='java'> // assertions will pass
   * assertThat(Collections.unmodifiableCollection(new ArrayList&lt;&gt;())).isUnmodifiable();
   * assertThat(Collections.unmodifiableList(new ArrayList&lt;&gt;())).isUnmodifiable();
   * assertThat(Collections.unmodifiableSet(new HashSet&lt;&gt;())).isUnmodifiable();
   *
   * // assertions will fail
   * assertThat(new ArrayList&lt;&gt;()).isUnmodifiable();
   * assertThat(new HashSet&lt;&gt;()).isUnmodifiable();</code></pre>
   *
   * @return {@code this} assertion object.
   * @throws AssertionError if the actual collection is modifiable.
   * @see Collections#unmodifiableCollection(Collection)
   * @see Collections#unmodifiableList(List)
   * @see Collections#unmodifiableSet(Set)
   */
  @Beta
  public SELF isUnmodifiable() {
    isNotNull();
    assertIsUnmodifiable();
    return myself;
  }

  @SuppressWarnings("unchecked")
  private void assertIsUnmodifiable() {
    immutables.expectUnsupportedOperationException(info, () -> actual.add(null), "Collection.add(null)");
    immutables.expectUnsupportedOperationException(info, () -> actual.addAll(emptyCollection()), "Collection.addAll(emptyCollection())");
    immutables.expectUnsupportedOperationException(info, () -> actual.clear(), "Collection.clear()");
    immutables.expectUnsupportedOperationException(info, () -> actual.iterator().remove(), "Collection.iterator().remove()");
    immutables.expectUnsupportedOperationException(info, () -> actual.remove(null), "Collection.remove(null)");
    immutables.expectUnsupportedOperationException(info, () -> actual.removeAll(emptyCollection()), "Collection.removeAll(emptyCollection())");
    immutables.expectUnsupportedOperationException(info, () -> actual.removeIf(element -> true), "Collection.removeIf(element -> true)");
    immutables.expectUnsupportedOperationException(info, () -> actual.retainAll(emptyCollection()), "Collection.retainAll(emptyCollection())");

    if (actual instanceof List) {
      List<ELEMENT> list = (List<ELEMENT>) actual;
      immutables.expectUnsupportedOperationException(info, () -> list.add(0, null), "List.add(0, null)");
      immutables.expectUnsupportedOperationException(info, () -> list.addAll(0, emptyCollection()), "List.addAll(0, emptyCollection())");
      immutables.expectUnsupportedOperationException(info, () -> list.listIterator().add(null), "List.listIterator().add(null)");
      immutables.expectUnsupportedOperationException(info, () -> list.listIterator().remove(), "List.listIterator().remove()");
      immutables.expectUnsupportedOperationException(info, () -> list.listIterator().set(null), "List.listIterator().set(null)");
      immutables.expectUnsupportedOperationException(info, () -> list.remove(0), "List.remove(0)");
      immutables.expectUnsupportedOperationException(info, () -> list.replaceAll(identity()), "List.replaceAll(identity())");
      immutables.expectUnsupportedOperationException(info, () -> list.set(0, null), "List.set(0, null)");
      immutables.expectUnsupportedOperationException(info, () -> list.sort((o1, o2) -> 0), "List.sort((o1, o2) -> 0)");
    }

    if (actual instanceof NavigableSet) {
      NavigableSet<ELEMENT> set = (NavigableSet<ELEMENT>) actual;
      immutables.expectUnsupportedOperationException(info, () -> set.descendingIterator().remove(), "NavigableSet.descendingIterator().remove()");
      immutables.expectUnsupportedOperationException(info, () -> set.pollFirst(), "NavigableSet.pollFirst()");
      immutables.expectUnsupportedOperationException(info, () -> set.pollLast(), "NavigableSet.pollLast()");
    }
  }

  private <E extends ELEMENT> Collection<E> emptyCollection() {
    return Collections.emptyList();
  }

}
