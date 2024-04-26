package at.aau.util;

import java.nio.file.Files;
import java.nio.file.Path;

public final class PathHelper {
  private PathHelper() {
    throw new UnsupportedOperationException("Utility class");
  }

  public static boolean isValidDirectory(Path path) {
    if (path == null) {
      return false;
    }

    return Files.exists(path) && Files.isDirectory(path);
  }
}
