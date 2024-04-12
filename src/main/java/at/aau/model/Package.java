package at.aau.model;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlAttribute;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;
import jakarta.xml.bind.annotation.adapters.NormalizedStringAdapter;
import jakarta.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import java.util.List;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "package")
public class Package {

  @XmlAttribute(name = "name", required = true)
  @XmlJavaTypeAdapter(NormalizedStringAdapter.class)
  private String name;

  @XmlElement(name = "class")
  private List<Class> classes;

  @XmlElement(name = "sourcefile")
  private List<Sourcefile> sourceFiles;

  @XmlElement(name = "counter")
  private List<Counter> counters;

  public String getName() {
    return name;
  }

  public List<Class> getClasses() {
    return ListHelper.unmodifiableList(classes);
  }

  public List<Sourcefile> getSourceFiles() {
    return ListHelper.unmodifiableList(sourceFiles);
  }

  public List<Counter> getCounters() {
    return ListHelper.unmodifiableList(counters);
  }
}
