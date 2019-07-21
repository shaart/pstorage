package shaart.pstorage.service.impl;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;
import javax.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import shaart.pstorage.converter.UserConverter;
import shaart.pstorage.dto.UserDto;
import shaart.pstorage.entity.User;
import shaart.pstorage.exception.UserNotFoundException;
import shaart.pstorage.repository.UserRepository;
import shaart.pstorage.service.UserService;

@Service
@Transactional
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

  private final UserRepository repository;
  private final UserConverter userConverter;

  @Override
  public void delete(UserDto userDto) {
    User user = userConverter.toEntity(userDto);
    repository.delete(user);
  }

  @Override
  public List<UserDto> findAll() {
    return repository.findAll().stream()
        .map(userConverter::toDto)
        .collect(Collectors.toList());
  }

  @Override
  public Optional<UserDto> findByName(String username) {
    Optional<User> user = repository.findByName(username);
    if (!user.isPresent()) {
      return Optional.empty();
    }

    User foundUser = user.get();
    return Optional.ofNullable(userConverter.toDto(foundUser));
  }

  @Override
  public boolean isCorrectPasswordFor(String username, String password) {
    Optional<User> user = repository.findByName(username);
    if (!user.isPresent()) {
      throw new UserNotFoundException();
    }
    User foundUser = user.get();

    return Objects.equals(foundUser.getMasterPassword(), password);
  }

  @Override
  public UserDto save(UserDto userDto) {
    User user = userConverter.toEntity(userDto);
    final User savedUser = repository.save(user);
    return userConverter.toDto(savedUser);
  }
}