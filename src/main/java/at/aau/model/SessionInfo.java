package at.aau.model;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlAttribute;
import jakarta.xml.bind.annotation.XmlRootElement;
import jakarta.xml.bind.annotation.adapters.NormalizedStringAdapter;
import jakarta.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "sessioninfo")
public class SessionInfo {

  @XmlAttribute(name = "id", required = true)
  @XmlJavaTypeAdapter(NormalizedStringAdapter.class)
  private String id;

  @XmlAttribute(name = "start", required = true)
  @XmlJavaTypeAdapter(NormalizedStringAdapter.class)
  private String start;

  @XmlAttribute(name = "dump", required = true)
  @XmlJavaTypeAdapter(NormalizedStringAdapter.class)
  private String dump;

  public String getId() {
    return id;
  }

  public String getStart() {
    return start;
  }

  public String getDump() {
    return dump;
  }
}
