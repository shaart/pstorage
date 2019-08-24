package shaart.pstorage.service;

import java.util.Optional;
import shaart.pstorage.dto.UserDto;

public interface SecurityAwareService {

  void authorize(String username, String password);

  Optional<UserDto> currentUser();
}
