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
package org.assertj.core.internal.paths;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;
import static org.assertj.core.api.BDDAssertions.then;
import static org.assertj.core.error.ShouldHaveSameFileSystem.shouldHaveSameFileSystem;
import static org.assertj.core.util.AssertionsUtil.expectAssertionError;
import static org.assertj.core.util.FailureMessages.actualIsNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.withSettings;

import java.nio.file.FileSystem;
import java.nio.file.Path;
import org.assertj.core.internal.PathsBaseTest;
import org.junit.jupiter.api.Test;
import org.opentest4j.AssertionFailedError;

class Paths_assertHasSameFileSystem_Test extends PathsBaseTest {

  @Test
  void should_fail_if_actual_path_is_null() {
    // GIVEN
    FileSystem expectedFileSystem = mock(FileSystem.class, withSettings().stubOnly());
    // WHEN
    AssertionError error = expectAssertionError(() -> paths.assertHasSameFileSystem(info, null, expectedFileSystem));
    // THEN
    then(error).hasMessage(actualIsNull());
  }

  @Test
  void should_fail_if_expected_filesystem_is_null() {
    // GIVEN
    Path actualPath = mock(Path.class, withSettings().stubOnly());
    FileSystem actualFileSystem = mock(FileSystem.class, withSettings().stubOnly());
    when(actualPath.getFileSystem()).thenReturn(actualFileSystem);
    // WHEN
    Throwable error = catchThrowable(() -> paths.assertHasSameFileSystem(info, actualPath, null));
    // THEN
    then(error)
      .isInstanceOf(NullPointerException.class)
      .hasMessage("The expected file system should not be null");
  }

  @Test
  void should_fail_if_actual_filesystem_is_null() {
    // GIVEN
    Path actualPath = mock(Path.class, withSettings().stubOnly());
    when(actualPath.getFileSystem()).thenReturn(null);
    FileSystem expectedFileSystem = mock(FileSystem.class, withSettings().stubOnly());
    // WHEN
    Throwable error = catchThrowable(() -> paths.assertHasSameFileSystem(info, actualPath, expectedFileSystem));
    // THEN
    then(error)
      .isInstanceOf(NullPointerException.class)
      .hasMessage("The actual file system should not be null");
  }

  @Test
  void should_fail_if_file_systems_differ() {
    // GIVEN
    Path actualPath = mock(Path.class, withSettings().stubOnly());
    FileSystem actualFileSystem = mock(FileSystem.class, withSettings().stubOnly());
    when(actualPath.getFileSystem()).thenReturn(actualFileSystem);
    FileSystem expectedFileSystem = mock(FileSystem.class, withSettings().stubOnly());
    // WHEN
    AssertionError error = expectAssertionError(() -> paths.assertHasSameFileSystem(info, actualPath, expectedFileSystem));
    // THEN
    then(error)
      .hasMessage(shouldHaveSameFileSystem(actualPath, expectedFileSystem).create())
      .isInstanceOf(AssertionFailedError.class)
      .extracting(AssertionFailedError.class::cast)
      .satisfies(failure -> assertThat(failure.getActual().getEphemeralValue()).isSameAs(actualFileSystem))
      .satisfies(failure -> assertThat(failure.getExpected().getEphemeralValue()).isSameAs(expectedFileSystem));
  }

  @Test
  void should_succeed_if_file_systems_are_same() {
    // GIVEN
    Path actualPath = mock(Path.class, withSettings().stubOnly());
    FileSystem actualFileSystem = mock(FileSystem.class, withSettings().stubOnly());
    when(actualPath.getFileSystem()).thenReturn(actualFileSystem);

    // THEN
    paths.assertHasSameFileSystem(info, actualPath, actualFileSystem);
  }
}
