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
package org.assertj.core.api.shortarray;

import static org.assertj.core.testkit.ShortArrays.arrayOf;
import static org.mockito.Mockito.verify;

import org.assertj.core.api.AbstractShortArrayAssert;
import org.assertj.core.api.ShortArrayAssert;
import org.junit.jupiter.api.DisplayName;

/**
 * Tests for <code>{@link ShortArrayAssert#endsWith(int...)}</code>.
 * 
 * @author Dan Avila
 */
@DisplayName("ShortArrayAssert endsWith (ints)")
class ShortArrayAssert_endsWith_with_Integer_Argument_Test extends ShortArrayAssertNullTest {

  @Override
  protected ShortArrayAssert invoke_api_method() {
    return assertions.endsWith(6, 8);
  }

  @Override
  protected void verify_internal_effects() {
    verify(arrays).assertEndsWith(getInfo(assertions), getActual(assertions), arrayOf(6, 8));
  }

  @Override
  protected void invoke_api_with_null_value(AbstractShortArrayAssert<?> emptyAssert, int[] nullArray) {
    emptyAssert.endsWith(nullArray);
  }
}
