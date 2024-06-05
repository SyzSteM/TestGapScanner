package at.aau.mojo;

import at.aau.jacoco.JacocoCoverageCollector;
import at.aau.jacoco.JacocoCoverageFilter;
import at.aau.jacoco.model.Class;
import at.aau.jacoco.model.Package;
import at.aau.metrics.CkMetricCollector;
import at.aau.metrics.MetricUtils;
import at.aau.metrics.RiskMetricCalculator;
import at.aau.model.ClassMetrics;
import at.aau.model.ClassWithRisk;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import org.apache.maven.execution.MavenSession;
import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;
import org.apache.maven.project.MavenProject;

@Mojo(name = "test-gap-scanner")
public class TestGapScannerMojo extends AbstractMojo {

  @Parameter(defaultValue = "${project.build.directory}/site/jacoco/jacoco.xml")
  private String coverageReportPath;

  @Parameter(defaultValue = "${project}", readonly = true, required = true)
  private MavenProject project;

  @Parameter(defaultValue = "${session}", readonly = true, required = true)
  private MavenSession session;

  private static List<Class> getUntestedClasses(Path coverageReport) throws Exception {
    List<Package> metrics = JacocoCoverageCollector.collect(coverageReport);

    return JacocoCoverageFilter.getUntestedClasses(metrics);
  }

  @Override
  public void execute() {
    Path projectBaseDirPath = project.getBasedir().toPath();
    Path coverageReport = Path.of(coverageReportPath);

    if (Files.notExists(coverageReport)) {
      getLog().warn("Coverage report not found at " + coverageReport);

      return;
    }

    getLog().info("Valid coverage report found, processing...");

    List<Class> untestedClasses;

    try {
      untestedClasses = getUntestedClasses(coverageReport);
    } catch (Exception e) {
      getLog().error("Failed to parse coverage report; abort", e);
      return;
    }

    getLog().info("Calculating risk scores for untested classes...");

    List<ClassMetrics> classMetrics = CkMetricCollector.collectMetrics(projectBaseDirPath);
    List<ClassMetrics> untestedClassMetrics =
        MetricUtils.filterUntestedClassMetrics(untestedClasses, classMetrics);

    List<ClassWithRisk> descendingClassRiskScores =
        RiskMetricCalculator.getDescendingClassRiskScores(untestedClassMetrics);

    for (int i = 0; i < descendingClassRiskScores.size(); i++) {
      ClassWithRisk matchingClass = descendingClassRiskScores.get(i);
      String message =
          String.format(
              "%d. FQN: %s, Risk: %.2f",
              i + 1, matchingClass.getClassName(), matchingClass.getRisk());
      getLog().info(message);
    }
  }
}
