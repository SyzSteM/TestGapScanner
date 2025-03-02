package at.aau.metrics;

import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import at.aau.jacoco.model.Method;
import at.aau.model.MethodDescriptor;
import at.aau.model.MetricsData;
import at.aau.model.UntestedClassWithRisk;

public final class MetricUtils {

  private static final Logger log = LoggerFactory.getLogger(MetricUtils.class);

  private MetricUtils() {
    throw new UnsupportedOperationException("Utility class");
  }

  public static List<MetricsData> getUntestedMethodMetrics(
      List<Method> untestedMethods, List<MetricsData> methodMetrics) {
    Map<MethodDescriptor, Method> descriptorToMethodMap = new HashMap<>();

    for (Method untestedMethod : untestedMethods) {
      Optional<MethodDescriptor> methodDescriptorOptional =
          MethodMatcher.methodDescriptorFromJacoco(untestedMethod);

      if (methodDescriptorOptional.isEmpty()) {
        log.error("Method descriptor is empty; skip - [method='{}']", untestedMethod);

        continue;
      }

      descriptorToMethodMap.put(methodDescriptorOptional.get(), untestedMethod);
    }

    return methodMetrics.stream()
        .filter(m -> descriptorToMethodMap.containsKey(m.getMethodDescriptor()))
        .collect(Collectors.toList());
  }

  static String normalizeClassName(String className) {
    return className.replace("/", ".");
  }

  private static Comparator<UntestedClassWithRisk> sortByRiskDescending() {
    return Comparator.comparingDouble(UntestedClassWithRisk::getRisk).reversed();
  }

}
