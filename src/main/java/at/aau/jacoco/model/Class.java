package at.aau.jacoco.model;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlAttribute;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;
import jakarta.xml.bind.annotation.adapters.NormalizedStringAdapter;
import jakarta.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import java.util.List;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "class")
public class Class {

  @XmlAttribute(name = "name", required = true)
  @XmlJavaTypeAdapter(NormalizedStringAdapter.class)
  private String name;

  @XmlAttribute(name = "sourcefilename")
  @XmlJavaTypeAdapter(NormalizedStringAdapter.class)
  private String sourceFilename;

  @XmlElement(name = "method")
  private List<Method> methods;

  @XmlElement(name = "counter")
  private List<Counter> counters;

  public String getName() {
    return name;
  }

  public String getSourceFilename() {
    return sourceFilename;
  }

  public List<Method> getMethods() {
    return ListHelper.unmodifiableList(methods);
  }

  public List<Counter> getCounters() {
    return ListHelper.unmodifiableList(counters);
  }
}
