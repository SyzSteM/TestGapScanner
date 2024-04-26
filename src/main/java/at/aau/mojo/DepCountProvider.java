package at.aau.mojo;

import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.project.MavenProject;

public interface DepCountProvider {
  long getDepCount(MavenProject project, String scope) throws MojoExecutionException;
}
