package shaart.pstorage.service.impl;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import javax.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import shaart.pstorage.converter.PasswordConverter;
import shaart.pstorage.dto.PasswordDto;
import shaart.pstorage.dto.UserDto;
import shaart.pstorage.entity.Password;
import shaart.pstorage.enumeration.RoleType;
import shaart.pstorage.exception.UnauthorizedException;
import shaart.pstorage.repository.PasswordRepository;
import shaart.pstorage.service.PasswordService;
import shaart.pstorage.service.SecurityAwareService;

@Service
@Transactional
@AllArgsConstructor
public class PasswordServiceImpl implements PasswordService {

  private final PasswordRepository repository;
  private final PasswordConverter passwordConverter;
  private final SecurityAwareService securityAwareService;

  @Override
  public List<PasswordDto> findAll() {
    final Optional<UserDto> contextUser = securityAwareService.currentUser();
    if (!contextUser.isPresent()) {
      throw new UnauthorizedException("Can't load user's passwords because not authorized");
    }

    final UserDto currentUser = contextUser.get();
    if (RoleType.ADMIN.name().equals(currentUser.getRole().getName())) {
      return repository.findAll().stream()
          .map(passwordConverter::toDto)
          .collect(Collectors.toList());
    }
    return findAllByUser(currentUser.getName());
  }

  @Override
  public List<PasswordDto> findAllByUser(String username) {
    return repository.findAllByUserName(username).stream()
        .map(passwordConverter::toDto)
        .collect(Collectors.toList());
  }

  @Override
  public PasswordDto save(PasswordDto passwordDto) {
    Password password = passwordConverter.toEntity(passwordDto);
    final Password savedPassword = repository.save(password);
    return passwordConverter.toDto(savedPassword);
  }
}