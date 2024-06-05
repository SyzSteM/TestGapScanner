package at.aau.model;

import java.util.Objects;

public final class  ClassWithRisk {
  private final String className;
  private final double risk;

  private ClassWithRisk(String className, double risk) {
    this.className = className;
    this.risk = risk;
  }

  public static ClassWithRisk of(String className, double risk) {
    return new ClassWithRisk(className, risk);
  }

  public String getClassName() {
    return className;
  }

  public double getRisk() {
    return risk;
  }

  @Override
  public int hashCode() {
    int result = Objects.hashCode(className);
    result = 31 * result + Double.hashCode(risk);
    return result;
  }

  @Override
  public final boolean equals(Object o) {
    if (this == o) return true;
    if (!(o instanceof ClassWithRisk)) return false;

    ClassWithRisk that = (ClassWithRisk) o;
    return Double.compare(risk, that.risk) == 0 && Objects.equals(className, that.className);
  }

  @Override
  public String toString() {
    return "ClassWithRisk{" + "className='" + className + '\'' + ", risk=" + risk + '}';
  }
}
