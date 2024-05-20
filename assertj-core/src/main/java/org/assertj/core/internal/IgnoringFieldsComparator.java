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

import static org.assertj.core.configuration.ConfigurationProvider.CONFIGURATION_PROVIDER;
import static org.assertj.core.internal.TypeComparators.defaultTypeComparators;

import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import org.assertj.core.api.AbstractIterableAssert;
import org.assertj.core.api.AbstractObjectAssert;
import org.assertj.core.api.recursive.comparison.RecursiveComparisonConfiguration;
import org.assertj.core.util.VisibleForTesting;
import org.assertj.core.util.introspection.IntrospectionError;

/**
 * @deprecated
 * This comparator is deprecated because it performs a <b>shallow</b> field by field comparison, i.e. elements are compared
 * field by field but the fields are compared with equals, use {@link AbstractIterableAssert#usingRecursiveFieldByFieldElementComparator(RecursiveComparisonConfiguration)}
 * or {@link AbstractObjectAssert#usingRecursiveComparison()} instead to perform a true recursive comparison.
 */
@Deprecated
public class IgnoringFieldsComparator extends FieldByFieldComparator {

  private final String[] fields;

  public IgnoringFieldsComparator(Map<String, Comparator<?>> comparatorByPropertyOrField,
                                  TypeComparators comparatorByType, String... fields) {
    super(comparatorByPropertyOrField, comparatorByType);
    this.fields = fields;
  }

  public IgnoringFieldsComparator(String... fields) {
    this(new HashMap<>(), defaultTypeComparators(), fields);
  }

  @VisibleForTesting
  public String[] getFields() {
    return fields;
  }

  @Override
  protected boolean areEqual(Object actualElement, Object otherElement) {
    try {
      return Objects.instance().areEqualToIgnoringGivenFields(actualElement, otherElement, comparatorsByPropertyOrField,
                                                              comparatorsByType, fields);
    } catch (IntrospectionError e) {
      return false;
    }
  }

  @Override
  protected String description() {
    return "field/property by field/property comparator on all fields/properties except "
           + CONFIGURATION_PROVIDER.representation().toStringOf(fields);
  }
}
