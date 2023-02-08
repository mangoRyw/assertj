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
package org.assertj.core.internal.long2darrays;

import static org.mockito.Mockito.verify;

import org.assertj.core.api.AssertionInfo;
import org.assertj.core.internal.Long2DArrays;
import org.assertj.core.internal.Long2DArraysBaseTest;
import org.junit.jupiter.api.Test;

/**
 * Tests for <code>{@link Long2DArrays#assertHasDimensions(AssertionInfo, long[][], int, int)}</code>.
 *
 * @author Maciej Wajcht
 */
class Long2DArrays_assertHasDimensions_Test extends Long2DArraysBaseTest {

  @Test
  void should_delegate_to_Arrays2D() {
    // WHEN
    long2dArrays.assertHasDimensions(info, actual, 2, 3);
    // THEN
    verify(arrays2d).assertHasDimensions(info, failures, actual, 2, 3);
  }
}
