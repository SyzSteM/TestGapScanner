package at.aau.util;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import org.apache.commons.io.file.AccumulatorPathVisitor;
import org.apache.commons.io.filefilter.FileFilterUtils;
import org.apache.maven.plugin.MojoFailureException;

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

  public static void walkFileTree(Path path) throws MojoFailureException {
    AccumulatorPathVisitor visitor =
        AccumulatorPathVisitor.withLongCounters(
            FileFilterUtils.suffixFileFilter(".java"), FileFilterUtils.trueFileFilter());

    try {
      Files.walkFileTree(path, visitor);
    } catch (IOException e) {
      throw new MojoFailureException("Failed to walk output directory " + path, e);
    }
  }
}
