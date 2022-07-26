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
 * Copyright 2012-2022 the original author or authors.
 */
package org.assertj.core.error.uri;

import static java.lang.String.format;
import static org.assertj.core.api.BDDAssertions.then;
import static org.assertj.core.error.uri.ShouldHavePath.shouldHavePath;

import java.net.URI;
import java.net.URL;

import org.assertj.core.internal.TestDescription;
import org.junit.jupiter.api.Test;

class ShouldBeReachable_create_Test {

  //CS304 (manually written) Issue link: https://github.com/assertj/assertj-core/issues/2196
  @Test
  void should_create_error_message_for_uri_when_unreachable() {
    // WHEN
    String error = shouldBeReachable(URI.create("http://www.baidu")).create(new TestDescription("TEST"));
    // THEN
    then(error).isEqualTo(format("[TEST] %n" +
                                       "Expecting actual:%n" +
                                       "  <http://www.baidu>%n" +
                                       "be reachable but it's not"));
  }

  //CS304 (manually written) Issue link: https://github.com/assertj/assertj-core/issues/2196
  @Test
  void should_create_error_message_for_url_when_unreachable() throws Exception {
    // WHEN
    String error = shouldBeReachable(new URL("http://www.baidu")).create(new TestDescription("TEST"));
    // THEN
    then(error).isEqualTo(format("[TEST] %n" +
      "Expecting actual:%n" +
      "  <http://www.baidu>%n" +
      "be reachable but it's not"));
  }
}
