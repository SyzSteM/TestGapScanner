package at.aau.mojo;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

import org.apache.maven.execution.MavenSession;
import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;
import org.apache.maven.project.MavenProject;

import at.aau.jacoco.model.Method;
import at.aau.model.MethodWithRisk;
import at.aau.util.ScannerUtils;

@Mojo(name = "test-gap-scanner")
public class TestGapScannerMojo extends AbstractMojo {

  @Parameter(defaultValue = "${project.build.directory}/site/jacoco/jacoco.xml")
  private String coverageReportPath;

  @Parameter(defaultValue = "${project.build.directory}/site/code-maat/revisions.csv")
  private String maatRevisionsPath;

  @Parameter(defaultValue = "${project.build.directory}/site/code-maat/soc.csv")
  private String maatSocPath;

  @Parameter(defaultValue = "${project}", readonly = true, required = true)
  private MavenProject project;

  @Parameter(defaultValue = "${session}", readonly = true, required = true)
  private MavenSession session;

  @Override
  public void execute() {
    Path projectBaseDirPath = project.getBasedir().toPath();
    Path coverageReport = Path.of(coverageReportPath);
    Path maatRevisions = Path.of(maatRevisionsPath);
    Path maatSoc = Path.of(maatSocPath);

    if (Files.notExists(coverageReport)) {
      getLog().warn("Coverage report not found at " + coverageReportPath);
      return;
    }

    if (Files.notExists(maatRevisions)) {
      getLog().warn("Maat revisions report not found at " + maatRevisionsPath);
      return;
    }

    if (Files.notExists(maatSoc)) {
      getLog().warn("Maat SoC report not found at " + maatSocPath);
      return;
    }

    getLog().info("Valid reports found, processing...");

    List<Method> untestedMethods;

    try {
      untestedMethods = ScannerUtils.getUntestedMethods(coverageReport);
    } catch (Exception e) {
      getLog().error("Failed to parse coverage report; abort", e);
      return;
    }

    getLog().info("Calculating risk scores for untested classes...");

    List<MethodWithRisk> descendingMethodRiskScores = ScannerUtils.calculateMethodRiskScores(
        projectBaseDirPath, untestedMethods, maatRevisions, maatSoc
    );

    StringBuilder outputBuilder = new StringBuilder().append("\n");
    Path outputFilePath = Path.of(project.getBuild().getDirectory()).resolve("test-gap-report.txt");

    for (int i = 0; i < descendingMethodRiskScores.size(); i++) {
      MethodWithRisk matchingClass = descendingMethodRiskScores.get(i);

      String rank = String.format("%d:", i + 1);
      String descriptor = String.format("  FQN: %s", matchingClass.getMethodDescriptor());
      String symbol = String.format("  Symbol: %s", matchingClass.getMethodDescriptor().getSymbol());
      String risk = String.format("  Risk: %.2f", matchingClass.getRisk());

      outputBuilder.append(rank).append(System.lineSeparator());
      outputBuilder.append(descriptor).append(System.lineSeparator());
      outputBuilder.append(symbol).append(System.lineSeparator());
      outputBuilder.append(risk).append(System.lineSeparator());
    }

    getLog().info(outputBuilder);
    writeToFile(outputFilePath, outputBuilder.toString());
  }

  private void writeToFile(Path filePath, String content) {
    try {
      getLog().info(String.format("Writing report to file: '%s'", filePath));

      Files.writeString(filePath, content, StandardCharsets.UTF_8);
    } catch (IOException e) {
      getLog().error(String.format("Error writing content to file; abort - [filePath='%s']", filePath), e);
    }
  }

}
