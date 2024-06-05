package at.aau.jacoco.model;

import at.aau.util.ListHelper;
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
@XmlRootElement(name = "method")
public class Method {

  @XmlAttribute(name = "name", required = true)
  @XmlJavaTypeAdapter(NormalizedStringAdapter.class)
  private String name;

  @XmlAttribute(name = "desc", required = true)
  @XmlJavaTypeAdapter(NormalizedStringAdapter.class)
  private String desc;

  @XmlAttribute(name = "line")
  @XmlJavaTypeAdapter(NormalizedStringAdapter.class)
  private String line;

  @XmlElement(name = "counter")
  private List<Counter> counters;

  public String getName() {
    return name;
  }

  public String getDesc() {
    return desc;
  }

  public String getLine() {
    return line;
  }

  public List<Counter> getCounters() {
    return ListHelper.unmodifiableList(counters);
  }

  @Override
  public int hashCode() {
    int result = Objects.hashCode(name);
    result = 31 * result + Objects.hashCode(desc);
    result = 31 * result + Objects.hashCode(line);
    result = 31 * result + Objects.hashCode(counters);
    return result;
  }

  @Override
  public final boolean equals(Object o) {
    if (this == o) return true;
    if (!(o instanceof Method)) return false;

    Method method = (Method) o;
    return Objects.equals(name, method.name)
        && Objects.equals(desc, method.desc)
        && Objects.equals(line, method.line)
        && Objects.equals(counters, method.counters);
  }

  @Override
  public String toString() {
    return "Method{"
        + "name='"
        + name
        + '\''
        + ", desc='"
        + desc
        + '\''
        + ", line='"
        + line
        + '\''
        + '}';
  }
}
