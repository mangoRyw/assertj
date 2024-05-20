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
package org.assertj.core.internal;

import static org.mockito.Mockito.spy;

import java.util.Comparator;

import org.assertj.core.util.AbsValueComparator;
import org.junit.jupiter.api.BeforeEach;

/**
 * Base class for testing <code>{@link Comparables}</code>, set up an instance with {@link StandardComparisonStrategy} and another
 * with {@link ComparatorBasedComparisonStrategy}.
 * <p>
 * Is in <code>org.assertj.core.internal</code> package to be able to set {@link Comparables#failures} appropriately.
 *
 * @author Joel Costigliola
 */
public class ComparablesBaseTest {

  protected Failures failures;
  protected Comparables comparables;

  protected ComparatorBasedComparisonStrategy customComparisonStrategy;
  protected Comparables comparablesWithCustomComparisonStrategy;

  @BeforeEach
  public void setUp() {
    failures = spy(new Failures());
    comparables = new Comparables();
    comparables.failures = failures;
    customComparisonStrategy = new ComparatorBasedComparisonStrategy(comparatorForCustomComparisonStrategy());
    comparablesWithCustomComparisonStrategy = new Comparables(customComparisonStrategy);
    comparablesWithCustomComparisonStrategy.failures = failures;
  }

  protected Comparator<?> comparatorForCustomComparisonStrategy() {
    return new AbsValueComparator<Integer>();
  }

}