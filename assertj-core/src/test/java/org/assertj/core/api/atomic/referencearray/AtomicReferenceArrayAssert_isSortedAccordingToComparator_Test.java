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
package org.assertj.core.api.atomic.referencearray;

import static org.assertj.core.testkit.AlwaysEqualComparator.alwaysEqual;
import static org.mockito.Mockito.verify;

import java.util.Comparator;
import org.assertj.core.api.AtomicReferenceArrayAssert;
import org.assertj.core.api.AtomicReferenceArrayAssertBaseTest;

class AtomicReferenceArrayAssert_isSortedAccordingToComparator_Test extends AtomicReferenceArrayAssertBaseTest {

  private Comparator<Object> mockComparator = alwaysEqual();

  @Override
  protected AtomicReferenceArrayAssert<Object> invoke_api_method() {
    return assertions.isSortedAccordingTo(mockComparator);
  }

  @Override
  protected void verify_internal_effects() {
    verify(arrays).assertIsSortedAccordingToComparator(info(), internalArray(), mockComparator);
  }
}
