package at.aau.jacoco.model;

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

  @Override
  public int hashCode() {
    int result = Long.hashCode(lineNumber);
    result = 31 * result + Long.hashCode(missedInstructionCount);
    result = 31 * result + Long.hashCode(coveredInstructionCount);
    result = 31 * result + Long.hashCode(missedBranchCount);
    result = 31 * result + Long.hashCode(coveredBranchCount);
    return result;
  }

  @Override
  public final boolean equals(Object o) {

    if (this == o) return true;
    if (!(o instanceof Line)) return false;

    Line line = (Line) o;
    return lineNumber == line.lineNumber
        && missedInstructionCount == line.missedInstructionCount
        && coveredInstructionCount == line.coveredInstructionCount
        && missedBranchCount == line.missedBranchCount
        && coveredBranchCount == line.coveredBranchCount;
  }

  @Override
  public String toString() {
    return "Line{"
        + "lineNumber="
        + lineNumber
        + ", missedInstructionCount="
        + missedInstructionCount
        + ", coveredInstructionCount="
        + coveredInstructionCount
        + ", missedBranchCount="
        + missedBranchCount
        + ", coveredBranchCount="
        + coveredBranchCount
        + '}';
  }
}
