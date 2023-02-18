package shaart.pstorage.util;

import static java.util.Objects.isNull;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.UUID;
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
   * Convert {@link String} to {@link UUID}.
   *
   * @param value initial value
   * @return value or null if value is received null
   */
  public UUID asUuidOrNull(String value) {
    return isNull(value) ? null : UUID.fromString(value);
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
   * Convert {@link UUID} to {@link String}.
   *
   * @param value initial value
   * @return value or empty string if value is null
   */
  public String asStringOrEmpty(UUID value) {
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
