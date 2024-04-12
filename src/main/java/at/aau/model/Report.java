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
}
