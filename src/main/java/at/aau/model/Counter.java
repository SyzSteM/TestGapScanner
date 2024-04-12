package at.aau.model;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlAttribute;
import jakarta.xml.bind.annotation.XmlRootElement;
import jakarta.xml.bind.annotation.XmlType;
import jakarta.xml.bind.annotation.adapters.CollapsedStringAdapter;
import jakarta.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

@XmlAccessorType(XmlAccessType.FIELD)
//@XmlType(name = "")
@XmlRootElement(name = "counter")
public class Counter {

  @XmlAttribute(name = "type", required = true)
  @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
  protected String type;

  @XmlAttribute(name = "missed", required = true)
  protected int missed;

  @XmlAttribute(name = "covered", required = true)
  protected int covered;

  /**
   * Gets the value of the type property.
   *
   * @return possible object is {@link String }
   */
  public String getType() {
    return type;
  }

  /**
   * Sets the value of the type property.
   *
   * @param value allowed object is {@link String }
   */
  public void setType(String value) {
    this.type = value;
  }

  /**
   * Gets the value of the missed property.
   *
   * @return possible object is {@link String }
   */
  //    public String getMissed() {
  //        return missed;
  //    }

  /**
   * Sets the value of the missed property.
   *
   * @param value allowed object is {@link String }
   */
  //    public void setMissed(String value) {
  //        this.missed = value;
  //    }

  /**
   * Gets the value of the covered property.
   *
   * @return possible object is {@link String }
   */
  //    public String getCovered() {
  //        return covered;
  //    }

  /**
   * Sets the value of the covered property.
   *
   * @param value allowed object is {@link String }
   */
  //    public void setCovered(String value) {
  //        this.covered = value;
  //    }

}
