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
package org.assertj.core.api.charsequence;

import static org.assertj.core.util.Arrays.array;
import static org.mockito.Mockito.verify;

import java.util.Arrays;
import org.assertj.core.api.CharSequenceAssert;
import org.assertj.core.api.CharSequenceAssertBaseTest;

/**
 * Tests for <code>{@link CharSequenceAssert#containsSequence(Iterable<CharSequence>)}</code>.
 *
 * @author Billy Yuan
 * @author André Diermann
 */
class CharSequenceAssert_containsSequence_Test extends CharSequenceAssertBaseTest {

  @Override
  protected CharSequenceAssert invoke_api_method() {
    return assertions.containsSequence(Arrays.<CharSequence> asList("od", "do"));
  }

  @Override
  protected void verify_internal_effects() {
    verify(strings).assertContainsSequence(getInfo(assertions), getActual(assertions), array("od", "do"));
  }
}
