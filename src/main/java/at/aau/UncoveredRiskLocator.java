package at.aau;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import org.apache.commons.io.FileUtils;
import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;

@Mojo(name = "risk-locator")
public class UncoveredRiskLocator extends AbstractMojo {

  @Parameter(defaultValue = "${project.build.directory}/site/jacoco/jacoco.xml")
  private String coverageReportPath;

  @Override
  public void execute() throws MojoExecutionException, MojoFailureException {
    Path coverageReport = Paths.get(coverageReportPath);

    if (Files.notExists(coverageReport)) {
      getLog().warn("Coverage report not found at " + coverageReport);

      return;
    }

    getLog().info("Coverage report found. Proceeding with additional checks...");

    try {
      String reportContent =
          FileUtils.readFileToString(coverageReport.toFile(), StandardCharsets.UTF_8);
    } catch (IOException e) {
      throw new MojoExecutionException("Error reading coverage report", e);
    }
  }
}
