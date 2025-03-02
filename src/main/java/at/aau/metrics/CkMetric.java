package at.aau.metrics;

import java.util.ArrayList;
import java.util.List;

import com.github.mauricioaniche.ck.CKClassResult;
import com.github.mauricioaniche.ck.CKMethodResult;

import at.aau.model.ClassMetricType;
import at.aau.model.MethodMetricType;
import at.aau.util.ListUtils;

public final class CkMetric {

  private static final List<CkClassMetric> CK_CLASS_METRICS = new ArrayList<>();
  private static final List<CkMethodMetric> CK_METHOD_METRICS = new ArrayList<>();

  static {
    CK_CLASS_METRICS.add(
        CkClassMetric.of(ClassMetricType.LOC, "Lines Of Code", CKClassResult::getLoc));
    CK_CLASS_METRICS.add(
        CkClassMetric.of(ClassMetricType.WMC, "Weighted Methods per Class", CKClassResult::getWmc));
    CK_CLASS_METRICS.add(
        CkClassMetric.of(ClassMetricType.RFC, "Response For a Class", CKClassResult::getRfc));
    CK_CLASS_METRICS.add(
        CkClassMetric.of(
            ClassMetricType.CBO, "Coupling Between Object classes", CKClassResult::getCbo));
    CK_CLASS_METRICS.add(
        CkClassMetric.of(ClassMetricType.DIT, "Depth of Inheritance Tree", CKClassResult::getDit));
    CK_CLASS_METRICS.add(
        CkClassMetric.of(ClassMetricType.NOC, "Number Of Children", CKClassResult::getNoc));
    CK_CLASS_METRICS.add(CkClassMetric.of(ClassMetricType.FIN, "Fan In", CKClassResult::getFanin));
    CK_CLASS_METRICS.add(
        CkClassMetric.of(ClassMetricType.FOUT, "Fan Out", CKClassResult::getFanout));
    CK_CLASS_METRICS.add(
        CkClassMetric.of(
            ClassMetricType.NOM, "Number Of Methods", CKClassResult::getNumberOfMethods));
    CK_CLASS_METRICS.add(
        CkClassMetric.of(
            ClassMetricType.NOF, "Number Of Fields", CKClassResult::getNumberOfFields));

    CK_METHOD_METRICS.add(
        CkMethodMetric.of(
            MethodMetricType.CBO, "Coupling Between Object classes", CKMethodResult::getCbo));
    CK_METHOD_METRICS.add(
        CkMethodMetric.of(
            MethodMetricType.CBOM,
            "Coupling Between Object classes modified",
            CKMethodResult::getCboModified
        ));
    CK_METHOD_METRICS.add(
        CkMethodMetric.of(MethodMetricType.FIN, "Fan In", CKMethodResult::getFanin));
    CK_METHOD_METRICS.add(
        CkMethodMetric.of(MethodMetricType.FOUT, "Fan Out", CKMethodResult::getFanout));
    CK_METHOD_METRICS.add(
        CkMethodMetric.of(MethodMetricType.RFC, "Response For a Class", CKMethodResult::getRfc));
    CK_METHOD_METRICS.add(
        CkMethodMetric.of(
            MethodMetricType.WMC, "Weighted Methods per Class", CKMethodResult::getWmc));
  }

  private CkMetric() {
    throw new UnsupportedOperationException("Utility class");
  }

  public static List<CkClassMetric> getCkClassMetrics() {
    return ListUtils.unmodifiableList(CK_CLASS_METRICS);
  }

  public static List<CkMethodMetric> getCkMethodMetrics() {
    return ListUtils.unmodifiableList(CK_METHOD_METRICS);
  }

}
