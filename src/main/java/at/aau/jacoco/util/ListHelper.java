package at.aau.jacoco.util;

import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;

public final class ListHelper {
  private ListHelper() {
    throw new UnsupportedOperationException("Utility class");
  }

  public static boolean listNotNullOrEmpty(List<?> list) {
    return Optional.ofNullable(list).filter(Predicate.not(List::isEmpty)).isPresent();
  }
}
