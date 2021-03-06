/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package zipkin2.reporter;

import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class InMemoryReporterMetricsTest {
  @Test
  public void incrementMessagesDropped_sameExceptionTypeIsNotCountedMoreThanOnce() {
    InMemoryReporterMetrics inMemoryReporterMetrics = new InMemoryReporterMetrics();

    inMemoryReporterMetrics.incrementMessagesDropped(new RuntimeException());
    inMemoryReporterMetrics.incrementMessagesDropped(new RuntimeException());
    inMemoryReporterMetrics.incrementMessagesDropped(new IllegalStateException());

    assertThat(inMemoryReporterMetrics.messagesDroppedByCause().get(RuntimeException.class)).isEqualTo(2);
    assertThat(inMemoryReporterMetrics.messagesDroppedByCause().get(IllegalStateException.class)).isEqualTo(1);
    assertThat(inMemoryReporterMetrics.messagesDroppedByCause().size()).isEqualTo(2);
  }

  @Test
  public void incrementMessagesDropped_multipleOccurrencesOfSameExceptionTypeAreCounted() {
    InMemoryReporterMetrics inMemoryReporterMetrics = new InMemoryReporterMetrics();

    inMemoryReporterMetrics.incrementMessagesDropped(new RuntimeException());
    inMemoryReporterMetrics.incrementMessagesDropped(new RuntimeException());
    inMemoryReporterMetrics.incrementMessagesDropped(new IllegalStateException());

    assertThat(inMemoryReporterMetrics.messagesDropped()).isEqualTo(3);
  }
}