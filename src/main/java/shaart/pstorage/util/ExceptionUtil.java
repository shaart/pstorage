package shaart.pstorage.util;

import java.io.PrintWriter;
import java.io.StringWriter;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

/**
 * Util class for working with exception.
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public enum ExceptionUtil {
  INSTANCE;

  public static ExceptionUtil getInstance() {
    return INSTANCE;
  }

  /**
   * Get exception's stacktrace as string.
   *
   * @param e an exception
   * @return string with stacktrace
   */
  public String getStacktrace(Exception e) {
    StringWriter stringWriter = new StringWriter();
    PrintWriter printWriter = new PrintWriter(stringWriter);
    e.printStackTrace(printWriter);

    return stringWriter.toString();
  }
}
