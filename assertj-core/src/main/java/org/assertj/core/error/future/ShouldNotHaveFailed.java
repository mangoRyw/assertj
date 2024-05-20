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
package org.assertj.core.error.future;

import java.util.concurrent.CompletableFuture;
import org.assertj.core.error.BasicErrorMessageFactory;
import org.assertj.core.error.ErrorMessageFactory;

public class ShouldNotHaveFailed extends BasicErrorMessageFactory {

  private static final String SHOULD_NOT_HAVE_FAILED = "%nExpecting%n  <%s>%nto not have failed.%n" + Warning.WARNING;

  public static ErrorMessageFactory shouldNotHaveFailed(CompletableFuture<?> actual) {
    return new ShouldNotHaveFailed(actual);
  }

  private ShouldNotHaveFailed(CompletableFuture<?> actual) {
    super(SHOULD_NOT_HAVE_FAILED, actual);
  }
}
