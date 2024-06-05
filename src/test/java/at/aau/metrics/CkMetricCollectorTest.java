package at.aau.metrics;

import static org.assertj.core.api.Assertions.assertThat;

import at.aau.model.ClassWithRisk;
import java.nio.file.Paths;
import java.util.Comparator;
import java.util.stream.Collectors;
import org.junit.jupiter.api.Test;

class CkMetricCollectorTest {

//  @Test
//  void shouldReturnClassMetrics_whenPathIsValid() {
//    var classPath = Paths.get("/home/timo/Development/plincs/basf");
//    var metrics = CkMetricCollector.collectMetrics(classPath);
//
//    var classWithRisks =
//        RiskMetricCalculator.getNormalizedClassMetrics(metrics).stream()
//            .map(RiskMetricCalculator::calculateRiskScore)
//            .sorted(Comparator.comparingDouble(ClassWithRisk::getRisk).reversed())
//            .collect(Collectors.toList());
//
//    classWithRisks.forEach(System.out::println);
//
//    assertThat(metrics).isNotEmpty();
//  }
}
