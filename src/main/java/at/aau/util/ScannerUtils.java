package at.aau.util;

import java.nio.file.Path;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.apache.commons.collections4.CollectionUtils;

import at.aau.exception.ParserException;
import at.aau.jacoco.JacocoCoverageCollector;
import at.aau.jacoco.JacocoCoverageFilter;
import at.aau.jacoco.model.Method;
import at.aau.jacoco.model.Package;
import at.aau.metrics.CkMetricCollector;
import at.aau.metrics.MethodMatcher;
import at.aau.metrics.MetricUtils;
import at.aau.metrics.RiskMetricCalculator;
import at.aau.model.ClassMetricType;
import at.aau.model.MethodWithRisk;
import at.aau.model.MetricsData;

public class ScannerUtils {

  private ScannerUtils() {
    throw new UnsupportedOperationException("Utility class");
  }


  public static List<Method> getUntestedMethods(Path coverageReport) throws ParserException {
    List<Package> metrics = JacocoCoverageCollector.collect(coverageReport);

    return JacocoCoverageFilter.getUntestedMethods(metrics);
  }

  public static List<MethodWithRisk> calculateMethodRiskScores(
      Path projectBaseDirPath,
      List<Method> untestedMethods,
      Path maatRevisions,
      Path maatSoc
  ) {
    List<MetricsData> methodMetricsData = CkMetricCollector.collectCkMetrics(projectBaseDirPath);
    List<MetricsData> untestedMethodsMetricsData =
        MetricUtils.getUntestedMethodMetrics(untestedMethods, methodMetricsData);

    Map<String, List<MetricsData>> classNameToMetricsDataMap = untestedMethodsMetricsData.stream()
        .collect(Collectors.groupingBy(MetricsData::getClassName, Collectors.toList()));

    addCodeMaatClassMetrics(classNameToMetricsDataMap, maatRevisions, ClassMetricType.REVS);
    addCodeMaatClassMetrics(classNameToMetricsDataMap, maatSoc, ClassMetricType.SOC);

    List<MetricsData> normalizedMetricsData = RiskMetricCalculator.normalizeMetricsData(untestedMethodsMetricsData);

    return RiskMetricCalculator.getDescendingMethodRiskScores(normalizedMetricsData);
  }

  private static void addCodeMaatClassMetrics(
      Map<String, List<MetricsData>> classNameToMetricsDataMap,
      Path maatPath,
      ClassMetricType maatMetricType
  ) {
    List<MetricsData> codeMaatMetrics = MethodMatcher.methodDescriptorFromMaat(maatPath, maatMetricType);

    for (MetricsData codeMaatMetric : codeMaatMetrics) {
      List<MetricsData> mapEntries = classNameToMetricsDataMap.get(codeMaatMetric.getClassName());

      if (CollectionUtils.isNotEmpty(mapEntries)) {
        MetricsData firstEntry = mapEntries.get(0);

        firstEntry.addClassMetrics(codeMaatMetric.getClassMetrics());
      }
    }
  }

}
