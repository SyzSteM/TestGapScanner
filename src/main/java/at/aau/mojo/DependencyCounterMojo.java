package at.aau.mojo;

import javax.inject.Inject;

import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;
import org.apache.maven.plugins.annotations.LifecyclePhase;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;
import org.apache.maven.project.MavenProject;

@Mojo(name = "dependency-counter", defaultPhase = LifecyclePhase.COMPILE)
public class DependencyCounterMojo extends AbstractMojo {

  private final DepCountProvider depCountProvider;

  @Parameter(defaultValue = "${project}", required = true, readonly = true)
  private MavenProject project;

  @Parameter(property = "scope")
  private String scope;

  @Inject
  public DependencyCounterMojo(DepCountProvider depCountProvider) {
    this.depCountProvider = depCountProvider;
  }

  @Override
  public void execute() throws MojoExecutionException, MojoFailureException {
    getLog().info("Number of dependencies: " + depCountProvider.getDepCount(project, scope));
  }

}
