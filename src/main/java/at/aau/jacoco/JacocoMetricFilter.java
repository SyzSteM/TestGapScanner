package at.aau.jacoco;

import at.aau.jacoco.model.Class;
import at.aau.jacoco.model.Package;
import at.aau.jacoco.util.ListHelper;
import java.util.List;
import java.util.stream.Collectors;

public final class JacocoMetricFilter {
  private JacocoMetricFilter() {
    throw new UnsupportedOperationException("Utility class");
  }

  public static List<Class> getUntestedClasses(List<Package> packages) {
    return packages.stream()
        .flatMap(p -> p.getClasses().stream())
        .filter(JacocoMetricFilter::isNormalClass)
        .filter(JacocoMetricFilter::isNotCovered)
        .collect(Collectors.toList());
  }

  private static boolean isNormalClass(Class clazz) {
    return ListHelper.listNotNullOrEmpty(clazz.getMethods())
        && ListHelper.listNotNullOrEmpty(clazz.getCounters());
  }

  private static boolean isNotCovered(Class clazz) {
    return clazz.getCounters().stream().allMatch(counter -> counter.getCovered() == 0);
  }
}
