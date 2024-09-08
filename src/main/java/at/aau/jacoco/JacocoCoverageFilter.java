package at.aau.jacoco;

import at.aau.jacoco.model.Class;
import at.aau.jacoco.model.Method;
import at.aau.jacoco.model.Package;
import at.aau.util.ListUtils;
import java.util.List;
import java.util.stream.Collectors;

public final class JacocoCoverageFilter {
  private static final List<String> IGNORED_METHOD_NAMES =
      List.of("<init>", "equals", "hashCode", "toString");

  private JacocoCoverageFilter() {
    throw new UnsupportedOperationException("Utility class");
  }

  public static List<Method> getUntestedMethods(List<Package> packages) {
    return packages.stream()
        .flatMap(p -> p.getClasses().stream())
        .filter(JacocoCoverageFilter::isNormalClass)
        .flatMap(c -> c.getMethods().stream())
        .filter(JacocoCoverageFilter::isNotIgnoredMethod)
        .filter(JacocoCoverageFilter::isUncoveredMethod)
        .collect(Collectors.toList());
  }

  private static boolean isNormalClass(Class clazz) {
    return ListUtils.notNullOrEmpty(clazz.getMethods())
        && ListUtils.notNullOrEmpty(clazz.getCounters());
  }

  private static boolean isNotIgnoredMethod(Method method) {
    return IGNORED_METHOD_NAMES.stream().noneMatch(n -> n.equalsIgnoreCase(method.getName()));
  }

  private static boolean isUncoveredMethod(Method method) {
    return method.getCounters().stream().allMatch(counter -> counter.getCovered() == 0);
  }
}
