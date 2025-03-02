package at.aau.util;

import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Predicate;

import org.jetbrains.annotations.Nullable;

/**
 * Provides utility methods for operations on {@code List} objects. This class includes methods to check non-null or
 * non-empty states of lists and to create an unmodifiable version of a list.
 *
 * <p>It is designed as a final class with a private constructor to prevent instantiation,
 * indicating usage strictly as a utility class.
 */
public final class ListUtils {

  private ListUtils() {
    throw new UnsupportedOperationException("Utility class");
  }

  /**
   * Checks if the provided list is neither {@code null} nor empty.
   *
   * <p>This method utilizes {@link Optional} and {@link Predicate} to determine if the list is
   * present and non-empty, ensuring a safe check without risk of {@code NullPointerException}.
   *
   * @param list the list to check; can be {@code null}
   * @return {@code true} if the list is both non-null and not empty, {@code false} otherwise
   */
  public static boolean notNullOrEmpty(List<?> list) {
    return Optional.ofNullable(list).filter(Predicate.not(List::isEmpty)).isPresent();
  }

  /**
   * Creates an unmodifiable version of the provided list. If the original list is {@code null} or contains {@code null}
   * elements, this method returns an empty list instead.
   *
   * <p>This ensures that the returned list is safe to use without further null-checks or
   * modifications, adhering to the principle of immutability in collections.
   *
   * @param <T>  the type of elements in the list
   * @param list the original list that may contain {@code null} elements or itself be {@code null}
   * @return an unmodifiable copy of the original list if it's non-null and contains no {@code null} elements;
   *     otherwise, returns an immutable empty list
   */
  public static <T> List<T> unmodifiableList(@Nullable List<T> list) {
    if (list == null) {
      return Collections.emptyList();
    }

    if (list.stream().anyMatch(Objects::isNull)) {
      return Collections.emptyList();
    }

    return List.copyOf(list);
  }

}
