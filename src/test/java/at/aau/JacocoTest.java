package at.aau;

import static org.junit.Assert.assertFalse;

import at.aau.jacoco.JacocoMetricCollector;
import at.aau.jacoco.JacocoMetricFilter;
import java.nio.file.Path;
import org.junit.Test;

public class JacocoTest {
  @Test
  public void test2() throws Exception {
    var metrics = JacocoMetricCollector.collect(Path.of("src/test/resources/jacoco/jacoco.xml"));
    var untestedClasses = JacocoMetricFilter.getUntestedClasses(metrics);

    assertFalse(untestedClasses.isEmpty());
  }
}
