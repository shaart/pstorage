package shaart.pstorage.converter.impl;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import shaart.pstorage.converter.PasswordConverter;
import shaart.pstorage.converter.UserConverter;
import shaart.pstorage.dto.PasswordDto;
import shaart.pstorage.dto.UserDto;
import shaart.pstorage.entity.Password;
import shaart.pstorage.entity.User;
import shaart.pstorage.util.OperationUtil;

@Component
@AllArgsConstructor
public class PasswordConverterImpl implements PasswordConverter {

  private final UserConverter userConverter;
  private final OperationUtil operationUtil;

  @Override
  public Password toEntity(PasswordDto passwordDto) {
    final Integer id = operationUtil.asInteger(passwordDto.getId());
    final Timestamp createdAt = operationUtil.asTimestamp(passwordDto.getCreatedAt());
    final User user = userConverter.toEntity(passwordDto.getUser());

    return Password.builder()
        .id(id)
        .user(user)
        .alias(passwordDto.getAlias())
        .value(passwordDto.getEncryptedValue())
        .createdAt(createdAt)
        .build();
  }

  @Override
  public PasswordDto toDto(Password passwordEntity) {
    final String id = operationUtil.asString(passwordEntity.getId());
    final LocalDateTime createdAt = operationUtil.asDateTime(passwordEntity.getCreatedAt());
    final UserDto userDto = userConverter.toDto(passwordEntity.getUser());

    return PasswordDto.builder()
        .id(id)
        .user(userDto)
        .alias(passwordEntity.getAlias())
        .encryptedValue(passwordEntity.getValue())
        .createdAt(createdAt)
        .build();
  }
}
