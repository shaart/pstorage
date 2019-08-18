package shaart.pstorage.util;

import static java.util.Objects.isNull;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

/**
 * Util class for converting values.
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public enum OperationUtil {
  INSTANCE;

  public static OperationUtil getInstance() {
    return INSTANCE;
  }

  /**
   * Convert {@link String} to {@link Integer}.
   *
   * @param value initial number value
   * @return value or null if value is null
   */
  public Integer asIntegerOrNull(String value) {
    return isNull(value) ? null : Integer.valueOf(value);
  }

  /**
   * Convert {@link LocalDateTime} to {@link Timestamp}.
   *
   * @param value initial value
   * @return value or null if value is null
   */
  public Timestamp asTimestampOrNull(LocalDateTime value) {
    return isNull(value) ? null : Timestamp.valueOf(value);
  }

  /**
   * Convert {@link Integer} to {@link String}.
   *
   * @param value initial {@link LocalDateTime}
   * @return value or empty string if value is null
   */
  public String asStringOrEmpty(Integer value) {
    return isNull(value) ? "" : value.toString();
  }

  /**
   * Convert {@link Timestamp} to {@link LocalDateTime}.
   *
   * @param value initial value
   * @return value or null if value is null
   */
  public LocalDateTime asDteTimeOrNull(Timestamp value) {
    return isNull(value) ? null : value.toLocalDateTime();
  }
}
