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
package org.assertj.core.api;

import static java.lang.Math.abs;

import java.util.Comparator;

public class ComparatorFactory {

  public static final ComparatorFactory INSTANCE = new ComparatorFactory();

  public Comparator<Double> doubleComparatorWithPrecision(final double precision) {
    @SuppressWarnings("unused") // can't use <> with anonymous class in java 8
    Comparator<Double> closeToComparator = new Comparator<Double>() {
      @Override
      public int compare(Double o1, Double o2) {
        if (abs(o1 - o2) < precision) return 0;
        return o1 - o2 > 0 ? 1 : -1;
      }

      @Override
      public String toString() {
        return "double comparator at precision " + precision;
      }
    };
    return closeToComparator;
  }

  public Comparator<Float> floatComparatorWithPrecision(final float precision) {
    @SuppressWarnings("unused")
    Comparator<Float> closeToComparator = new Comparator<Float>() {
      @Override
      public int compare(Float o1, Float o2) {
        if(abs(o1 - o2) < precision) return 0;
        return o1 - o2 > 0 ? 1 : -1;
      }

      @Override
      public String toString() {
        return "float comparator at precision " + precision;
      }
    };
    return closeToComparator;
  }
}
