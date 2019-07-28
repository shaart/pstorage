package shaart.pstorage.service.impl;

import java.nio.charset.Charset;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import javax.crypto.Cipher;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import shaart.pstorage.config.PStorageProperties;
import shaart.pstorage.exception.CryptoException;
import shaart.pstorage.service.EncryptionService;

@Slf4j
@Component
@RequiredArgsConstructor
public class EncryptionServiceImpl implements EncryptionService {

  private static final String UTF_8 = "UTF-8";
  private static final String ENCRYPT_TYPE = "AES";
  private static final String CYPHER_ALGORITHM = "AES/CBC/PKCS5PADDING";

  private final PStorageProperties pstorageProperties;

  @Override
  public String encrypt(String value) {
    try {
      Cipher cipher = this.newCipherInstance(CipherMode.ENCRYPT);

      byte[] encrypted = cipher.doFinal(value.getBytes());
      return new String(Base64.getEncoder().encode(encrypted));
    } catch (Exception e) {
      log.error(e.getLocalizedMessage(), e);
      throw new CryptoException(e);
    }
  }

  @Override
  public String encrypt(String value, String key) {
    throw new UnsupportedOperationException("Not implemented");
  }

  @Override
  public String decrypt(String value) {
    try {
      Cipher cipher = this.newCipherInstance(CipherMode.DECRYPT);
      byte[] original = cipher.doFinal(Base64.getDecoder().decode(value));

      return new String(original);
    } catch (Exception e) {
      log.error(e.getLocalizedMessage(), e);
      throw new CryptoException(e);
    }
  }

  @Override
  public String decrypt(String value, String key) {
    throw new UnsupportedOperationException("Not implemented");
  }

  private Cipher newCipherInstance(CipherMode mode)
      throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidAlgorithmParameterException,
      InvalidKeyException {
    String vector = pstorageProperties.getAes().getCommon().getVector();
    String key = pstorageProperties.getAes().getCommon().getKey();

    IvParameterSpec ivParameterSpec = new IvParameterSpec(vector.getBytes(Charset.forName(UTF_8)));
    SecretKeySpec secretKeySpec = new SecretKeySpec(key.getBytes(Charset.forName(UTF_8)),
        ENCRYPT_TYPE);

    Cipher instance = Cipher.getInstance(CYPHER_ALGORITHM);

    instance.init(mode.getValue(), secretKeySpec, ivParameterSpec);

    return instance;
  }

  private enum CipherMode {
    ENCRYPT(Cipher.ENCRYPT_MODE),
    DECRYPT(Cipher.DECRYPT_MODE);

    @Getter
    int value;

    CipherMode(int modeValue) {
      value = modeValue;
    }
  }
}
