package at.aau.model;

import at.aau.util.ListUtils;
import com.google.common.base.MoreObjects;
import com.google.common.base.Objects;
import java.util.List;

public final class ClassMetrics {
  private final String className;
  private final List<MetricMeasurement> classMetrics;
  private final List<MetricMeasurement> methodMetrics;

  private ClassMetrics(
      String className,
      List<MetricMeasurement> classMetrics,
      List<MetricMeasurement> methodMetrics) {
    this.className = className;
    this.classMetrics = ListUtils.unmodifiableList(classMetrics);
    this.methodMetrics = ListUtils.unmodifiableList(methodMetrics);
  }

  public static ClassMetrics of(
      String className,
      List<MetricMeasurement> classMetrics,
      List<MetricMeasurement> methodMetrics) {
    return new ClassMetrics(className, classMetrics, methodMetrics);
  }

  public String getClassName() {
    return className;
  }

  @Override
  public int hashCode() {
    return Objects.hashCode(getClassName(), getClassMetrics(), getMethodMetrics());
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj) return true;
    if (obj == null || getClass() != obj.getClass()) return false;
    ClassMetrics that = (ClassMetrics) obj;
    return Objects.equal(getClassName(), that.getClassName())
        && Objects.equal(getClassMetrics(), that.getClassMetrics())
        && Objects.equal(getMethodMetrics(), that.getMethodMetrics());
  }

  @Override
  public String toString() {
    return MoreObjects.toStringHelper(this)
        .add("className", className)
        .add("classMetrics", classMetrics)
        .add("methodMetrics", methodMetrics)
        .toString();
  }

  public List<MetricMeasurement> getClassMetrics() {
    return classMetrics;
  }

  public List<MetricMeasurement> getMethodMetrics() {
    return methodMetrics;
  }
}
