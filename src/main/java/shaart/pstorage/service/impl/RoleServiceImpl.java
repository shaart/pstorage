package shaart.pstorage.service.impl;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import javax.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import shaart.pstorage.converter.RoleConverter;
import shaart.pstorage.dto.RoleDto;
import shaart.pstorage.entity.Role;
import shaart.pstorage.enumeration.RoleType;
import shaart.pstorage.repository.RoleRepository;
import shaart.pstorage.service.RoleService;

@Service
@Transactional
@RequiredArgsConstructor
public class RoleServiceImpl implements RoleService {

  private final RoleRepository repository;
  private final RoleConverter roleConverter;

  @Override
  public void delete(RoleDto roleDto) {
    Role role = roleConverter.toEntity(roleDto);
    repository.delete(role);
  }

  @Override
  public boolean exists(String roleName) {
    return repository.existsByName(roleName);
  }

  @Override
  public List<RoleDto> findAll() {
    return repository.findAll().stream()
        .map(roleConverter::toDto)
        .collect(Collectors.toList());
  }

  @Override
  public Optional<RoleDto> findByName(String roleName) {
    return repository.findByName(roleName)
        .map(roleConverter::toDto);
  }

  @Override
  public RoleDto save(RoleDto roleDto) {
    Role role = roleConverter.toEntity(roleDto);
    final Role savedRole = repository.save(role);
    return roleConverter.toDto(savedRole);
  }

  @Override
  public RoleDto findUserDefault() {
    return repository.findByName(RoleType.USER.name())
        .map(roleConverter::toDto)
        .orElseThrow(() -> new IllegalStateException("Default role USER not found"));
  }
}