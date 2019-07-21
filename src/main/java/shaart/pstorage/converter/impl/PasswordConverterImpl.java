package shaart.pstorage.converter.impl;

import static java.util.Objects.isNull;

import org.springframework.stereotype.Component;
import shaart.pstorage.converter.PasswordConverter;
import shaart.pstorage.dto.PasswordDto;
import shaart.pstorage.entity.Password;

@Component
public class PasswordConverterImpl implements PasswordConverter {

  @Override
  public Password toEntity(PasswordDto passwordDto) {
    final Integer id = isNull(passwordDto.getId()) ? null : Integer.valueOf(passwordDto.getId());

    return Password.builder()
        .id(id)
        .alias(passwordDto.getAlias())
        .value(passwordDto.getEncryptedValue())
        .build();
  }

  @Override
  public PasswordDto toDto(Password savedPassword) {
    final String id = isNull(savedPassword.getId()) ? "" : savedPassword.getId().toString();

    return PasswordDto.builder()
        .id(id)
        .alias(savedPassword.getAlias())
        .encryptedValue(savedPassword.getValue())
        .build();
  }
}
