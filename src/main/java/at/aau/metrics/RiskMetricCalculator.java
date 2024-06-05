package at.aau.metrics;

import at.aau.model.ClassMetrics;
import at.aau.model.ClassWithRisk;
import at.aau.model.MetricMeasurement;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.DoubleSummaryStatistics;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public final class RiskMetricCalculator {

  private RiskMetricCalculator() {
    throw new UnsupportedOperationException("Utility class");
  }

  public static List<ClassWithRisk> getDescendingClassRiskScores(List<ClassMetrics> classMetrics) {
    return getNormalizedClassMetrics(classMetrics).stream()
        .map(RiskMetricCalculator::calculateRiskScore)
        .sorted(sortByRiskDescending())
        .collect(Collectors.toList());
  }

  private static Comparator<ClassWithRisk> sortByRiskDescending() {
    return Comparator.comparingDouble(ClassWithRisk::getRisk).reversed();
  }

  private static List<ClassMetrics> getNormalizedClassMetrics(List<ClassMetrics> metrics) {
    Map<String, DoubleSummaryStatistics> summary =
        metrics.stream()
            .flatMap(m -> m.getClassMetrics().stream())
            .collect(
                Collectors.groupingBy(
                    MetricMeasurement::getMetricName,
                    Collectors.summarizingDouble(MetricMeasurement::getValue)));

    List<ClassMetrics> normalizedClassMetrics = new ArrayList<>();
    for (ClassMetrics classMetric : metrics) {
      List<MetricMeasurement> normalizedMetrics =
          classMetric.getClassMetrics().stream()
              .map(
                  m ->
                      MetricMeasurement.of(
                          m.getMetricName(),
                          normalize(
                              m.getValue(),
                              summary.get(m.getMetricName()).getMin(),
                              summary.get(m.getMetricName()).getMax())))
              .collect(Collectors.toList());

      normalizedClassMetrics.add(
          ClassMetrics.of(classMetric.getClassName(), normalizedMetrics, null));
    }

    return normalizedClassMetrics;
  }

  private static double normalize(double value, double min, double max) {
    return (value - min) / (max - min);
  }

  private static ClassWithRisk calculateRiskScore(ClassMetrics normalizedClassMetrics) {
    double normalizedWmc =
        normalizedClassMetrics.getClassMetrics().stream()
            .filter(m -> m.getMetricName().equalsIgnoreCase("wmc"))
            .findFirst()
            .map(MetricMeasurement::getValue)
            .orElse(0.0);
    double normalizedCbo =
        normalizedClassMetrics.getClassMetrics().stream()
            .filter(m -> m.getMetricName().equalsIgnoreCase("CBO"))
            .findFirst()
            .map(MetricMeasurement::getValue)
            .orElse(0.0);
    double normalizedRfc =
        normalizedClassMetrics.getClassMetrics().stream()
            .filter(m -> m.getMetricName().equalsIgnoreCase("RFC"))
            .findFirst()
            .map(MetricMeasurement::getValue)
            .orElse(0.0);
    double normalizedDit =
        normalizedClassMetrics.getClassMetrics().stream()
            .filter(m -> m.getMetricName().equalsIgnoreCase("DIT"))
            .findFirst()
            .map(MetricMeasurement::getValue)
            .orElse(0.0);
    double normalizedLoc =
        normalizedClassMetrics.getClassMetrics().stream()
            .filter(m -> m.getMetricName().equalsIgnoreCase("LOC"))
            .findFirst()
            .map(MetricMeasurement::getValue)
            .orElse(0.0);

    double W1 = 0.4;
    double W2 = 0.3;
    double W3 = 0.1;
    double W4 = 0.1;
    double W5 = 0.1;

    double riskValue =
        (W1 * normalizedWmc)
            + (W2 * normalizedCbo)
            + (W3 * normalizedRfc)
            + (W4 * normalizedDit)
            + (W5 * normalizedLoc);

    return ClassWithRisk.of(normalizedClassMetrics.getClassName(), riskValue);
  }
}
