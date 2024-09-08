package at.aau.mojo;

import at.aau.jacoco.JacocoCoverageCollector;
import at.aau.jacoco.JacocoCoverageFilter;
import at.aau.jacoco.model.Method;
import at.aau.jacoco.model.Package;
import at.aau.metrics.CkMetricCollector;
import at.aau.metrics.MetricUtils;
import at.aau.metrics.RiskMetricCalculator;
import at.aau.model.MethodWithRisk;
import at.aau.model.MetricsData;
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

  private static List<Method> getUntestedMethods(Path coverageReport) throws Exception {
    List<Package> metrics = JacocoCoverageCollector.collect(coverageReport);

    return JacocoCoverageFilter.getUntestedMethods(metrics);
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

    List<Method> uncoveredMethods;

    try {
      uncoveredMethods = getUntestedMethods(coverageReport);
    } catch (Exception e) {
      getLog().error("Failed to parse coverage report; abort", e);
      return;
    }

    getLog().info("Calculating risk scores for untested classes...");

    List<MetricsData> methodMetricsData = CkMetricCollector.collectMetrics(projectBaseDirPath);
    List<MetricsData> untestedMethodsMetricsData =
        MetricUtils.getUntestedMethodMetrics(uncoveredMethods, methodMetricsData);
    List<MetricsData> normalizedMetricsData =
        RiskMetricCalculator.normalizeMetricsData(untestedMethodsMetricsData);
    List<MethodWithRisk> descendingMethodRiskScores =
        RiskMetricCalculator.getDescendingMethodRiskScores(normalizedMetricsData);

    for (int i = 0; i < descendingMethodRiskScores.size(); i++) {
      MethodWithRisk matchingClass = descendingMethodRiskScores.get(i);
      String message =
          String.format(
              "%d. FQN: %s, Risk: %.2f",
              i + 1, matchingClass.getMethodDescriptor(), matchingClass.getRisk());
      getLog().info(String.format("%d:", i + 1));
      getLog().info(String.format("  FQN: %s", matchingClass.getMethodDescriptor()));
      getLog().info(String.format("  Risk: %.2f", matchingClass.getRisk()));
    }
  }
}
