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
package org.assertj.core.internal;

import org.assertj.core.test.ExpectedException;
import org.assertj.core.util.AbsValueComparator;
import org.assertj.core.util.BigIntegerComparator;
import org.junit.Before;
import org.junit.Rule;

import java.math.BigInteger;

import static org.assertj.core.test.ExpectedException.none;
import static org.assertj.core.util.BigIntegerComparator.BIG_INTEGER_COMPARATOR;
import static org.mockito.Mockito.spy;


public class BigIntegersBaseTest {

  @Rule
  public ExpectedException thrown = none();
  protected Failures failures;
  protected BigIntegers bigIntegers;

  protected ComparatorBasedComparisonStrategy comparatorComparisonStrategy;
  /**
   * {@link BigIntegers} using a comparison strategy based on {@link BigIntegerComparator}.
   */
  protected BigIntegers bigIntegersWithComparatorComparisonStrategy;
  // another BigIntegers with a custom ComparisonStrategy other than numbersWithComparatorComparisonStrategy
  protected BigIntegers bigIntegersWithAbsValueComparisonStrategy;
  protected ComparatorBasedComparisonStrategy absValueComparisonStrategy;

  @Before
  public void setUp() {
    failures = spy(new Failures());
    bigIntegers = new BigIntegers();
    bigIntegers.setFailures(failures);
    comparatorComparisonStrategy = new ComparatorBasedComparisonStrategy(BIG_INTEGER_COMPARATOR);
    bigIntegersWithComparatorComparisonStrategy = new BigIntegers(comparatorComparisonStrategy);
    bigIntegersWithComparatorComparisonStrategy.failures = failures;
    absValueComparisonStrategy = new ComparatorBasedComparisonStrategy(new AbsValueComparator<BigInteger>());
    bigIntegersWithAbsValueComparisonStrategy = new BigIntegers(absValueComparisonStrategy);
    bigIntegersWithAbsValueComparisonStrategy.failures = failures;
  }
}
