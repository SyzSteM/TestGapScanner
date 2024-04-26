package at.aau.mojo;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.file.AccumulatorPathVisitor;
import org.apache.commons.io.filefilter.FileFilterUtils;
import org.apache.maven.execution.MavenSession;
import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;
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

  @Override
  public void execute() throws MojoExecutionException, MojoFailureException {
    Path coverageReport = Paths.get(coverageReportPath);

    if (Files.notExists(coverageReport)) {
      getLog().warn("Coverage report not found at " + coverageReport);

      return;
    }

    getLog().info("Coverage report found. Proceeding with additional checks...");

    Path outputDirPath = Paths.get(project.getBasedir().getPath());
    getLog().info("");
    AccumulatorPathVisitor visitor =
        AccumulatorPathVisitor.withLongCounters(
            FileFilterUtils.suffixFileFilter(".java"), FileFilterUtils.trueFileFilter());

    try {
      Files.walkFileTree(outputDirPath, visitor);
    } catch (IOException e) {
      throw new MojoFailureException("Failed to walk output directory " + outputDirPath, e);
    }

    for (Path classFile : visitor.getFileList()) {
      getLog().info("Processing " + classFile);
    }

    try {
      String reportContent =
          FileUtils.readFileToString(coverageReport.toFile(), StandardCharsets.UTF_8);
    } catch (IOException e) {
      throw new MojoExecutionException("Error reading coverage report", e);
    }
  }
}
