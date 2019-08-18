package shaart.pstorage.service;

import java.util.List;
import java.util.Optional;
import shaart.pstorage.dto.RoleDto;

public interface RoleService {

  void delete(RoleDto roleDto);

  boolean exists(String roleName);

  List<RoleDto> findAll();

  Optional<RoleDto> findByName(String roleName);

  RoleDto save(RoleDto roleDto);

  RoleDto findUserDefault();
}
