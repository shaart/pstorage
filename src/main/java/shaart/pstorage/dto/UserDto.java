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
public class UserDto {

  private String id;

  private String name;

  @ToString.Exclude
  private String masterPassword;

  private EncryptionType encryptionType;

  private RoleDto role;

  private LocalDateTime createdAt;
}