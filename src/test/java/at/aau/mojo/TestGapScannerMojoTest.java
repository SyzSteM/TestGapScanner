package at.aau.mojo;

import static io.takari.maven.testing.AbstractTestResources.assertFilesPresent;

import java.io.File;

import io.takari.maven.testing.TestMavenRuntime;
import io.takari.maven.testing.TestResources;

import org.junit.Rule;
import org.junit.Test;

public class TestGapScannerMojoTest {

  @Rule
  public final TestResources resources = new TestResources();

  @Rule
  public final TestMavenRuntime maven = new TestMavenRuntime();

  @Test
  public void test() throws Exception {
    File baseDir = resources.getBasedir("test-project");

    maven.executeMojo(baseDir, "test-gap-scanner");

    //    assertFileContents("", baseDir, "target/test-gap-report.txt");
    assertFilesPresent(baseDir, "target/site/jacoco/jacoco.xml", "target/test-gap-report.txt");
  }

}