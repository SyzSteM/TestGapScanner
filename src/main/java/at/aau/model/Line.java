package at.aau.model;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlAttribute;
import jakarta.xml.bind.annotation.XmlRootElement;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "line")
public class Line {

  @XmlAttribute(name = "nr", required = true)
  private long lineNumber;

  @XmlAttribute(name = "mi")
  private long missedInstructionCount;

  @XmlAttribute(name = "ci")
  private long coveredInstructionCount;

  @XmlAttribute(name = "mb")
  private long missedBranchCount;

  @XmlAttribute(name = "cb")
  private long coveredBranchCount;

  public long getLineNumber() {
    return lineNumber;
  }

  public long getMissedInstructionCount() {
    return missedInstructionCount;
  }

  public long getCoveredInstructionCount() {
    return coveredInstructionCount;
  }

  public long getMissedBranchCount() {
    return missedBranchCount;
  }

  public long getCoveredBranchCount() {
    return coveredBranchCount;
  }
}
