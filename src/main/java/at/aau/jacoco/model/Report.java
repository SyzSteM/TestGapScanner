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
@XmlRootElement(name = "report")
public class Report {

  @XmlAttribute(name = "name", required = true)
  @XmlJavaTypeAdapter(NormalizedStringAdapter.class)
  private String name;

  @XmlElement(name = "sessioninfo")
  private List<SessionInfo> sessionInfo;

  @XmlElement(name = "group")
  private List<Group> groups;

  @XmlElement(name = "package")
  private List<Package> packages;

  @XmlElement(name = "counter")
  private List<Counter> counters;

  public String getName() {
    return name;
  }

  public List<SessionInfo> getSessionInfo() {
    return ListHelper.unmodifiableList(sessionInfo);
  }

  public List<Group> getGroups() {
    return ListHelper.unmodifiableList(groups);
  }

  public List<Package> getPackages() {
    return ListHelper.unmodifiableList(packages);
  }

  public List<Counter> getCounters() {
    return ListHelper.unmodifiableList(counters);
  }

  @Override
  public int hashCode() {
    int result = Objects.hashCode(name);
    result = 31 * result + Objects.hashCode(sessionInfo);
    result = 31 * result + Objects.hashCode(groups);
    result = 31 * result + Objects.hashCode(packages);
    result = 31 * result + Objects.hashCode(counters);
    return result;
  }

  @Override
  public final boolean equals(Object o) {
    if (this == o) return true;
    if (!(o instanceof Report)) return false;

    Report report = (Report) o;
    return Objects.equals(name, report.name)
        && Objects.equals(sessionInfo, report.sessionInfo)
        && Objects.equals(groups, report.groups)
        && Objects.equals(packages, report.packages)
        && Objects.equals(counters, report.counters);
  }

  @Override
  public String toString() {
    return "Report{" + "name='" + name + '\'' + '}';
  }
}
