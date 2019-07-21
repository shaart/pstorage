package shaart.pstorage.util;

import static java.util.Objects.isNull;

import java.sql.Timestamp;
import java.time.LocalDateTime;

public enum OperationUtil {
  INSTANCE;

  public static OperationUtil getInstance() {
    return INSTANCE;
  }

  public Integer asInteger(String value) {
    return isNull(value) ? null : Integer.valueOf(value);
  }

  public Timestamp asTimestamp(LocalDateTime createdAt) {
    return isNull(createdAt) ? null : Timestamp.valueOf(createdAt);
  }

  public String asString(Integer value) {
    return isNull(value) ? "" : value.toString();
  }

  public LocalDateTime asDateTime(Timestamp createdAt) {
    return isNull(createdAt) ? null : createdAt.toLocalDateTime();
  }
}
