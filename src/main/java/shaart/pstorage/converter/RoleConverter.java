package shaart.pstorage.converter;

import shaart.pstorage.dto.RoleDto;
import shaart.pstorage.entity.Role;

public interface RoleConverter {

  Role toEntity(RoleDto roleDto);

  RoleDto toDto(Role roleEntity);
}
