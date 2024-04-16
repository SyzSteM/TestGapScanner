package at.aau.jacoco.model;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlAttribute;
import jakarta.xml.bind.annotation.XmlRootElement;
import jakarta.xml.bind.annotation.adapters.CollapsedStringAdapter;
import jakarta.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "counter")
public class Counter {

  @XmlAttribute(name = "type", required = true)
  @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
  private String type;

  @XmlAttribute(name = "missed", required = true)
  private int missed;

  @XmlAttribute(name = "covered", required = true)
  private int covered;

  public String getType() {
    return type;
  }

  public int getMissed() {
    return missed;
  }

  public int getCovered() {
    return covered;
  }
}
