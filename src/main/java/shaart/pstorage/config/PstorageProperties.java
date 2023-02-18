package shaart.pstorage.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Getter
@Setter
@SuppressWarnings("WeakerAccess")
@ConfigurationProperties(prefix = "pstorage")
public class PstorageProperties {

  private Validation validation;
  private Ui ui;
  private Aes aes;

  @Getter
  @Setter
  public static class Ui {
    private String title;
  }

  @Getter
  @Setter
  public static class Validation {

    Password password;
    Username username;
  }

  @Getter
  @Setter
  public static class Password {

    Password.Length length;

    @Getter
    @Setter
    public static class Length {

      private Integer min;
      private Integer max;
    }
  }

  @Getter
  @Setter
  public static class Username {

    Username.Length length;

    @Getter
    @Setter
    public static class Length {

      private Integer max;

    }
  }

  @Getter
  @Setter
  public static class Aes {

    Common common;

    @Getter
    @Setter
    public static class Common {

      private String key;
      private String vector;
    }
  }
}
