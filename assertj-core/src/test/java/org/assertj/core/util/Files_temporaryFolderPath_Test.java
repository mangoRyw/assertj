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
package org.assertj.core.util;

import static java.io.File.separator;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.util.Strings.append;

import org.junit.jupiter.api.Test;

/**
 * Tests for <code>{@link Files#temporaryFolderPath()}</code>.
 * 
 * @author Alex Ruiz
 * @author Yvonne Wang
 */
class Files_temporaryFolderPath_Test extends Files_TestCase {

  @Test
  void should_find_path_of_temporary_folder() {
    String a = Files.temporaryFolderPath();
    String e = append(separator).to(systemTemporaryFolder());
    assertThat(a).isEqualTo(e);
  }
}
