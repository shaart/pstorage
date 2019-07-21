package shaart.pstorage.service;

import java.util.List;
import shaart.pstorage.dto.UserDto;

public interface UserService {

  void delete(UserDto userDto);

  List<UserDto> findAll();

  boolean isCorrectPasswordFor(String username, String password);

  UserDto save(UserDto user);
}