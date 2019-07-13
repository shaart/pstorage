package shaart.pstorage.converter.impl;

import static java.util.Objects.isNull;

import org.springframework.stereotype.Component;
import shaart.pstorage.converter.PasswordConverter;
import shaart.pstorage.dto.PasswordDto;
import shaart.pstorage.entity.Password;

@Component
public class PasswordConverterImpl implements PasswordConverter {

  @Override
  public Password convert(PasswordDto passwordDto) {
    final Long id = isNull(passwordDto.getId()) ? null : Long.valueOf(passwordDto.getId());

    return Password.builder()
        .id(id)
        .alias(passwordDto.getAlias())
        .encryptedValue(passwordDto.getEncryptedValue())
        .build();
  }

  @Override
  public PasswordDto convert(Password savedPassword) {
    final String id = isNull(savedPassword.getId()) ? "" : savedPassword.getId().toString();

    return PasswordDto.builder()
        .id(id)
        .alias(savedPassword.getAlias())
        .encryptedValue(savedPassword.getEncryptedValue())
        .build();
  }
}
