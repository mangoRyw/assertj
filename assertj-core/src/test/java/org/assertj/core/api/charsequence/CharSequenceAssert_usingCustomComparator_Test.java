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
package org.assertj.core.api.charsequence;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Comparator;

import org.assertj.core.api.CharSequenceAssert;
import org.assertj.core.api.CharSequenceAssertBaseTest;
import org.assertj.core.test.CaseInsensitiveCharSequenceComparator;

/**
 * Tests for <code>{@link CharSequenceAssert#usingComparator(Comparator)}</code>.
 * 
 * @author Joel Costigliola
 * @author Mikhail Mazursky
 */
class CharSequenceAssert_usingCustomComparator_Test extends CharSequenceAssertBaseTest {

  @Override
  protected CharSequenceAssert invoke_api_method() {
    return assertions.usingComparator(CaseInsensitiveCharSequenceComparator.INSTANCE);
  }

  @Override
  protected void verify_internal_effects() {
    assertThat(CaseInsensitiveCharSequenceComparator.INSTANCE).isSameAs(getObjects(assertions).getComparator());
    assertThat(CaseInsensitiveCharSequenceComparator.INSTANCE).isSameAs(getStrings(assertions).getComparator());
  }
}
