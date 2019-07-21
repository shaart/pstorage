package shaart.pstorage.service;

import java.util.List;
import java.util.Optional;
import shaart.pstorage.dto.UserDto;

public interface UserService {

  void delete(UserDto userDto);

  List<UserDto> findAll();

  Optional<UserDto> findByName(String username);

  boolean isCorrectPasswordFor(String username, String password);

  UserDto save(UserDto user);
}