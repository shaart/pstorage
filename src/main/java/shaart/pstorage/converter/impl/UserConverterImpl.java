package shaart.pstorage.converter.impl;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import shaart.pstorage.converter.UserConverter;
import shaart.pstorage.dto.UserDto;
import shaart.pstorage.entity.User;
import shaart.pstorage.util.OperationUtil;

@Component
public class UserConverterImpl implements UserConverter {

  private OperationUtil operationUtil;

  @Autowired
  public void setOperationUtil(OperationUtil operationUtil) {
    this.operationUtil = operationUtil;
  }

  @Override
  public User toEntity(UserDto userDto) {
    final Integer id = operationUtil.asInteger(userDto.getId());
    final Timestamp createdAt = operationUtil.asTimestamp(userDto.getCreatedAt());

    return User.builder()
        .id(id)
        .name(userDto.getName())
        .masterPassword(userDto.getMasterPassword())
        .createdAt(createdAt)
        .build();
  }

  @Override
  public UserDto toDto(User userEntity) {
    final String id = operationUtil.asString(userEntity.getId());
    final LocalDateTime createdAt = operationUtil.asDateTime(userEntity.getCreatedAt());

    return UserDto.builder()
        .id(id)
        .name(userEntity.getName())
        .masterPassword(userEntity.getMasterPassword())
        .createdAt(createdAt)
        .build();
  }
}
