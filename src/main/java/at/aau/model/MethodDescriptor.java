package at.aau.model;

import java.util.List;
import java.util.Objects;

import org.apache.commons.lang3.StringUtils;

import at.aau.util.ListUtils;

public class MethodDescriptor {

  private final String className;
  private final String methodName;
  private final List<String> parameters;

  private MethodDescriptor(String className, String methodName, List<String> parameters) {
    this.className = className;
    this.methodName = methodName;
    this.parameters = ListUtils.unmodifiableList(parameters);
  }

  public static MethodDescriptor of(
      String className, String methodName, List<String> parameterNames) {
    return new MethodDescriptor(className, methodName, parameterNames);
  }

  public String getClassName() {
    return className;
  }

  public String getMethodName() {
    return methodName;
  }

  public List<String> getParameters() {
    return parameters;
  }

  public String getSymbol() {
    return String.format("%s.%s", StringUtils.substringAfterLast(className, "."), methodName);
  }

  @Override
  public int hashCode() {
    int result = Objects.hashCode(className);
    result = 31 * result + Objects.hashCode(methodName);
    result = 31 * result + Objects.hashCode(parameters);
    return result;
  }

  @Override
  public final boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (!(o instanceof MethodDescriptor)) {
      return false;
    }

    MethodDescriptor that = (MethodDescriptor) o;
    return Objects.equals(className, that.className)
        && Objects.equals(methodName, that.methodName)
        && Objects.equals(parameters, that.parameters);
  }

  @Override
  public String toString() {
    return String.format("%s.%s(%s)", className, methodName, String.join(", ", parameters));
  }

}
