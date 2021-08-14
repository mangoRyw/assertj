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
 * Copyright 2012-2021 the original author or authors.
 */
package org.assertj.core.internal.paths;

import static org.assertj.core.api.Assertions.catchThrowable;
import static org.assertj.core.api.BDDAssertions.then;
import static org.assertj.core.error.ShouldHaveParent.shouldHaveParent;
import static org.assertj.core.util.AssertionsUtil.expectAssertionError;
import static org.assertj.core.util.FailureMessages.actualIsNull;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import org.assertj.core.internal.PathsBaseTest;
import org.junit.jupiter.api.Test;

class Paths_assertHasParentRaw_Test extends PathsBaseTest {

  @Test
  void should_fail_if_actual_is_null() {
    // GIVEN
    Path expected = tempDir.resolve("expected");
    // WHEN
    AssertionError error = expectAssertionError(() -> paths.assertHasParentRaw(info, null, expected));
    // THEN
    then(error).hasMessage(actualIsNull());
  }

  @Test
  void should_fail_if_expected_is_null() {
    // GIVEN
    Path actual = tempDir.resolve("actual");
    // WHEN
    Throwable thrown = catchThrowable(() -> paths.assertHasParentRaw(info, actual, null));
    // THEN
    then(thrown).isInstanceOf(NullPointerException.class)
                .hasMessage("expected parent path should not be null");
  }

  @Test
  void should_fail_if_actual_has_no_parent() {
    // GIVEN
    Path actual = tempDir.getRoot();
    Path expected = tempDir.resolve("expected");
    // WHEN
    AssertionError error = expectAssertionError(() -> paths.assertHasParentRaw(info, actual, expected));
    // THEN
    then(error).hasMessage(shouldHaveParent(actual, expected).create());
  }

  @Test
  void should_fail_if_actual_parent_is_not_expected_parent() throws IOException {
    // GIVEN
    Path actual = Files.createFile(tempDir.resolve("actual"));
    Path expected = Files.createFile(tempDir.resolve("expected"));
    // WHEN
    AssertionError error = expectAssertionError(() -> paths.assertHasParentRaw(info, actual, expected));
    // THEN
    then(error).hasMessage(shouldHaveParent(actual, actual.getParent(), expected).create());
  }

  @Test
  void should_pass_if_actual_has_expected_parent() throws IOException {
    // GIVEN
    Path actual = Files.createFile(tempDir.resolve("actual"));
    Path expected = tempDir;
    // WHEN
    paths.assertHasParentRaw(info, actual, expected);
  }

  @Test
  void should_fail_if_actual_is_not_canonical() throws IOException {
    // GIVEN
    Path expected = Files.createDirectory(tempDir.resolve("expected"));
    Path file = Files.createFile(expected.resolve("file"));
    Path actual = Files.createSymbolicLink(tempDir.resolve("actual"), file);
    // WHEN
    AssertionError error = expectAssertionError(() -> paths.assertHasParentRaw(info, actual, expected));
    // THEN
    then(error).hasMessage(shouldHaveParent(actual, actual.getParent(), expected).create());
  }

  @Test
  void should_fail_if_expected_is_not_canonical() throws IOException {
    // GIVEN
    Path directory = Files.createDirectory(tempDir.resolve("directory"));
    Path expected = Files.createSymbolicLink(tempDir.resolve("expected"), directory);
    Path actual = Files.createFile(directory.resolve("actual"));
    // WHEN
    AssertionError error = expectAssertionError(() -> paths.assertHasParentRaw(info, actual, expected));
    // THEN
    then(error).hasMessage(shouldHaveParent(actual, actual.getParent(), expected).create());
  }

}
