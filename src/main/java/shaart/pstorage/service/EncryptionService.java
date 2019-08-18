package shaart.pstorage.service;

import shaart.pstorage.dto.CryptoDto;
import shaart.pstorage.dto.CryptoResult;

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
   * @param key an encryption key
   * @return encrypted value
   */
  CryptoResult encrypt(CryptoDto encryptionDto, String key);

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

}
