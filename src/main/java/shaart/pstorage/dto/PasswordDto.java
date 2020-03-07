package shaart.pstorage.dto;

import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import shaart.pstorage.enumeration.EncryptionType;

@Getter
@Setter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class PasswordDto {

  private String id;

  private UserDto user;

  private String alias;

  private EncryptionType encryptionType;

  @ToString.Exclude
  private String encryptedValue;

  @ToString.Exclude
  private final String showedEncryptedValue = "***";

  private LocalDateTime createdAt;
}