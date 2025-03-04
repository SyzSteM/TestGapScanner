package at.aau.metrics;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.DoubleSummaryStatistics;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import at.aau.model.ClassMetricType;
import at.aau.model.MethodMetricType;
import at.aau.model.MethodWithRisk;
import at.aau.model.MetricMeasurement;
import at.aau.model.MetricType;
import at.aau.model.MetricsData;

public final class RiskMetricCalculator {

  private RiskMetricCalculator() {
    throw new UnsupportedOperationException("Utility class");
  }

  public static List<MetricsData> normalizeMetricsData(List<MetricsData> metrics) {
    Map<MetricType, DoubleSummaryStatistics> classMetricsSummary =
        metrics.stream()
            .flatMap(m -> m.getClassMetrics().stream())
            .collect(
                Collectors.groupingBy(
                    MetricMeasurement::getType,
                    Collectors.summarizingDouble(MetricMeasurement::getValue)
                ));

    Map<MetricType, DoubleSummaryStatistics> methodMetricsSummary =
        metrics.stream()
            .flatMap(m -> m.getMethodMetrics().stream())
            .collect(
                Collectors.groupingBy(
                    MetricMeasurement::getType,
                    Collectors.summarizingDouble(MetricMeasurement::getValue)
                ));

    List<MetricsData> normalizedMetrics = new ArrayList<>();
    for (MetricsData metric : metrics) {
      List<MetricMeasurement> classMetrics =
          metric.getClassMetrics().stream()
              .map(
                  m ->
                      MetricMeasurement.of(
                          m.getType(),
                          normalize(
                              m.getValue(),
                              classMetricsSummary.get(m.getType()).getMin(),
                              classMetricsSummary.get(m.getType()).getMax()
                          )
                      ))
              .collect(Collectors.toList());

      List<MetricMeasurement> methodMetrics =
          metric.getMethodMetrics().stream()
              .map(
                  m ->
                      MetricMeasurement.of(
                          m.getType(),
                          normalize(
                              m.getValue(),
                              methodMetricsSummary.get(m.getType()).getMin(),
                              methodMetricsSummary.get(m.getType()).getMax()
                          )
                      ))
              .collect(Collectors.toList());

      normalizedMetrics.add(
          MetricsData.of(metric.getMethodDescriptor(), methodMetrics, classMetrics));
    }

    return normalizedMetrics;
  }

  public static List<MethodWithRisk> getDescendingMethodRiskScores(
      List<MetricsData> normalizedMetricsData
  ) {
    return normalizedMetricsData.stream()
        .map(RiskMetricCalculator::calculateRiskScore)
        .sorted(sortByRiskDescending())
        .collect(Collectors.toList());
  }

  private static Comparator<MethodWithRisk> sortByRiskDescending() {
    return Comparator.comparingDouble(MethodWithRisk::getRisk).reversed();
  }

  private static double normalize(double value, double min, double max) {
    double normalizedValue = value - min;
    double normalizedMax = max - min;

    if (normalizedValue == 0 || normalizedMax == 0) {
      return 0;
    }

    return normalizedValue / normalizedMax;
  }

  private static MethodWithRisk calculateRiskScore(MetricsData normalizedMetricsData) {
    Map<MetricType, Double> classMetricsNameToValueMap =
        normalizedMetricsData.getClassMetrics().stream()
            .collect(Collectors.toMap(MetricMeasurement::getType, MetricMeasurement::getValue));
    Map<MetricType, Double> methodMetricsNameToValueMap =
        normalizedMetricsData.getMethodMetrics().stream()
            .collect(Collectors.toMap(MetricMeasurement::getType, MetricMeasurement::getValue));

    double classRiskValue =
        (0.3 * classMetricsNameToValueMap.get(ClassMetricType.REVS))
            + (0.2 * classMetricsNameToValueMap.get(ClassMetricType.LOC))
            + (0.2 * classMetricsNameToValueMap.get(ClassMetricType.NOM))
            + (0.2 * classMetricsNameToValueMap.get(ClassMetricType.WMC))
            + (0.1 * classMetricsNameToValueMap.get(ClassMetricType.CBO));

    double W1 = 0.3;
    double W2 = 0.2;
    double W3 = 0.2;
    double W4 = 0.2;
    double W5 = 0.1;

    double methodRiskValue =
        (W1 * methodMetricsNameToValueMap.get(MethodMetricType.WMC))
            + (W2 * methodMetricsNameToValueMap.get(MethodMetricType.CBO))
            + (W3 * methodMetricsNameToValueMap.get(MethodMetricType.RFC))
            + (W4 * methodMetricsNameToValueMap.get(MethodMetricType.FOUT))
            + (W5 * methodMetricsNameToValueMap.get(MethodMetricType.FIN));

    double weightedMethodRisk = (0.3 * classRiskValue) + (0.7 * methodRiskValue);

    return MethodWithRisk.of(normalizedMetricsData.getMethodDescriptor(), weightedMethodRisk);
  }

}
