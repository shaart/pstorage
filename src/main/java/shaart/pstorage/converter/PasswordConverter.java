package shaart.pstorage.converter;

import shaart.pstorage.dto.PasswordDto;
import shaart.pstorage.entity.Password;

/**
 * Converter for {@link Password} and {@link PasswordDto}.
 */
public interface PasswordConverter {

  Password convert(PasswordDto passwordDto);

  PasswordDto convert(Password savedPassword);
}
