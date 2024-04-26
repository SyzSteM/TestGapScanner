package at.aau.metrics;

import com.github.mauricioaniche.ck.CKClassResult;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

public class CkMetric {
  public static final List<CkMetric> CK_CLASS_METRICS = new ArrayList<>();
  public static final List<CkMetric> CK_METHOD_METRICS = new ArrayList<>();

  static {
    CK_CLASS_METRICS.add(of("loc", "Lines Of Code", CKClassResult::getLoc));
    CK_CLASS_METRICS.add(of("wmc", "Weighted Methods per Class", CKClassResult::getWmc));
    CK_CLASS_METRICS.add(of("rfc", "Response For a Class", CKClassResult::getRfc));
    CK_CLASS_METRICS.add(of("cbo", "Coupling Between Object classes", CKClassResult::getCbo));
    CK_CLASS_METRICS.add(of("dit", "Depth of Inheritance Tree", CKClassResult::getDit));
    CK_CLASS_METRICS.add(of("noc", "Number Of Children", CKClassResult::getNoc));
    CK_CLASS_METRICS.add(of("fin", "Fan In", CKClassResult::getFanin));
    CK_CLASS_METRICS.add(of("fout", "Fan Out", CKClassResult::getFanout));
    CK_CLASS_METRICS.add(of("nom", "Number Of Methods", CKClassResult::getNumberOfMethods));
    CK_CLASS_METRICS.add(of("nof", "Number Of Fields", CKClassResult::getNumberOfFields));
  }

  private final String name;
  private final String description;
  private final Function<CKClassResult, Integer> getter;

  private CkMetric(String name, String description, Function<CKClassResult, Integer> getter) {
    this.name = name;
    this.description = description;
    this.getter = getter;
  }

  private static CkMetric of(
      String name, String description, Function<CKClassResult, Integer> getter) {
    return new CkMetric(name, description, getter);
  }

  @Override
  public int hashCode() {
    return new HashCodeBuilder(17, 37)
        .append(getName())
        .append(getDescription())
        .append(getter)
        .toHashCode();
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj) return true;

    if (obj == null || getClass() != obj.getClass()) return false;

    CkMetric ckMetric = (CkMetric) obj;

    return new EqualsBuilder()
        .append(getName(), ckMetric.getName())
        .append(getDescription(), ckMetric.getDescription())
        .append(getter, ckMetric.getter)
        .isEquals();
  }

  @Override
  public String toString() {
    return new ToStringBuilder(this)
        .append("name", name)
        .append("description", description)
        .toString();
  }

  public String getName() {
    return name;
  }

  public String getDescription() {
    return description;
  }

  public Integer getMetricValueFromResult(CKClassResult result) {
    return getter.apply(result);
  }
}
