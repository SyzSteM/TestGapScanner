package at.aau.model;

import java.util.Objects;

import com.google.common.base.MoreObjects;

public final class MetricMeasurement {

  private final MetricType type;
  private final double value;

  private MetricMeasurement(MetricType type, double value) {
    this.type = type;
    this.value = value;
  }

  public static MetricMeasurement of(MetricType type, double value) {
    return new MetricMeasurement(type, value);
  }

  public MetricType getType() {
    return type;
  }

  public double getValue() {
    return value;
  }

  @Override
  public int hashCode() {
    int result = Objects.hashCode(type);
    result = 31 * result + Double.hashCode(value);
    return result;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (!(o instanceof MetricMeasurement)) {
      return false;
    }

    MetricMeasurement that = (MetricMeasurement) o;
    return Double.compare(value, that.value) == 0 && Objects.equals(type, that.type);
  }

  @Override
  public String toString() {
    return MoreObjects.toStringHelper(this).add("type", type).add("value", value).toString();
  }

}
