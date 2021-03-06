package shaart.pstorage.service;

import java.util.List;
import java.util.Optional;
import java.util.function.Supplier;
import shaart.pstorage.dto.PasswordDto;
import shaart.pstorage.dto.UserDto;
import shaart.pstorage.enumeration.EncryptionType;

public interface PasswordService {

  List<PasswordDto> findAll(Supplier<Optional<UserDto>> currentUserSupplier);

  List<PasswordDto> findAllByUser(String userName);

  PasswordDto save(PasswordDto password);

  List<PasswordDto> findFavoritesByUser(String userName);

  void updateAlias(String passwordId, String newAlias);

  void updatePassword(String passwordId, EncryptionType encryptionType,
      String newEncryptedValue);

  void deleteById(String passwordId);
}