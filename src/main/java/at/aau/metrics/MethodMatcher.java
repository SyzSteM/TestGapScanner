package at.aau.metrics;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import com.github.mauricioaniche.ck.CKMethodResult;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import at.aau.jacoco.model.Method;
import at.aau.model.MethodDescriptor;
import at.aau.model.MetricMeasurement;
import at.aau.model.MetricType;
import at.aau.model.MetricsData;

public final class MethodMatcher {

  private static final Logger log = LoggerFactory.getLogger(MethodMatcher.class);

  private MethodMatcher() {
    throw new UnsupportedOperationException("Utility class");
  }

  public static Optional<MethodDescriptor> methodDescriptorFromCkResult(
      CKMethodResult ckMethodResult
  ) {
    return methodDescriptorFromCkResult(ckMethodResult.getQualifiedMethodName());
  }

  public static Optional<MethodDescriptor> methodDescriptorFromCkResult(
      String qualifiedMethodName
  ) {
    if (StringUtils.isBlank(qualifiedMethodName)) {
      log.error("Qualified method name is blank; return empty");
      return Optional.empty();
    }

    // ignore static initializer blocks, for example 'static { }' at the beginning of a class
    if (qualifiedMethodName.equalsIgnoreCase("(initializer 1)")) {
      return Optional.empty();
    }

    String[] parts = StringUtils.split(qualifiedMethodName, '/');

    if (parts.length != 2) {
      log.error("Qualified method name is malformed; return empty - [name={}]", qualifiedMethodName);
      return Optional.empty();
    }

    String classAndMethodPart = parts[0];
    String parameterPart = parts[1];

    String className = StringUtils.substringBeforeLast(classAndMethodPart, ".");
    String methodName = StringUtils.substringAfterLast(classAndMethodPart, ".");
    List<String> parameters = new ArrayList<>();

    if (parameterPart.contains("[") && parameterPart.contains("]")) {
      parameters = List.of(StringUtils.substringBetween(parameterPart, "[", "]").split(","));
    }

    return Optional.of(MethodDescriptor.of(className, methodName, parameters));
  }

  public static Optional<MethodDescriptor> methodDescriptorFromJacoco(Method method) {
    return methodDescriptorFromJacoco(method.getClassName(), method.getName(), method.getDesc());
  }

  public static Optional<MethodDescriptor> methodDescriptorFromJacoco(
      String className,
      String methodName,
      String methodDesc
  ) {
    if (StringUtils.isAnyBlank(className, methodName, methodDesc)) {
      log.error("Any parameter is blank; return empty");
      return Optional.empty();
    }

    String normalizedClassName = MetricUtils.normalizeClassName(className);
    String parameterString = StringUtils.substringBetween(methodDesc, "(", ")");
    List<String> parameters = new ArrayList<>();

    if (StringUtils.isNoneBlank(parameterString)) {
      parameters = Arrays.stream(parameterString.split(";"))
          .map(MetricUtils::normalizeClassName)
          .map(p -> p.substring(1))
          .collect(Collectors.toList());
    }

    return Optional.of(MethodDescriptor.of(normalizedClassName, methodName, parameters));
  }

  public static List<MetricsData> methodDescriptorFromMaat(Path maatPath, MetricType maatMetricType) {
    List<String> maatLines;

    try {
      maatLines = Files.readAllLines(maatPath);
    } catch (IOException e) {
      log.error("Error reading lines from maat path", e);
      return Collections.emptyList();
    }

    boolean firstLine = true;
    List<MetricsData> metrics = new ArrayList<>();

    for (String maatLine : maatLines) {
      if (firstLine) {
        firstLine = false;
        continue;
      }

      String[] maatParts = StringUtils.split(maatLine, ',');

      if (maatParts.length != 2) {
        log.error("Invalid maat line; return empty - [line='{}']", maatLine);
        return Collections.emptyList();
      }

      String fullFilePath = maatParts[0];
      String metric = maatParts[1];

      String fqn = StringUtils.substringBetween(fullFilePath, "src/main/java/", ".java");

      if (StringUtils.isBlank(fqn)) {
        log.info("Invalid line or just not a java file; skip - [line={}]", maatLine);
        continue;
      }

      String nFqn = MetricUtils.normalizeClassName(fqn);

      MetricMeasurement metricMeasurement = MetricMeasurement.of(maatMetricType, Double.parseDouble(metric));
      MetricsData metricsData = MetricsData.of(nFqn, List.of(metricMeasurement));

      metrics.add(metricsData);
    }

    return metrics;
  }

}
