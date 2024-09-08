package at.aau.metrics;

import at.aau.model.MethodMetricType;
import com.github.mauricioaniche.ck.CKMethodResult;
import com.google.common.base.MoreObjects;
import java.util.Objects;
import java.util.function.Function;

public final class CkMethodMetric {
  private final MethodMetricType type;
  private final String description;
  private final Function<CKMethodResult, Integer> getter;

  private CkMethodMetric(
      MethodMetricType type, String description, Function<CKMethodResult, Integer> getter) {
    this.type = type;
    this.description = description;
    this.getter = getter;
  }

  public static CkMethodMetric of(
      MethodMetricType type, String description, Function<CKMethodResult, Integer> getter) {
    return new CkMethodMetric(type, description, getter);
  }

  public MethodMetricType getType() {
    return type;
  }

  public String getDescription() {
    return description;
  }

  public int getMetricValueFromResult(CKMethodResult result) {
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
  public final boolean equals(Object o) {
    if (this == o) return true;
    if (!(o instanceof CkMethodMetric)) return false;

    CkMethodMetric metric = (CkMethodMetric) o;
    return type == metric.type
        && Objects.equals(description, metric.description)
        && Objects.equals(getter, metric.getter);
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
