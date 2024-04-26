package at.aau.jacoco;

import static org.junit.Assert.assertFalse;

import java.nio.file.Path;
import org.junit.Test;

public class JacocoCoverageCollectorTest {
  @Test
  public void test2() throws Exception {
    var metrics = JacocoCoverageCollector.collect(Path.of("src/test/resources/jacoco/jacoco.xml"));
    var untestedClasses = JacocoCoverageFilter.getUntestedClasses(metrics);

    assertFalse(untestedClasses.isEmpty());
  }
}
