package shaart.pstorage.converter;

import shaart.pstorage.dto.UserDto;
import shaart.pstorage.entity.User;

/**
 * Converter for {@link User} and {@link UserDto}.
 */
public interface UserConverter {

  User toEntity(UserDto userDto);

  UserDto toDto(User savedUser);
}
