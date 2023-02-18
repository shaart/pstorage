package shaart.pstorage.crypto.impl;

import jakarta.annotation.PostConstruct;
import java.nio.charset.StandardCharsets;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.AlgorithmParameterSpec;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;
import java.util.Arrays;
import java.util.Base64;
import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import shaart.pstorage.crypto.Coder;
import shaart.pstorage.enumeration.EncryptionType;
import shaart.pstorage.exception.CryptoException;

@Slf4j
@Component
public class AesCoder implements Coder {

  private static final String PKBDF2_ALGO = "PBKDF2WithHmacSHA256";
  private static final String CYPHER_ALGORITHM = "AES/CBC/PKCS5PADDING";
  private static final int ITERATIONS = 10000;
  private static final int HASH_LENGTH_BYTES = 256;
  private static final int IV_LENGTH = 16;
  private static final int DUMMY_VALUE = 1;

  private Cipher cipher;
  private SecretKeyFactory factory;

  @PostConstruct
  @Override
  public void initialize() {
    try {
      cipher = Cipher.getInstance(CYPHER_ALGORITHM);
      factory = SecretKeyFactory.getInstance(PKBDF2_ALGO);
    } catch (NoSuchAlgorithmException | NoSuchPaddingException e) {
      throw new CryptoException(e);
    }
  }

  @Override
  public String decrypt(String toBeDecrypted, String key) {
    final byte[] secureIv = generateSecureIv(key);

    final byte[] salt = generateSalt(key);
    final SecretKey secretKey = generateEncryptionKey(key, salt);

    return decrypt(secretKey, secureIv, toBeDecrypted);
  }

  /**
   * Decrypt toBeDecrypted using the secret and passed iv.
   */
  private String decrypt(SecretKey secret, byte[] encodedIv,
      String toBeDecrypted) {
    byte[] iv = Base64.getDecoder().decode(encodedIv);
    AlgorithmParameterSpec ivParameterSpec = new IvParameterSpec(iv);

    byte[] decryptedValue;
    try {
      cipher.init(Cipher.DECRYPT_MODE, secret, ivParameterSpec);
      byte[] decodedValue = Base64.getDecoder().decode(toBeDecrypted.getBytes());

      decryptedValue = cipher.doFinal(decodedValue);
    } catch (InvalidKeyException | InvalidAlgorithmParameterException | IllegalBlockSizeException
        | BadPaddingException e) {
      log.error(e.getLocalizedMessage(), e);
      throw new CryptoException(e);
    }

    return new String(decryptedValue);
  }

  @Override
  public String encrypt(String toBeEncrypted, String key) {
    final byte[] salt = generateSalt(key);

    final SecretKey secretKey = generateEncryptionKey(key, salt);
    final byte[] encodedIv = generateSecureIv(key);

    return encrypt(secretKey, encodedIv, toBeEncrypted);
  }

  /**
   * Encrypt given toBeEncrypted with passed SecretKey and IV.
   */
  private String encrypt(SecretKey secret, byte[] encodedIv, String toBeEncrypted) {
    byte[] iv = Base64.getDecoder().decode(encodedIv);
    AlgorithmParameterSpec ivParameterSpec = new IvParameterSpec(iv);
    byte[] encryptedValue;

    try {
      cipher.init(Cipher.ENCRYPT_MODE, secret, ivParameterSpec);
      final byte[] toBeEncryptedBytes = toBeEncrypted.getBytes(StandardCharsets.UTF_8);
      byte[] cipherText = cipher.doFinal(toBeEncryptedBytes);

      encryptedValue = Base64.getEncoder().encode(cipherText);
    } catch (InvalidKeyException | InvalidAlgorithmParameterException | IllegalBlockSizeException
        | BadPaddingException e) {
      log.error(e.getLocalizedMessage(), e);
      throw new CryptoException(e);
    }
    return new String(encryptedValue);
  }

  @Override
  public EncryptionType getEncryptionType() {
    return EncryptionType.AES_CODER;
  }

  /**
   * Generate an encryption key using PBKDF2 with given salt, iterations and hash bytes.
   */
  private SecretKey generateEncryptionKey(String str, byte[] salt) {
    char[] strChars = str.toCharArray();
    KeySpec spec = new PBEKeySpec(strChars, salt, ITERATIONS, HASH_LENGTH_BYTES);

    SecretKey key;
    try {
      key = factory.generateSecret(spec);
      return new SecretKeySpec(key.getEncoded(), "AES");
    } catch (InvalidKeySpecException e) {
      log.error(e.getLocalizedMessage(), e);
      throw new CryptoException(e);
    }
  }

  /**
   * Generates salt based on master password.
   *
   * @param masterPassword main password (key)
   * @return a salt
   */
  private byte[] generateSalt(String masterPassword) {
    return masterPassword.getBytes(StandardCharsets.UTF_8);
  }

  /**
   * Generates secureIV using masterPassword.
   *
   * @param masterPassword main password (key)
   * @return a secure IV array
   */
  private byte[] generateSecureIv(String masterPassword) {
    byte[] resultBytes = new byte[IV_LENGTH];
    byte[] bytes = masterPassword.getBytes(StandardCharsets.UTF_8);
    if (bytes.length > IV_LENGTH) {
      resultBytes = Arrays.copyOfRange(bytes, 0, IV_LENGTH);
    } else if (bytes.length < IV_LENGTH) {
      System.arraycopy(bytes, 0, resultBytes, 0, bytes.length);
      for (int i = bytes.length; i < resultBytes.length; i++) {
        resultBytes[i] = DUMMY_VALUE;
      }
    }

    return Base64.getEncoder().encode(resultBytes);
  }
}
