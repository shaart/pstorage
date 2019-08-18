package shaart.pstorage.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import shaart.pstorage.enumeration.EncryptionType;

@Getter
@Setter
@Builder
@AllArgsConstructor
public class CryptoResult {

  private String value;
  private EncryptionType encryptionType;
}
