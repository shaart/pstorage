package shaart.pstorage.service;

import java.util.List;
import java.util.Optional;
import shaart.pstorage.dto.UserDto;

/**
 * Service for working with User.
 */
public interface UserService {

  void delete(UserDto userDto);

  List<UserDto> findAll();

  Optional<UserDto> findByName(String username);

  /**
   * Checks is password correct for user with username.
   *
   * @param username Username for check
   * @param password Input password to check
   * @return true if credentials matches
   */
  boolean isCorrectPasswordFor(String username, String password);

  UserDto save(UserDto user);

  /**
   * Checks if user exists.
   *
   * @param username User's name
   * @return true is exists
   */
  boolean exists(String username);
}