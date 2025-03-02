package at.aau.jacoco.model;

import java.util.Objects;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlAttribute;
import jakarta.xml.bind.annotation.XmlRootElement;
import jakarta.xml.bind.annotation.adapters.CollapsedStringAdapter;
import jakarta.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "counter")
public class Counter {

  @XmlAttribute(name = "type", required = true)
  @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
  private String type;

  @XmlAttribute(name = "missed", required = true)
  private int missed;

  @XmlAttribute(name = "covered", required = true)
  private int covered;

  public String getType() {
    return type;
  }

  public int getMissed() {
    return missed;
  }

  public int getCovered() {
    return covered;
  }

  @Override
  public int hashCode() {
    int result = Objects.hashCode(type);
    result = 31 * result + missed;
    result = 31 * result + covered;
    return result;
  }

  @Override
  public final boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (!(o instanceof Counter)) {
      return false;
    }

    Counter counter = (Counter) o;
    return missed == counter.missed
        && covered == counter.covered
        && Objects.equals(type, counter.type);
  }

  @Override
  public String toString() {
    return "Counter{"
        + "type='"
        + type
        + '\''
        + ", missed="
        + missed
        + ", covered="
        + covered
        + '}';
  }

}
