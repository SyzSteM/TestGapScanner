package at.aau.jacoco.model;

import at.aau.util.ListUtils;
import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlAttribute;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;
import jakarta.xml.bind.annotation.adapters.NormalizedStringAdapter;
import jakarta.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import java.util.List;
import java.util.Objects;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "sourcefile")
public class Sourcefile {

  @XmlAttribute(name = "name", required = true)
  @XmlJavaTypeAdapter(NormalizedStringAdapter.class)
  private String name;

  @XmlElement(name = "line")
  private List<Line> lines;

  @XmlElement(name = "counter")
  private List<Counter> counters;

  public String getName() {
    return name;
  }

  public List<Line> getLines() {
    return ListUtils.unmodifiableList(lines);
  }

  public List<Counter> getCounters() {
    return ListUtils.unmodifiableList(counters);
  }

  @Override
  public int hashCode() {
    int result = Objects.hashCode(name);
    result = 31 * result + Objects.hashCode(lines);
    result = 31 * result + Objects.hashCode(counters);
    return result;
  }

  @Override
  public final boolean equals(Object o) {
    if (this == o) return true;
    if (!(o instanceof Sourcefile)) return false;

    Sourcefile that = (Sourcefile) o;
    return Objects.equals(name, that.name)
        && Objects.equals(lines, that.lines)
        && Objects.equals(counters, that.counters);
  }

  @Override
  public String toString() {
    return "Sourcefile{" + "name='" + name + '\'' + '}';
  }
}
