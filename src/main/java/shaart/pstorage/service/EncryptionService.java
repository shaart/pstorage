package shaart.pstorage.service;

/**
 * An encryption service for encrypt-decrypt operations.
 */
public interface EncryptionService {

  /**
   * Encrypts a value using default key.
   *
   * @param value a value to be encrypted
   * @return encrypted value
   */
  String encrypt(String value);

  /**
   * Encrypts a value using key.
   *
   * @param value a value to be encrypted
   * @param key an encryption key
   * @return encrypted value
   */
  String encrypt(String value, String key);

  /**
   * Decrypts a value using default key.
   *
   * @param value a value to be decrypted
   * @return decrypted value
   */
  String decrypt(String value);

  /**
   * Decrypts a value using key.
   *
   * @param value a value to be decrypted
   * @return decrypted value
   */
  String decrypt(String value, String key);

}
