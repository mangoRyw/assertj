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
 * Copyright 2012-2025 the original author or authors.
 */
package org.assertj.tests.core.testkit;

import static org.assertj.core.api.BDDAssertions.then;

import org.junit.jupiter.api.Test;
import org.junitpioneer.jupiter.DefaultLocale;

class CaseInsensitiveStringComparatorTest {

  private final CaseInsensitiveStringComparator underTest = new CaseInsensitiveStringComparator();

  @Test
  @DefaultLocale("tr-TR")
  void should_work_with_Turkish_default_locale() {
    // WHEN
    int result = underTest.compare("i", "I");
    // THEN
    then(result).isZero();
  }

}
