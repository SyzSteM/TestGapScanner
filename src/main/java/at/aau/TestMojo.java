package at.aau;

import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;
import org.apache.maven.plugins.annotations.LifecyclePhase;
import org.apache.maven.plugins.annotations.Mojo;

@Mojo(name = "hello", defaultPhase = LifecyclePhase.INITIALIZE)
public class TestMojo extends AbstractMojo {
  @Override
  public void execute() throws MojoExecutionException, MojoFailureException {
    getLog().info("TestMojo started");
  }
}
