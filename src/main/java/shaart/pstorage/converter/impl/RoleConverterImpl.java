package shaart.pstorage.converter.impl;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;
import shaart.pstorage.converter.RoleConverter;
import shaart.pstorage.dto.RoleDto;
import shaart.pstorage.entity.Role;
import shaart.pstorage.util.OperationUtil;

@Component
public class RoleConverterImpl implements RoleConverter {

  private OperationUtil operationUtil;

  @Autowired
  public void setOperationUtil(OperationUtil operationUtil) {
    this.operationUtil = operationUtil;
  }

  @Override
  public Role toEntity(@NonNull RoleDto roleDto) {
    final Integer id = operationUtil.asIntegerOrNull(roleDto.getId());
    final Timestamp createdAt = operationUtil.asTimestampOrNull(roleDto.getCreatedAt());

    return Role.builder()
        .id(id)
        .name(roleDto.getName())
        .createdAt(createdAt)
        .build();
  }

  @Override
  public RoleDto toDto(@NonNull Role roleEntity) {
    final String id = operationUtil.asStringOrEmpty(roleEntity.getId());
    final LocalDateTime createdAt = operationUtil.asDteTimeOrNull(roleEntity.getCreatedAt());

    return RoleDto.builder()
        .id(id)
        .name(roleEntity.getName())
        .createdAt(createdAt)
        .build();
  }
}
