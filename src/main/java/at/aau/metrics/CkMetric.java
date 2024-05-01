package at.aau.metrics;

import com.github.mauricioaniche.ck.CKClassResult;
import com.google.common.base.MoreObjects;
import com.google.common.base.Objects;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

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
    return Objects.hashCode(getName(), getDescription(), getter);
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj) return true;
    if (!(obj instanceof CkMetric)) return false;
    CkMetric ckMetric = (CkMetric) obj;
    return Objects.equal(getName(), ckMetric.getName())
        && Objects.equal(getDescription(), ckMetric.getDescription())
        && Objects.equal(getter, ckMetric.getter);
  }

  @Override
  public String toString() {
    return MoreObjects.toStringHelper(this)
        .add("name", name)
        .add("description", description)
        .add("getter", getter)
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
