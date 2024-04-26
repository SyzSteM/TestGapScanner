package at.aau.jacoco;

import at.aau.jacoco.model.Class;
import at.aau.jacoco.model.Package;
import at.aau.util.ListHelper;
import java.util.List;
import java.util.stream.Collectors;

public final class JacocoCoverageFilter {
  private JacocoCoverageFilter() {
    throw new UnsupportedOperationException("Utility class");
  }

  public static List<Class> getUntestedClasses(List<Package> packages) {
    return packages.stream()
        .flatMap(p -> p.getClasses().stream())
        .filter(JacocoCoverageFilter::isNormalClass)
        .filter(JacocoCoverageFilter::isNotCovered)
        .collect(Collectors.toList());
  }

  private static boolean isNormalClass(Class clazz) {
    return ListHelper.notNullOrEmpty(clazz.getMethods())
        && ListHelper.notNullOrEmpty(clazz.getCounters());
  }

  private static boolean isNotCovered(Class clazz) {
    return clazz.getCounters().stream().allMatch(counter -> counter.getCovered() == 0);
  }
}
