package at.aau.metrics;

import at.aau.model.ClassMetrics;
import at.aau.model.MetricMeasurement;
import at.aau.util.PathHelper;
import com.github.mauricioaniche.ck.CK;
import com.github.mauricioaniche.ck.CKClassResult;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public final class CkMetricCollector {
  private CkMetricCollector() {
    throw new UnsupportedOperationException("Utility class");
  }

  public static List<ClassMetrics> collectMetrics(Path path) {
    if (!PathHelper.isValidDirectory(path)) {
      return Collections.emptyList();
    }

    List<CKClassResult> ckResults = getCkMetrics(path);
    List<ClassMetrics> classMetrics = new ArrayList<>();

    for (CKClassResult result : ckResults) {
      String className = result.getClassName();
      List<MetricMeasurement> metricMeasurements = new ArrayList<>();

      for (CkMetric metric : CkMetric.CK_CLASS_METRICS) {
        String metricName = metric.getName();
        Integer metricValue = metric.getMetricValueFromResult(result);

        metricMeasurements.add(MetricMeasurement.of(metricName, metricValue));
      }

      classMetrics.add(ClassMetrics.of(className, metricMeasurements, null));
    }

    return List.copyOf(classMetrics);
  }

  private static List<CKClassResult> getCkMetrics(Path path) {
    AggregatingCkNotifier ckNotifier = new AggregatingCkNotifier();

    new CK().calculate(path, ckNotifier);

    return ckNotifier.getCkResults();
  }
}
