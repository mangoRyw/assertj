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
package org.assertj.core.error;

import java.time.Period;

/**
 * @author Hayden Meloche
 */
public class ShouldHavePeriod extends BasicErrorMessageFactory {

  private static final String EXPECTED_PREFIX = "%n"
    + "Expecting Period:%n"
    + " <%s>%n"
    + "to have %s ";

  private ShouldHavePeriod(Period actual, int actualSpecific, int expectedSpecific, String metric) {
    super(EXPECTED_PREFIX + metric + " but had %s", actual, expectedSpecific, actualSpecific);
  }

  public static ShouldHavePeriod shouldHaveYears(Period actual, int actualYear, int expectedYear) {
    String metric;
    if (expectedYear == 1 || expectedYear == -1) {
      metric = "year";
    } else {
      metric = "years";
    }
    return new ShouldHavePeriod(actual, actualYear, expectedYear, metric);
  }

  public static ShouldHavePeriod shouldHaveMonths(Period actual, int actualMonths, int expectedMonths) {
    String metric;
    if (expectedMonths == 1 || expectedMonths == -1) {
      metric = "month";
    } else {
      metric = "months";
    }
    return new ShouldHavePeriod(actual, actualMonths, expectedMonths, metric);
  }

  public static ShouldHavePeriod shouldHaveDays(Period actual, int actualDays, int expectedDays) {
    String metric;
    if (expectedDays == 1 || expectedDays == -1) {
      metric = "day";
    } else {
      metric = "days";
    }
    return new ShouldHavePeriod(actual, actualDays, expectedDays, metric);
  }
}
