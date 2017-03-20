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
 * Copyright 2012-2017 the original author or authors.
 */
package org.assertj.core.internal.classes;

import static org.assertj.core.error.ShouldHaveExactlyFields.shouldHaveExactlyDeclaredFields;
import static org.assertj.core.test.TestData.someInfo;
import static org.assertj.core.util.FailureMessages.actualIsNull;
import static org.assertj.core.util.Sets.newLinkedHashSet;

import org.assertj.core.internal.ClassesBaseTest;
import org.assertj.core.util.Sets;
import org.junit.Before;
import org.junit.Test;

/**
 * Tests for
 * <code
 * >{@link org.assertj.core.internal.Classes#assertHasExactlyDeclaredFields(org.assertj.core.api.AssertionInfo, Class, String...)}</code>
 * .
 *
 * @author Filip Hrisafov
 */
public class Classes_assertHasExactlyDeclaredFields_Test extends ClassesBaseTest {

  @Before
  public void setupActual() {
    actual = AnnotatedClass.class;
  }

  @Test
  public void should_fail_if_actual_is_null() {
    actual = null;
    thrown.expectAssertionError(actualIsNull());
    classes.assertHasExactlyDeclaredFields(someInfo(), actual);
  }

  @Test
  public void should_fail_if_no_fields_are_expected() {
    thrown.expectAssertionError(shouldHaveExactlyDeclaredFields(actual,
                                                                newLinkedHashSet("publicField", "public2Field",
                                                                                 "protectedField", "privateField"),
                                                                Sets.<String>newLinkedHashSet(),
                                                                newLinkedHashSet("publicField", "public2Field",
                                                                                 "protectedField", "privateField")));
    classes.assertHasExactlyDeclaredFields(someInfo(), actual);
  }

  @Test
  public void should_fail_if_not_all_fields_are_expected() {
    thrown.expectAssertionError(shouldHaveExactlyDeclaredFields(actual,
                                                                newLinkedHashSet("publicField", "public2Field",
                                                                                 "protectedField", "privateField"),
                                                                Sets.<String>newLinkedHashSet(),
                                                                newLinkedHashSet("public2Field")));
    classes.assertHasExactlyDeclaredFields(someInfo(), actual, "publicField", "protectedField", "privateField");
  }

  @Test
  public void should_fail_if_fields_are_missing() {
    String[] expected = new String[] { "missingField", "publicField", "public2Field", "protectedField",
      "privateField" };
    thrown.expectAssertionError(shouldHaveExactlyDeclaredFields(actual,
                                                                newLinkedHashSet("publicField", "public2Field",
                                                                                 "protectedField", "privateField"),
                                                                newLinkedHashSet("missingField"),
                                                                Sets.<String>newLinkedHashSet()));
    classes.assertHasExactlyDeclaredFields(someInfo(), actual, expected);
  }

  @Test
  public void should_pass_if_all_fields_are_expected() {
    String[] expected = new String[] { "publicField", "public2Field", "protectedField", "privateField" };
    classes.assertHasExactlyDeclaredFields(someInfo(), actual, expected);
  }

  @Test
  public void should_pass_if_public_all_fields_are_reversed_expected() {
    String[] expected = new String[] { "protectedField", "privateField", "public2Field", "publicField" };
    classes.assertHasExactlyDeclaredFields(someInfo(), actual, expected);
  }

  @Test()
  public void should_fail_if_fields_are_not_expected_and_not_found() {
    String[] expected = new String[] { "publicField", "public2Field", "missing", "privateField" };
    thrown.expectAssertionError(shouldHaveExactlyDeclaredFields(actual,
                                                                newLinkedHashSet("publicField", "public2Field",
                                                                                 "protectedField", "privateField"),
                                                                newLinkedHashSet("missing"),
                                                                newLinkedHashSet("protectedField")));
    classes.assertHasExactlyDeclaredFields(someInfo(), actual, expected);
  }
}
