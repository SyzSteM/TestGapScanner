package at.aau.metrics;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import com.github.mauricioaniche.ck.CK;
import com.github.mauricioaniche.ck.CKClassResult;
import com.github.mauricioaniche.ck.CKMethodResult;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import at.aau.model.MethodDescriptor;
import at.aau.model.MetricMeasurement;
import at.aau.model.MetricType;
import at.aau.model.MetricsData;
import at.aau.util.PathHelper;

public final class CkMetricCollector {

  private static final Logger log = LoggerFactory.getLogger(CkMetricCollector.class);

  private CkMetricCollector() {
    throw new UnsupportedOperationException("Utility class");
  }

  public static List<MetricsData> collectCkMetrics(Path path) {
    if (!PathHelper.isValidDirectory(path)) {
      return Collections.emptyList();
    }

    List<CKClassResult> ckClassResults = getCkMetrics(path);
    List<MetricsData> methodMetrics = new ArrayList<>();

    for (CKClassResult classResult : ckClassResults) {
      List<MetricMeasurement> classMetricMeasurements = getClassMeasurements(classResult);

      for (CKMethodResult methodResult : classResult.getMethods()) {
        if (methodResult.isConstructor()) {
          continue;
        }

        Optional<MethodDescriptor> methodDescriptor =
            MethodMatcher.methodDescriptorFromCkResult(methodResult.getQualifiedMethodName());

        if (methodDescriptor.isEmpty()) {
          log.warn(
              "Method descriptor is empty; skip - [classResult='{}', method='{}']",
              classResult,
              methodResult
          );

          continue;
        }

        List<MetricMeasurement> methodMetricMeasurements = getMethodMeasurements(methodResult);

        methodMetrics.add(
            MetricsData.of(
                methodDescriptor.get(), methodMetricMeasurements, classMetricMeasurements));
      }
    }

    return List.copyOf(methodMetrics);
  }

  private static List<MetricMeasurement> getClassMeasurements(CKClassResult classResult) {
    List<MetricMeasurement> metricMeasurements = new ArrayList<>();

    for (CkClassMetric metric : CkMetric.getCkClassMetrics()) {
      MetricType metricType = metric.getType();
      int metricValue = metric.getMetricValueFromResult(classResult);

      metricMeasurements.add(MetricMeasurement.of(metricType, metricValue));
    }

    return metricMeasurements;
  }

  private static List<MetricMeasurement> getMethodMeasurements(CKMethodResult methodResult) {
    List<MetricMeasurement> metricMeasurements = new ArrayList<>();

    for (CkMethodMetric metric : CkMetric.getCkMethodMetrics()) {
      MetricType metricType = metric.getType();
      int metricValue = metric.getMetricValueFromResult(methodResult);

      metricMeasurements.add(MetricMeasurement.of(metricType, metricValue));
    }

    return metricMeasurements;
  }

  private static List<CKClassResult> getCkMetrics(Path path) {
    AggregatingCkNotifier ckNotifier = new AggregatingCkNotifier();

    new CK().calculate(path, ckNotifier);

    return ckNotifier.getCkResults();
  }

}
