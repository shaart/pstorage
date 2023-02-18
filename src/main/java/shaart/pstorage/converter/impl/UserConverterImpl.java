package shaart.pstorage.converter.impl;

import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;
import shaart.pstorage.converter.RoleConverter;
import shaart.pstorage.converter.UserConverter;
import shaart.pstorage.dto.RoleDto;
import shaart.pstorage.dto.UserDto;
import shaart.pstorage.entity.User;
import shaart.pstorage.util.OperationUtil;

@Component
@AllArgsConstructor
public class UserConverterImpl implements UserConverter {

  private final OperationUtil operationUtil;
  private final RoleConverter roleConverter;

  @Override
  public User toEntity(@NonNull UserDto userDto) {
    var id = operationUtil.asUuidOrNull(userDto.getId());
    var createdAt = operationUtil.asTimestampOrNull(userDto.getCreatedAt());
    var role = roleConverter.toEntity(userDto.getRole());

    return User.builder()
        .id(id)
        .name(userDto.getName())
        .masterPassword(userDto.getMasterPassword())
        .encryptionType(userDto.getEncryptionType())
        .role(role)
        .createdAt(createdAt)
        .build();
  }

  @Override
  public UserDto toDto(@NonNull User userEntity) {
    final String id = operationUtil.asStringOrEmpty(userEntity.getId());
    final LocalDateTime createdAt = operationUtil.asDteTimeOrNull(userEntity.getCreatedAt());
    final RoleDto roleDto = roleConverter.toDto(userEntity.getRole());

    return UserDto.builder()
        .id(id)
        .name(userEntity.getName())
        .masterPassword(userEntity.getMasterPassword())
        .encryptionType(userEntity.getEncryptionType())
        .role(roleDto)
        .createdAt(createdAt)
        .build();
  }
}
