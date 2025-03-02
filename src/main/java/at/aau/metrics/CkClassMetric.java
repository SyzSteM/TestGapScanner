package at.aau.metrics;

import java.util.Objects;
import java.util.function.Function;

import com.github.mauricioaniche.ck.CKClassResult;
import com.google.common.base.MoreObjects;

import at.aau.model.ClassMetricType;

public final class CkClassMetric {

  private final ClassMetricType type;
  private final String description;
  private final Function<CKClassResult, Integer> getter;

  private CkClassMetric(
      ClassMetricType type, String description, Function<CKClassResult, Integer> getter) {
    this.type = type;
    this.description = description;
    this.getter = getter;
  }

  public static CkClassMetric of(
      ClassMetricType type, String description, Function<CKClassResult, Integer> getter) {
    return new CkClassMetric(type, description, getter);
  }

  public ClassMetricType getType() {
    return type;
  }

  public String getDescription() {
    return description;
  }

  public Integer getMetricValueFromResult(CKClassResult result) {
    return getter.apply(result);
  }

  @Override
  public int hashCode() {
    int result = Objects.hashCode(type);
    result = 31 * result + Objects.hashCode(description);
    result = 31 * result + Objects.hashCode(getter);
    return result;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (!(o instanceof CkClassMetric)) {
      return false;
    }

    CkClassMetric that = (CkClassMetric) o;
    return type == that.type
        && Objects.equals(description, that.description)
        && Objects.equals(getter, that.getter);
  }

  @Override
  public String toString() {
    return MoreObjects.toStringHelper(this)
        .add("type", type)
        .add("description", description)
        .add("getter", getter)
        .toString();
  }

}
