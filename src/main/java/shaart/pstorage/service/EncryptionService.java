package shaart.pstorage.service;

import shaart.pstorage.dto.CryptoDto;
import shaart.pstorage.dto.CryptoResult;
import shaart.pstorage.dto.UserDto;
import shaart.pstorage.enumeration.EncryptionType;

/**
 * An encryption service for encrypt-decrypt operations.
 */
public interface EncryptionService {

  /**
   * Encrypts a value using default key.
   *
   * @param encryptionDto a value to be encrypted
   * @return encrypted value
   */
  CryptoResult encrypt(CryptoDto encryptionDto);

  /**
   * Encrypts a value using key.
   *
   * @param encryptionDto a value to be encrypted
   * @param key           an encryption key
   * @return encrypted value
   */
  CryptoResult encrypt(CryptoDto encryptionDto, String key);

  /**
   * Encrypts a value using user's master password as a key.
   *
   * @param encryptionDto a value to be encrypted
   * @param user          a user with master password
   * @return encrypted value
   */
  CryptoResult encryptForUser(CryptoDto encryptionDto, UserDto user);

  /**
   * Decrypts a value using default key.
   *
   * @param value a value to be decrypted
   * @return decrypted value
   */
  CryptoResult decrypt(CryptoDto value);

  /**
   * Decrypts a value using key.
   *
   * @param value a value to be decrypted
   * @return decrypted value
   */
  CryptoResult decrypt(CryptoDto value, String key);

  CryptoResult decrypt(EncryptionType encryptionType, CryptoDto cryptoDto, String key);

  /**
   * Encrypts a value using user's master password as a key.
   *
   * @param value a value to be decrypted
   * @param user  a user with master password
   * @return decrypted value
   */
  CryptoResult decryptForUser(CryptoDto value, UserDto user);
}
