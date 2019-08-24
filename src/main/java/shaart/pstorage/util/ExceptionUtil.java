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
  public String getStacktrace(Throwable e) {
    StringWriter stringWriter = new StringWriter();
    PrintWriter printWriter = new PrintWriter(stringWriter);
    e.printStackTrace(printWriter);

    return stringWriter.toString();
  }

  /**
   * Get exception's initial location in format FILENAME:LINE.
   *
   * @param e an exception
   * @return string with filename and line of the exception
   */
  public String getLocation(Throwable e) {
    StackTraceElement traceElement = e.getStackTrace()[0];
    return traceElement.getFileName() + ":" + traceElement.getLineNumber();
  }
}
