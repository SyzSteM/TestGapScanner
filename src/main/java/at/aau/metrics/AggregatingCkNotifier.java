package at.aau.metrics;

import com.github.mauricioaniche.ck.CKClassResult;
import com.github.mauricioaniche.ck.CKNotifier;
import java.util.ArrayList;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

class AggregatingCkNotifier implements CKNotifier {
  private static final Logger LOGGER = LoggerFactory.getLogger(AggregatingCkNotifier.class);

  private final List<CKClassResult> ckResults = new ArrayList<>();

  @Override
  public void notify(CKClassResult result) {
    ckResults.add(result);
  }

  @Override
  public void notifyError(String sourceFilePath, Exception e) {
    LOGGER.error("Error in {}", sourceFilePath, e);
  }

  public List<CKClassResult> getCkResults() {
    return List.copyOf(ckResults);
  }
}
