package shaart.pstorage.converter.impl;

import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import shaart.pstorage.converter.PasswordConverter;
import shaart.pstorage.converter.UserConverter;
import shaart.pstorage.dto.PasswordDto;
import shaart.pstorage.dto.UserDto;
import shaart.pstorage.entity.Password;
import shaart.pstorage.util.OperationUtil;

@Component
@AllArgsConstructor
public class PasswordConverterImpl implements PasswordConverter {

  private final UserConverter userConverter;
  private final OperationUtil operationUtil;

  @Override
  public Password toEntity(PasswordDto passwordDto) {
    var id = operationUtil.asUuidOrNull(passwordDto.getId());
    var createdAt = operationUtil.asTimestampOrNull(passwordDto.getCreatedAt());
    var user = userConverter.toEntity(passwordDto.getUser());

    return Password.builder()
        .id(id)
        .user(user)
        .alias(passwordDto.getAlias())
        .encryptionType(passwordDto.getEncryptionType())
        .encryptedValue(passwordDto.getEncryptedValue())
        .createdAt(createdAt)
        .build();
  }

  @Override
  public PasswordDto toDto(Password passwordEntity) {
    final String id = operationUtil.asStringOrEmpty(passwordEntity.getId());
    final LocalDateTime createdAt = operationUtil.asDteTimeOrNull(passwordEntity.getCreatedAt());
    final UserDto userDto = userConverter.toDto(passwordEntity.getUser());

    return PasswordDto.builder()
        .id(id)
        .user(userDto)
        .alias(passwordEntity.getAlias())
        .encryptionType(passwordEntity.getEncryptionType())
        .encryptedValue(passwordEntity.getEncryptedValue())
        .createdAt(createdAt)
        .build();
  }
}
