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

  @Override
  public int hashCode() {
    int result = Objects.hashCode(name);
    result = 31 * result + Objects.hashCode(sourceFilename);
    result = 31 * result + Objects.hashCode(methods);
    result = 31 * result + Objects.hashCode(counters);
    return result;
  }

  @Override
  public final boolean equals(Object o) {
    if (this == o) return true;
    if (!(o instanceof Class)) return false;

    Class aClass = (Class) o;
    return Objects.equals(name, aClass.name)
        && Objects.equals(sourceFilename, aClass.sourceFilename)
        && Objects.equals(methods, aClass.methods)
        && Objects.equals(counters, aClass.counters);
  }

  @Override
  public String toString() {
    return "Class{" + "name='" + name + '\'' + ", sourceFilename='" + sourceFilename + '\'' + '}';
  }
}
