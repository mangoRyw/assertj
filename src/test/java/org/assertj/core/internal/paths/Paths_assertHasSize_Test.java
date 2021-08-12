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
import static org.assertj.core.error.ShouldBeRegularFile.shouldBeRegularFile;
import static org.assertj.core.error.ShouldExist.shouldExist;
import static org.assertj.core.error.ShouldHaveSize.shouldHaveSize;
import static org.assertj.core.util.AssertionsUtil.expectAssertionError;
import static org.assertj.core.util.FailureMessages.actualIsNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.file.Files;
import java.nio.file.Path;

import org.assertj.core.internal.PathsBaseTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class Paths_assertHasSize_Test extends PathsBaseTest {

  @Test
  void should_fail_if_actual_is_null() {
    // WHEN
    AssertionError error = expectAssertionError(() -> paths.assertHasSize(info, null, 0L));
    // THEN
    then(error).hasMessage(actualIsNull());
  }

  @Test
  void should_fail_if_actual_does_not_exist() {
    // GIVEN
    Path actual = tempDir.resolve("non-existent");
    // WHEN
    AssertionError error = expectAssertionError(() -> paths.assertHasSize(info, actual, 0L));
    // THEN
    then(error).hasMessage(shouldExist(actual).create());
  }

  @Test
  void should_fail_if_actual_is_not_regular_file() throws IOException {
    // GIVEN
    Path actual = Files.createDirectory(tempDir.resolve("directory"));
    // WHEN
    AssertionError error = expectAssertionError(() -> paths.assertHasSize(info, actual, 0L));
    // THEN
    then(error).hasMessage(shouldBeRegularFile(actual).create());
  }

  @Test
  void should_rethrow_IOException_as_UncheckedIOException() throws IOException {
    // GIVEN
    Path actual = Files.createFile(tempDir.resolve("actual"));
    IOException exception = new IOException("boom!");
    given(nioFilesWrapper.size(actual)).willThrow(exception);
    // WHEN
    Throwable thrown = catchThrowable(() -> paths.assertHasSize(info, actual, 0L));
    // THEN
    then(thrown).isInstanceOf(UncheckedIOException.class)
                .hasCause(exception);
  }

  @Test
  void should_pass_if_actual_size_is_equal_to_expected_size() throws IOException {
    // GIVEN
    Path actual = Files.write(tempDir.resolve("actual"), "content".getBytes());
    // WHEN/THEN
    paths.assertHasSize(info, actual, 7L);
  }

  @Test
  void should_fail_if_actual_size_is_not_equal_to_expected_size() throws IOException {
    // GIVEN
    Path actual = Files.write(tempDir.resolve("actual"), "content".getBytes());
    // WHEN
    AssertionError error = expectAssertionError(() -> paths.assertHasSize(info, actual, 6L));
    // THEN
    then(error).hasMessage(shouldHaveSize(actual, 6L).create());
  }

}
