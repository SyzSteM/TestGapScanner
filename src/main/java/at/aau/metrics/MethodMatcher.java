package at.aau.metrics;

import at.aau.jacoco.model.Method;
import at.aau.model.MethodDescriptor;
import com.github.mauricioaniche.ck.CKMethodResult;
import java.util.*;
import java.util.stream.Collectors;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MethodMatcher {

  private static final Logger log = LoggerFactory.getLogger(MethodMatcher.class);

  public static Optional<MethodDescriptor> methodDescriptorFromCkResult(
      CKMethodResult ckMethodResult) {
    return methodDescriptorFromCkResult(ckMethodResult.getQualifiedMethodName());
  }

  public static Optional<MethodDescriptor> methodDescriptorFromCkResult(
      String qualifiedMethodName) {
    if (StringUtils.isBlank(qualifiedMethodName)) {
      log.error("Qualified method name is blank; return empty");

      return Optional.empty();
    }

    String[] parts = StringUtils.split(qualifiedMethodName, '/');

    if (parts.length != 2) {
      log.error(
          "Qualified method name is malformed; return empty - [name={}]", qualifiedMethodName);

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
      String className, String methodName, String methodDesc) {
    if (StringUtils.isAnyBlank(className, methodName, methodDesc)) {
      log.error("Any parameter is blank; return empty");

      return Optional.empty();
    }

    String normalizedClassName = MetricUtils.normalizeClassName(className);
    String parameterString = StringUtils.substringBetween(methodDesc, "(", ")");
    List<String> parameters = new ArrayList<>();

    if (StringUtils.isNoneBlank(parameterString)) {
      parameters =
          Arrays.stream(parameterString.split(";"))
              .map(MetricUtils::normalizeClassName)
              .map(p -> p.substring(1))
              .collect(Collectors.toList());
    }

    return Optional.of(MethodDescriptor.of(normalizedClassName, methodName, parameters));
  }

  public static void main(String[] args) {
    // Example data from CKMetrics and JaCoCo
    String ckMetricMethod =
        "at.aau.testproject.management.DocumentManager.addDocument/2[at.aau.testproject.Document,java.lang.String]";
    String jacocoClassName = "at/aau/testproject/management/DocumentManager";
    String jacocoMethodName = "addDocument";
    String jacocoMethodDesc = "(Lat/aau/testproject/Document;Ljava/lang/String;)V";

    methodDescriptorFromCkResult(ckMetricMethod);
    methodDescriptorFromJacoco(jacocoClassName, jacocoMethodName, jacocoMethodDesc);
  }
}
