package at.aau.mojo;

import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;

@Mojo(name = "greeting")
public class GreetingMojo extends AbstractMojo {

  private String greeting;

  @Override
  public void execute() throws MojoExecutionException, MojoFailureException {
    getLog().info("Hello, " + greeting);
  }

  public String getGreeting() {
    return greeting;
  }

  @Parameter(property = "name", defaultValue = "${project.version}")
  public void setGreeting(String greeting) {
    this.greeting = greeting;
  }

}
