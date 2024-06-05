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

  @Override
  public int hashCode() {
    int result = Objects.hashCode(name);
    result = 31 * result + Objects.hashCode(classes);
    result = 31 * result + Objects.hashCode(sourceFiles);
    result = 31 * result + Objects.hashCode(counters);
    return result;
  }

  @Override
  public final boolean equals(Object o) {
    if (this == o) return true;
    if (!(o instanceof Package)) return false;

    Package aPackage = (Package) o;
    return Objects.equals(name, aPackage.name)
        && Objects.equals(classes, aPackage.classes)
        && Objects.equals(sourceFiles, aPackage.sourceFiles)
        && Objects.equals(counters, aPackage.counters);
  }

  @Override
  public String toString() {
    return "Package{" + "name='" + name + '\'' + '}';
  }
}
