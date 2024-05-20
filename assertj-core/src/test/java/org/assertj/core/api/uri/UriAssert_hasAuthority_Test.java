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
package org.assertj.core.api.uri;

import static org.mockito.Mockito.verify;

import org.assertj.core.api.UriAssert;
import org.assertj.core.api.UriAssertBaseTest;

/**
 * Test for <code>{@link org.assertj.core.api.UriAssert#hasAuthority(String)}</code>.
 */
class UriAssert_hasAuthority_Test extends UriAssertBaseTest {
  private String expected = "authority";

  @Override
  protected UriAssert invoke_api_method() {
    return assertions.hasAuthority(expected);
  }

  @Override
  protected void verify_internal_effects() {
    verify(uris).assertHasAuthority(getInfo(assertions), getActual(assertions), expected);
  }
}
