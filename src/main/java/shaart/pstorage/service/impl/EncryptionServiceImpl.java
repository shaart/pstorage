package shaart.pstorage.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import shaart.pstorage.config.PStorageProperties;
import shaart.pstorage.crypto.AesCoder;
import shaart.pstorage.dto.CryptoDto;
import shaart.pstorage.service.EncryptionService;

@Slf4j
@Component
@RequiredArgsConstructor
public class EncryptionServiceImpl implements EncryptionService {

  private final PStorageProperties pstorageProperties;
  private final AesCoder aesCoder;

  @Override
  public String decrypt(CryptoDto decryptionDto) {
    String key = pstorageProperties.getAes().getCommon().getKey();

    return decrypt(decryptionDto, key);
  }

  @Override
  public String decrypt(CryptoDto decryptionDto, String key) {
    return aesCoder.decrypt(decryptionDto.getValue(), key);
  }

  @Override
  public String encrypt(CryptoDto encryptionDto) {
    String key = pstorageProperties.getAes().getCommon().getKey();

    return encrypt(encryptionDto, key);
  }

  @Override
  public String encrypt(CryptoDto encryptionDto, String key) {
    return aesCoder.encrypt(encryptionDto.getValue(), key);
  }
}
