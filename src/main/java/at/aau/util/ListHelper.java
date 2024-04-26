package at.aau.util;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;
import org.jetbrains.annotations.Nullable;

public final class ListHelper {
  private ListHelper() {
    throw new UnsupportedOperationException("Utility class");
  }

  public static boolean listNotNullOrEmpty(List<?> list) {
    return Optional.ofNullable(list).filter(Predicate.not(List::isEmpty)).isPresent();
  }
  public static <T> List<T> unmodifiableList(@Nullable List<T> list) {
    if (list == null) {
      return Collections.emptyList();
    }

    return Collections.unmodifiableList(list);
  }
}
