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
package org.assertj.core.error;

import java.nio.file.FileSystem;
import java.nio.file.Path;

/**
 * Creates an error message indicating that an assertion that verifies the {@link FileSystem} for
 * a given path does not match a given file system.
 *
 * @author Ashley Scopes
 */
public class ShouldHaveFileSystem extends BasicErrorMessageFactory {
  private static final String PATH_SHOULD_HAVE_FILE_SYSTEM = "%nExpecting path:%n  %s%nto have file system:%n  %s";

  public static ErrorMessageFactory shouldHaveFileSystem(final Path actual, final FileSystem fileSystem) {
    return new ShouldHaveFileSystem(actual, fileSystem);
  }

  private ShouldHaveFileSystem(final Path actual, final FileSystem expected) {
    super(PATH_SHOULD_HAVE_FILE_SYSTEM, actual, expected);
  }
}
