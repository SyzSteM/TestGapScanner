package at.aau.jacoco.model;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlAttribute;
import jakarta.xml.bind.annotation.XmlRootElement;
import jakarta.xml.bind.annotation.adapters.NormalizedStringAdapter;
import jakarta.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import java.util.Objects;

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

  @Override
  public int hashCode() {
    int result = Objects.hashCode(id);
    result = 31 * result + Objects.hashCode(start);
    result = 31 * result + Objects.hashCode(dump);
    return result;
  }

  @Override
  public final boolean equals(Object o) {
    if (this == o) return true;
    if (!(o instanceof SessionInfo)) return false;

    SessionInfo that = (SessionInfo) o;
    return Objects.equals(id, that.id)
        && Objects.equals(start, that.start)
        && Objects.equals(dump, that.dump);
  }

  @Override
  public String toString() {
    return "SessionInfo{"
        + "id='"
        + id
        + '\''
        + ", start='"
        + start
        + '\''
        + ", dump='"
        + dump
        + '\''
        + '}';
  }
}
