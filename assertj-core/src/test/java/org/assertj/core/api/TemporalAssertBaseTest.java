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
package org.assertj.core.api;

import static org.mockito.Mockito.mock;

import java.time.temporal.Temporal;
import org.assertj.core.internal.Comparables;

public abstract class TemporalAssertBaseTest<ASSERT extends AbstractTemporalAssert<ASSERT, TEMPORAL>, TEMPORAL extends Temporal>
    extends BaseTestTemplate<ASSERT, TEMPORAL> {

  protected Comparables comparables;

  @Override
  protected void inject_internal_objects() {
    super.inject_internal_objects();
    comparables = mock(Comparables.class);
    assertions.comparables = comparables;
  }

  protected abstract Comparables getComparables(ASSERT someAssertions);
}
