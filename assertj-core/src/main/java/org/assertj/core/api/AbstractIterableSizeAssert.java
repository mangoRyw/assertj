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
 * Copyright 2012-2023 the original author or authors.
 */
package org.assertj.core.api;

//@format:off
public abstract class AbstractIterableSizeAssert<SELF extends AbstractIterableAssert<SELF, ACTUAL, ELEMENT, ELEMENT_ASSERT>, 
                                                 ACTUAL extends Iterable<? extends ELEMENT>, 
                                                 ELEMENT, 
                                                 ELEMENT_ASSERT extends AbstractAssert<ELEMENT_ASSERT, ELEMENT>>
    extends AbstractIntegerAssert<AbstractIterableSizeAssert<SELF, ACTUAL, ELEMENT, ELEMENT_ASSERT>> {
//@format:on

  protected AbstractIterableSizeAssert(Integer actual, Class<?> selfType) {
    super(actual, selfType);
  }

  public abstract AbstractIterableAssert<SELF, ACTUAL, ELEMENT, ELEMENT_ASSERT> returnToIterable();
}
