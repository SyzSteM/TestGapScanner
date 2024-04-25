package at.aau.metrics;

import com.github.mauricioaniche.ck.CK;
import com.github.mauricioaniche.ck.CKClassResult;
import java.nio.file.Path;
import java.util.List;

public final class CkMetricCollector {
  private CkMetricCollector() {
    throw new UnsupportedOperationException("Utility class");
  }

  public static List<CKClassResult> collect(Path path) {
    AggregatingCkNotifier ckNotifier = new AggregatingCkNotifier();

    new CK().calculate(path, ckNotifier);

    return ckNotifier.getCkResults();
  }
}
