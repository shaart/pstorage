package shaart.pstorage.service.impl;

import jakarta.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.function.Supplier;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;
import shaart.pstorage.converter.PasswordConverter;
import shaart.pstorage.dto.PasswordDto;
import shaart.pstorage.dto.UserDto;
import shaart.pstorage.entity.Password;
import shaart.pstorage.enumeration.EncryptionType;
import shaart.pstorage.enumeration.RoleType;
import shaart.pstorage.exception.PasswordNotFoundException;
import shaart.pstorage.exception.UnauthorizedException;
import shaart.pstorage.repository.PasswordRepository;
import shaart.pstorage.service.PasswordService;

@Slf4j
@Service
@Transactional
@AllArgsConstructor
public class PasswordServiceImpl implements PasswordService {

  private final PasswordRepository repository;
  private final PasswordConverter passwordConverter;

  @Override
  public List<PasswordDto> findAll(Supplier<Optional<UserDto>> currentUserSupplier) {
    final Optional<UserDto> contextUser = currentUserSupplier.get();
    if (contextUser.isEmpty()) {
      throw new UnauthorizedException("Can't load user's passwords because not authorized");
    }

    final UserDto currentUser = contextUser.get();
    if (RoleType.ADMIN.name().equals(currentUser.getRole().getName())) {
      return repository.findAll().stream()
          .map(passwordConverter::toDto)
          .toList();
    }
    return findAllByUser(currentUser.getName());
  }

  @Override
  public List<PasswordDto> findAllByUser(String userName) {
    return repository.findAllByUserName(userName).stream()
        .map(passwordConverter::toDto)
        .toList();
  }

  @Override
  public PasswordDto save(PasswordDto passwordDto) {
    Password password = passwordConverter.toEntity(passwordDto);
    final Password savedPassword = repository.save(password);

    return passwordConverter.toDto(savedPassword);
  }

  @Override
  public List<PasswordDto> findFavoritesByUser(String userName) {
    //TODO 19.08.2019 00:41 add table for user-favorite-passwords and change logic here
    return repository.findAllByUserName(userName).stream()
        .map(passwordConverter::toDto)
        .toList();
  }

  @Override
  public void updateAlias(String passwordId, String newAlias) {
    log.info("Updating alias of password with id = '{}' to alias '{}'", passwordId, newAlias);
    final Password foundPassword = findPasswordById(passwordId);
    foundPassword.setAlias(newAlias);
    repository.save(foundPassword);
  }

  @Override
  public void updatePassword(String passwordId,
      EncryptionType encryptionType, String newEncryptedValue) {
    log.info("Updating value of password with id = '{}' to new", passwordId);
    final Password foundPassword = findPasswordById(passwordId);
    foundPassword.setEncryptionType(encryptionType);
    foundPassword.setEncryptedValue(newEncryptedValue);
    repository.save(foundPassword);
  }

  @Override
  public void deleteById(@NonNull String passwordId) {
    repository.deleteById(UUID.fromString(passwordId));
  }

  /**
   * Finds password by id.
   *
   * @param passwordId ID
   * @return password entity
   * @throws PasswordNotFoundException if password with provided id wasn't found in database
   */
  private Password findPasswordById(String passwordId) {
    final Optional<Password> password = repository.findById(UUID.fromString(passwordId));
    if (password.isEmpty()) {
      final String message = String.format("Password with id = '%s' not found", passwordId);
      throw new PasswordNotFoundException(message);
    }
    return password.get();
  }
}