package at.aau.jacoco.model;

import java.util.List;
import java.util.Objects;

import jakarta.xml.bind.Unmarshaller;
import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlAttribute;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;
import jakarta.xml.bind.annotation.adapters.NormalizedStringAdapter;
import jakarta.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import at.aau.util.ListUtils;

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
    return ListUtils.unmodifiableList(methods);
  }

  public List<Counter> getCounters() {
    return ListUtils.unmodifiableList(counters);
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
    if (this == o) {
      return true;
    }
    if (!(o instanceof Class)) {
      return false;
    }

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

  /**
   * Callback method invoked by JAXB (Java Architecture for XML Binding) after unmarshalling an XML content tree into a
   * Java object. This method is called after all the properties are unmarshalled, and it allows custom initialization
   * or processing after the unmarshalling completes.
   *
   * <p>The method iterates over the list of {@link Method}s and sets their parent class reference
   * to this class instance.
   *
   * @param unmarshaller the {@link Unmarshaller} that generated this callback; provides context for the unmarshalling
   *                     process
   * @param parent       the parent object in the object graph; can be {@code null} if this object is the root
   * @see Unmarshaller
   * @see <a href="https://docs.oracle.com/javase/8/docs/api/javax/xml/bind/Unmarshaller.html#unmarshalEventCallback">
   *     Unmarshaller unmarshalEventCallback Documentation</a>
   */
  void afterUnmarshal(Unmarshaller unmarshaller, Object parent) {
    if (methods != null) {
      for (Method method : methods) {
        method.setParentClass(this);
      }
    }
  }

}
