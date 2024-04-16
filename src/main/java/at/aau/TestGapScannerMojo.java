package at.aau;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.ArrayList;
import org.apache.commons.io.FileUtils;
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

    var output = Path.of(project.getBuild().getOutputDirectory());

    try {
      var classFiles = new ArrayList<Path>();
      Files.walkFileTree(
          output,
          new SimpleFileVisitor<>() {

            @Override
            public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) {
              if (file.toString().endsWith(".class")) {
                classFiles.add(file);
              }
              return FileVisitResult.CONTINUE;
            }
          });

      for (Path classFile : classFiles) {
        getLog().info("Processing " + classFile);
      }
    } catch (IOException e) {
      throw new MojoExecutionException("Failed to access class files");
    }

    try {
      String reportContent =
          FileUtils.readFileToString(coverageReport.toFile(), StandardCharsets.UTF_8);
    } catch (IOException e) {
      throw new MojoExecutionException("Error reading coverage report", e);
    }
  }
}
