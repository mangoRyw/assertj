package org.assertj.core.api;

import static org.assertj.core.api.Assertions.catchThrowable;
import static org.assertj.core.api.BDDAssertions.then;
import static org.assertj.core.error.ShouldBeInstance.shouldBeInstance;
import static org.assertj.core.error.ShouldNotBeNull.shouldNotBeNull;
import static org.mockito.BDDMockito.willReturn;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class InstanceOfAssertFactoryTest {

  private InstanceOfAssertFactory<Integer, Assert<?, ?>> underTest;

  @Mock
  private AssertFactory<Integer, Assert<?, ?>> mockAssertFactory;

  @Mock
  private Assert<?, ?> mockAssert;

  @BeforeEach
  void setUp() {
    underTest = new InstanceOfAssertFactory<>(Integer.class, mockAssertFactory);
  }

  @Test
  void should_throw_npe_if_no_type_is_given() {
    // WHEN
    Throwable thrown = catchThrowable(() -> new InstanceOfAssertFactory<>(null, mockAssertFactory));
    // THEN
    then(thrown).isInstanceOf(NullPointerException.class)
                .hasMessage(shouldNotBeNull("type").create());
  }

  @Test
  void should_throw_npe_if_no_assert_factory_is_given() {
    // WHEN
    Throwable thrown = catchThrowable(() -> new InstanceOfAssertFactory<>(Object.class, null));
    // THEN
    then(thrown).isInstanceOf(NullPointerException.class)
                .hasMessage(shouldNotBeNull("assertFactory").create());
  }

  @Test
  void should_return_assert_factory_result_if_actual_is_an_instance_of_given_type() {
    // GIVEN
    int value = 0;
    willReturn(mockAssert).given(mockAssertFactory).createAssert(value);
    // WHEN
    Assert<?, ?> result = underTest.createAssert(value);
    // THEN
    then(result).isSameAs(mockAssert);
  }

  @Test
  void should_throw_assertion_error_if_actual_is_not_an_instance_of_given_type() {
    // GIVEN
    String value = "string";
    // WHEN
    Throwable thrown = catchThrowable(() -> underTest.createAssert(value));
    // THEN
    then(thrown).isInstanceOf(AssertionError.class)
                .hasMessage(shouldBeInstance("string", Integer.class).create());
  }

}
