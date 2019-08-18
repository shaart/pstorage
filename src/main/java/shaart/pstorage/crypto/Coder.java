package shaart.pstorage.crypto;

import javax.annotation.PostConstruct;
import shaart.pstorage.enumeration.EncryptionType;
import shaart.pstorage.exception.CryptoException;

/**
 * Component for encrypting and decrypting with some algorithm.
 */
public interface Coder {

  @PostConstruct
  void initialize();

  /**
   * Decrypts info with a key.
   *
   * @param toBeDecrypted Encrypted information
   * @param key A key that was used during encryption
   * @return decrypted value
   * @throws CryptoException on error
   */
  String decrypt(String toBeDecrypted, String key);

  /**
   * Encrypt value with a key.
   *
   * @param toBeEncrypted Initial value
   * @param key A key
   * @return encrypted value
   * @throws CryptoException on error
   */
  String encrypt(String toBeEncrypted, String key);

  /**
   * Get encryption type of current coder.
   */
  EncryptionType getEncryptionType();
}
