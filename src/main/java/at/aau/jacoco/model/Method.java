package at.aau.jacoco.model;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlAttribute;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;
import jakarta.xml.bind.annotation.adapters.NormalizedStringAdapter;
import jakarta.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import java.util.List;

import at.aau.util.ListHelper;

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
}
