package at.aau.model;

import com.google.common.base.MoreObjects;
import java.util.Objects;

public final class MethodWithRisk {
  private final MethodDescriptor methodDescriptor;
  private final double risk;

  private MethodWithRisk(MethodDescriptor methodDescriptor, double risk) {
    this.methodDescriptor = methodDescriptor;
    this.risk = risk;
  }

  public static MethodWithRisk of(MethodDescriptor methodDescriptor, double risk) {
    return new MethodWithRisk(methodDescriptor, risk);
  }

  public double getRisk() {
    return risk;
  }

  public MethodDescriptor getMethodDescriptor() {
    return methodDescriptor;
  }

  @Override
  public int hashCode() {
    int result = Objects.hashCode(methodDescriptor);
    result = 31 * result + Double.hashCode(risk);
    return result;
  }

  @Override
  public final boolean equals(Object o) {
    if (this == o) return true;
    if (!(o instanceof MethodWithRisk)) return false;

    MethodWithRisk that = (MethodWithRisk) o;
    return Double.compare(risk, that.risk) == 0
        && Objects.equals(methodDescriptor, that.methodDescriptor);
  }

  @Override
  public String toString() {
    return MoreObjects.toStringHelper(this)
        .add("methodDescriptor", methodDescriptor)
        .add("risk", risk)
        .toString();
  }
}
