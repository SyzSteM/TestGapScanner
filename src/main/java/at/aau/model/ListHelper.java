package at.aau.model;

import java.util.Collections;
import java.util.List;
import org.jetbrains.annotations.Nullable;

public final class ListHelper {
  private ListHelper() {}

  public static <T> List<T> unmodifiableList(@Nullable List<T> list) {
    if (list == null) {
      return Collections.emptyList();
    }

    return Collections.unmodifiableList(list);
  }
}
