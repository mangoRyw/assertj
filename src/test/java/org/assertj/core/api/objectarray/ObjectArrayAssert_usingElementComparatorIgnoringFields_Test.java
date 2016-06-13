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
package org.assertj.core.api.objectarray;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Comparator;

import org.assertj.core.api.ObjectArrayAssert;
import org.assertj.core.api.ObjectArrayAssertBaseTest;
import org.assertj.core.internal.ComparatorBasedComparisonStrategy;
import org.assertj.core.internal.IgnoringFieldsComparator;
import org.assertj.core.internal.ObjectArrays;
import org.assertj.core.test.AlwaysEqualStringComparator;
import org.assertj.core.test.Jedi;
import org.junit.Before;
import org.junit.Test;

public class ObjectArrayAssert_usingElementComparatorIgnoringFields_Test extends ObjectArrayAssertBaseTest {

  private ObjectArrays arraysBefore;

  @Before
  public void before() {
    arraysBefore = getArrays(assertions);
  }

  @Override
  protected ObjectArrayAssert<Object> invoke_api_method() {
	return assertions.usingElementComparatorIgnoringFields("field");
  }

  @Override
  protected void verify_internal_effects() {
    ObjectArrays iterables = getArrays(assertions);
    assertThat(iterables).isNotSameAs(arraysBefore);
	assertThat(iterables.getComparisonStrategy()).isInstanceOf(ComparatorBasedComparisonStrategy.class);
	ComparatorBasedComparisonStrategy strategy = (ComparatorBasedComparisonStrategy) iterables.getComparisonStrategy();
	assertThat(strategy.getComparator()).isInstanceOf(IgnoringFieldsComparator.class);
	assertThat(((IgnoringFieldsComparator) strategy.getComparator()).getFields()).containsOnly("field");
  }

  @Test
  public void should_be_able_to_use_a_comparator_for_specified_fields_of_elments_when_using_element_comparator_ignoring_fields() {
    Jedi actual = new Jedi("Yoda", "green");
    Jedi other = new Jedi("Luke", "green");

    assertThat(new Jedi[] { (actual) }).usingComparatorForElementFieldsWithName(new AlwaysEqualStringComparator(), "name")
                                       .usingElementComparatorIgnoringFields("lightSaberColor").contains(other);
  }

  @Test
  public void comparators_for_element_field_names_should_have_precedence_over_comparators_for_element_field_types_using_element_comparator_ignoring_fields() {
    Comparator<String> comperator = new Comparator<String>() {
      public int compare(String o1, String o2) {
        return o1.compareTo(o2);
      }
    };
    Jedi actual = new Jedi("Yoda", "green");
    Jedi other = new Jedi("Luke", "green");

    assertThat(new Jedi[] { actual }).usingComparatorForElementFieldsWithName(new AlwaysEqualStringComparator(), "name")
                                     .usingComparatorForElementFieldsWithType(comperator, String.class)
                                     .usingElementComparatorIgnoringFields("lightSaberColor").contains(other);
  }

  @Test
  public void should_be_able_to_use_a_comparator_for_element_fields_with_specified_type_using_element_comparator_ignoring_fields() {
    Jedi actual = new Jedi("Yoda", "green");
    Jedi other = new Jedi("Luke", "blue");

    assertThat(new Jedi[] { actual }).usingComparatorForElementFieldsWithType(new AlwaysEqualStringComparator(), String.class)
                                     .usingElementComparatorIgnoringFields("name").contains(other);
  }
}
