package at.aau.model;

import com.google.common.base.MoreObjects;
import com.google.common.base.Objects;

public final class MetricMeasurement {
  private final String metricName;
  private final double value;

  private MetricMeasurement(String metricName, double value) {
    this.metricName = metricName;
    this.value = value;
  }

  public static MetricMeasurement of(String metricName, double value) {
    return new MetricMeasurement(metricName, value);
  }

  public String getMetricName() {
    return metricName;
  }

  public double getValue() {
    return value;
  }

  @Override
  public int hashCode() {
    return Objects.hashCode(getMetricName(), getValue());
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj) return true;
    if (obj == null || getClass() != obj.getClass()) return false;
    MetricMeasurement that = (MetricMeasurement) obj;
    return getValue() == that.getValue() && Objects.equal(getMetricName(), that.getMetricName());
  }

  @Override
  public String toString() {
    return MoreObjects.toStringHelper(this)
        .add("metricName", metricName)
        .add("value", value)
        .toString();
  }
}
