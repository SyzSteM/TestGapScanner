package at.aau.model;

import java.util.List;
import java.util.Objects;

import com.google.common.base.MoreObjects;

public class MetricsData {

  private final MethodDescriptor methodDescriptor;
  private final List<MetricMeasurement> methodMetrics;
  private final List<MetricMeasurement> classMetrics;

  private MetricsData(
      MethodDescriptor methodDescriptor,
      List<MetricMeasurement> methodMetrics,
      List<MetricMeasurement> classMetrics
  ) {
    this.methodDescriptor = methodDescriptor;
    this.methodMetrics = methodMetrics;
    this.classMetrics = classMetrics;
  }

  public static MetricsData of(
      String className,
      List<MetricMeasurement> classMetrics
  ) {
    return of(className, null, null, null, classMetrics);
  }

  public static MetricsData of(
      String className,
      String methodName,
      List<String> parameterNames,
      List<MetricMeasurement> methodMetrics,
      List<MetricMeasurement> classMetrics
  ) {
    return new MetricsData(
        MethodDescriptor.of(className, methodName, parameterNames), methodMetrics, classMetrics);
  }

  public static MetricsData of(
      MethodDescriptor methodDescriptor,
      List<MetricMeasurement> methodMetrics,
      List<MetricMeasurement> classMetrics
  ) {
    return new MetricsData(methodDescriptor, methodMetrics, classMetrics);
  }

  public void addClassMetrics(List<MetricMeasurement> classMetrics) {
    if (this.classMetrics == null) {
      return;
    }

    this.classMetrics.addAll(classMetrics);
  }

  public String getClassName() {
    return methodDescriptor.getClassName();
  }

  public String getMethodName() {
    return methodDescriptor.getMethodName();
  }

  public List<String> getMethodParameters() {
    return methodDescriptor.getParameters();
  }

  public MethodDescriptor getMethodDescriptor() {
    return methodDescriptor;
  }

  public List<MetricMeasurement> getMethodMetrics() {
    return methodMetrics;
  }

  public List<MetricMeasurement> getClassMetrics() {
    return classMetrics;
  }

  @Override
  public int hashCode() {
    int result = Objects.hashCode(methodDescriptor);
    result = 31 * result + Objects.hashCode(methodMetrics);
    result = 31 * result + Objects.hashCode(classMetrics);
    return result;
  }

  @Override
  public final boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (!(o instanceof MetricsData)) {
      return false;
    }

    MetricsData that = (MetricsData) o;
    return Objects.equals(methodDescriptor, that.methodDescriptor)
        && Objects.equals(methodMetrics, that.methodMetrics)
        && Objects.equals(classMetrics, that.classMetrics);
  }

  @Override
  public String toString() {
    return MoreObjects.toStringHelper(this)
        .add("methodDescriptor", methodDescriptor)
        .add("methodMetrics", methodMetrics)
        .add("classMetrics", classMetrics)
        .toString();
  }

}
