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
 * Copyright 2012-2020 the original author or authors.
 */
package org.assertj.core.api.junit.jupiter;

import org.assertj.core.api.BDDSoftAssertions;
import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static java.util.Arrays.asList;
import static org.junit.jupiter.api.TestInstance.Lifecycle.PER_CLASS;
import static org.junit.jupiter.api.TestInstance.Lifecycle.PER_METHOD;

/**
 * Integration tests for {@link SoftAssertionsExtension} with {@link BDDSoftAssertions}.
 *
 * <p>This class is effectively a copy of {@link SoftAssertionsExtensionIntegrationTest}
 * with {@link SoftAssertions} replaced by {@link BDDSoftAssertions}.
 *
 * @author Sam Brannen
 * @since 3.13
 * @see SoftAssertionsExtensionIntegrationTest
 */
@DisplayName("JUnit Jupiter BDD Soft Assertions extension integration tests")
class BDDSoftAssertionsExtensionIntegrationTest extends AbstractSoftAssertionsExtensionIntegrationTests {

	@Override
	protected Class<?> getTestInstancePerMethodTestCase() {
		return TestInstancePerMethodExample.class;
	}

	@Override
	protected Class<?> getTestInstancePerClassTestCase() {
		return TestInstancePerClassExample.class;
	}

	@Override
	protected Class<?> getTestInstancePerMethodNestedTestCase() {
		return TestInstancePerMethodNestedExample.class;
	}

	@Override
	protected Class<?> getTestInstancePerClassNestedTestCase() {
		return TestInstancePerClassNestedExample.class;
	}

	@Override
	protected Class<?> getTestInstancePerMethodConstructorTestCase() {
		return TestInstancePerMethodConstructorExample.class;
	}

	@Override
	protected Class<?> getTestInstancePerClassConstructorTestCase() {
		return TestInstancePerClassConstructorExample.class;
	}

	@Override
	protected Class<?> getTestInstancePerMethodNestedConstructorTestCase() {
		return TestInstancePerMethodNestedConstructorExample.class;
	}

	@Override
	protected Class<?> getTestInstancePerClassNestedConstructorTestCase() {
		return TestInstancePerClassNestedConstructorExample.class;
	}

	// -------------------------------------------------------------------------
  // -------------------- injection of method parameters --------------------
  // -------------------------------------------------------------------------

	@ExtendWith(SoftAssertionsExtension.class)
	@TestMethodOrder(OrderAnnotation.class)
	private static abstract class AbstractSoftAssertionsExample {

		@Test
		@Order(1)
		void multipleFailures(BDDSoftAssertions softly) {
			softly.then(1).isEqualTo(0);
			softly.then(2).isEqualTo(2);
			softly.then(3).isEqualTo(4);
		}

		@Test
		@Order(2)
		void allAssertionsShouldPass(BDDSoftAssertions softly) {
			softly.then(1).isEqualTo(1);
			softly.then(asList(1, 2)).containsOnly(1, 2);
		}

		@ParameterizedTest
		@CsvSource({ "1, 1, 2", "1, 2, 3" })
		@Order(3)
		void parameterizedTest(int a, int b, int sum, BDDSoftAssertions softly) {
			softly.then(a + b).as("sum").isEqualTo(sum);
			softly.then(a).as("operand 1 is equal to operand 2").isEqualTo(b);
		}
	}

	@TestInstance(PER_METHOD)
	@Disabled("Executed via the JUnit Platform Test Kit")
	static class TestInstancePerMethodExample extends AbstractSoftAssertionsExample {
	}

	@TestInstance(PER_CLASS)
	@Disabled("Executed via the JUnit Platform Test Kit")
	static class TestInstancePerClassExample extends AbstractSoftAssertionsExample {
	}

	@TestInstance(PER_METHOD)
	@Disabled("Executed via the JUnit Platform Test Kit")
	static class TestInstancePerMethodNestedExample extends AbstractSoftAssertionsExample {

		@Nested
		@Disabled("Executed via the JUnit Platform Test Kit")
		class InnerExample extends AbstractSoftAssertionsExample {
		}
	}

	@TestInstance(PER_CLASS)
	@Disabled("Executed via the JUnit Platform Test Kit")
	static class TestInstancePerClassNestedExample extends AbstractSoftAssertionsExample {

		@Nested
		@Disabled("Executed via the JUnit Platform Test Kit")
		class InnerExample extends AbstractSoftAssertionsExample {
		}
	}

  // -------------------------------------------------------------------------
  // ------------------ injection of constructor parameters ------------------
  // -------------------------------------------------------------------------

  @ExtendWith(SoftAssertionsExtension.class)
  @TestMethodOrder(OrderAnnotation.class)
  private static abstract class AbstractSoftAssertionsConstructorExample {
    private final BDDSoftAssertions softly;

    protected AbstractSoftAssertionsConstructorExample(BDDSoftAssertions softly) {
      this.softly = softly;
    }

    @Test
    @Order(1)
    void multipleFailures() {
      softly.then(1).isEqualTo(0);
      softly.then(2).isEqualTo(2);
      softly.then(3).isEqualTo(4);
    }

    @Test
    @Order(2)
    void allAssertionsShouldPass() {
      softly.then(1).isEqualTo(1);
      softly.then(asList(1, 2)).containsOnly(1, 2);
    }

    @ParameterizedTest
    @CsvSource({ "1, 1, 2", "1, 2, 3" })
    @Order(3)
    void parameterizedTest(int a, int b, int sum) {
      softly.then(a + b).as("sum").isEqualTo(sum);
      softly.then(a).as("operand 1 is equal to operand 2").isEqualTo(b);
    }
  }

  @TestInstance(PER_METHOD)
  @Disabled("Executed via the JUnit Platform Test Kit")
  static class TestInstancePerMethodConstructorExample extends AbstractSoftAssertionsConstructorExample {
    protected TestInstancePerMethodConstructorExample(BDDSoftAssertions softly) {
      super(softly);
    }
  }

  @TestInstance(PER_CLASS)
  @Disabled("Executed via the JUnit Platform Test Kit")
  static class TestInstancePerClassConstructorExample extends AbstractSoftAssertionsConstructorExample {
    protected TestInstancePerClassConstructorExample(BDDSoftAssertions softly) {
      super(softly);
    }
  }

  @TestInstance(PER_METHOD)
  @Disabled("Executed via the JUnit Platform Test Kit")
  static class TestInstancePerMethodNestedConstructorExample extends AbstractSoftAssertionsConstructorExample {
    protected TestInstancePerMethodNestedConstructorExample(BDDSoftAssertions softly) {
      super(softly);
    }

    @Nested
    @Disabled("Executed via the JUnit Platform Test Kit")
    class InnerExample extends AbstractSoftAssertionsConstructorExample {
      protected InnerExample(BDDSoftAssertions softly) {
        super(softly);
      }
    }
  }

  @TestInstance(PER_CLASS)
  @Disabled("Executed via the JUnit Platform Test Kit")
  static class TestInstancePerClassNestedConstructorExample extends AbstractSoftAssertionsConstructorExample {
    protected TestInstancePerClassNestedConstructorExample(BDDSoftAssertions softly) {
      super(softly);
    }

    @Nested
    @Disabled("Executed via the JUnit Platform Test Kit")
    class InnerExample extends AbstractSoftAssertionsConstructorExample {
      protected InnerExample(BDDSoftAssertions softly) {
        super(softly);
      }
    }
  }
}
