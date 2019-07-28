package shaart.pstorage.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class CryptoDto {

  private String value;

  public static CryptoDto of(String value) {
    return new CryptoDto(value);
  }
}
