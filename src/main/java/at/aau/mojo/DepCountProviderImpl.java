package at.aau.mojo;

import java.util.List;

import javax.inject.Singleton;

import org.apache.maven.model.Dependency;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.project.MavenProject;

@Singleton
public class DepCountProviderImpl implements DepCountProvider {

  @Override
  public long getDepCount(MavenProject project, String scope) throws MojoExecutionException {
    List<Dependency> dependencies = project.getDependencies();

    return dependencies.stream()
        .filter(dep -> (scope == null || scope.isEmpty()) || scope.equals(dep.getScope()))
        .count();
  }

}
