package at.aau;

import at.aau.metrics.CkMetricCollector;
import java.nio.file.Paths;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SomeTest {
  private static final Logger LOGGER = LoggerFactory.getLogger(SomeTest.class);

  @Test
  public void someTest() {
    var path = Paths.get("src", "test", "projects", "test-project", "src", "main");
    var metrics = CkMetricCollector.collect(path);

    for (var entry : metrics) {
      LOGGER.info("{}", entry);
    }
  }
}
