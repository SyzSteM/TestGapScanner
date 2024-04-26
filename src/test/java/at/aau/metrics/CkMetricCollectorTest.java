package at.aau.metrics;

import static org.assertj.core.api.Assertions.assertThat;

import java.nio.file.Paths;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

class CkMetricCollectorTest {
  private static final Logger LOGGER = LoggerFactory.getLogger(CkMetricCollectorTest.class);

  @Test
  void shouldReturnCkMetrics_whenPathIsValid() {
    var classPath = Paths.get("src", "test", "projects", "test-project", "src", "main");
    var metrics = CkMetricCollector.collect(classPath);

    for (var entry : metrics) {
      LOGGER.info("{}", entry);
    }

    assertThat(metrics).isNotEmpty();
  }
}
