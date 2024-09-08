package at.aau.jacoco;

import static org.assertj.core.api.Assertions.assertThat;

import java.nio.file.Path;
import org.junit.Test;

public class JacocoCoverageCollectorTest {
  @Test
  public void shouldReturnUntestedClasses_whenPathIsValid() throws Exception {
    var jacocoReportPath = Path.of("src", "test", "resources", "jacoco", "jacoco.xml");
    var metrics = JacocoCoverageCollector.collect(jacocoReportPath);
    var untestedClasses = JacocoCoverageFilter.getUntestedMethods(metrics);

    assertThat(untestedClasses).isNotEmpty();
  }
}
