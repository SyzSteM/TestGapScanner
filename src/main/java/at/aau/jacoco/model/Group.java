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
@XmlRootElement(name = "group")
public class Group {

  @XmlAttribute(name = "name", required = true)
  @XmlJavaTypeAdapter(NormalizedStringAdapter.class)
  private String name;

  @XmlElement(name = "group")
  private List<Group> groups;

  @XmlElement(name = "package")
  private List<Package> packages;

  @XmlElement(name = "counter")
  private List<Counter> counters;

  public String getName() {
    return name;
  }

  public List<Group> getGroups() {
    return ListUtils.unmodifiableList(groups);
  }

  public List<Package> getPackages() {
    return ListUtils.unmodifiableList(packages);
  }

  public List<Counter> getCounters() {
    return ListUtils.unmodifiableList(counters);
  }

  @Override
  public int hashCode() {
    int result = Objects.hashCode(name);
    result = 31 * result + Objects.hashCode(groups);
    result = 31 * result + Objects.hashCode(packages);
    result = 31 * result + Objects.hashCode(counters);
    return result;
  }

  @Override
  public final boolean equals(Object o) {
    if (this == o) return true;
    if (!(o instanceof Group)) return false;

    Group group = (Group) o;
    return Objects.equals(name, group.name)
        && Objects.equals(groups, group.groups)
        && Objects.equals(packages, group.packages)
        && Objects.equals(counters, group.counters);
  }

  @Override
  public String toString() {
    return "Group{" + "name='" + name + '\'' + '}';
  }
}
