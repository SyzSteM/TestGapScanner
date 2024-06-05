package at.aau.model;

import at.aau.jacoco.model.Counter;
import at.aau.jacoco.model.Method;
import java.util.List;
import java.util.Objects;

public final class UntestedClassWithRisk {
  private final String fqn;
  private final double risk;
  private final List<Method> methods;
  private final List<Counter> counters;

  private UntestedClassWithRisk(
      String fqn, double risk, List<Method> methods, List<Counter> counters) {
    this.fqn = fqn;
    this.risk = risk;
    this.methods = methods;
    this.counters = counters;
  }

  public static UntestedClassWithRisk of(
      String fqn, double risk, List<Method> methods, List<Counter> counters) {
    return new UntestedClassWithRisk(fqn, risk, methods, counters);
  }

  public String getFqn() {
    return fqn;
  }

  public double getRisk() {
    return risk;
  }

  public List<Method> getMethods() {
    return List.copyOf(methods);
  }

  public List<Counter> getCounters() {
    return List.copyOf(counters);
  }

  @Override
  public int hashCode() {
    int result = Objects.hashCode(fqn);
    result = 31 * result + Double.hashCode(risk);
    result = 31 * result + Objects.hashCode(methods);
    result = 31 * result + Objects.hashCode(counters);
    return result;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (!(o instanceof UntestedClassWithRisk)) return false;

    UntestedClassWithRisk that = (UntestedClassWithRisk) o;
    return Double.compare(risk, that.risk) == 0
        && Objects.equals(fqn, that.fqn)
        && Objects.equals(methods, that.methods)
        && Objects.equals(counters, that.counters);
  }

  @Override
  public String toString() {
    return "UntestedClassWithRisk{" + "fqn='" + fqn + '\'' + ", risk=" + risk + '}';
  }
}
