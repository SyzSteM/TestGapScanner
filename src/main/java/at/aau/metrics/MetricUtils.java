package at.aau.metrics;

import at.aau.jacoco.model.Class;
import at.aau.model.ClassMetrics;
import at.aau.model.UntestedClassWithRisk;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

public final class MetricUtils {

  private MetricUtils() {
    throw new UnsupportedOperationException("Utility class");
  }

  public static List<ClassMetrics> filterUntestedClassMetrics(
      List<Class> untestedClasses, List<ClassMetrics> classMetrics) {
    Map<String, ClassMetrics> classToMetricsMap = new HashMap<>();

    Map<String, Class> fqnToClassMap =
        untestedClasses.stream()
            .collect(Collectors.toMap(k -> normalizeClassName(k.getName()), Function.identity()));

    return classMetrics.stream()
        .filter(m -> fqnToClassMap.containsKey(m.getClassName()))
        .collect(Collectors.toList());
  }

  private static String normalizeClassName(String className) {
    return className.replace("/", ".");
  }

  private static Comparator<UntestedClassWithRisk> sortByRiskDescending() {
    return Comparator.comparingDouble(UntestedClassWithRisk::getRisk).reversed();
  }
}
