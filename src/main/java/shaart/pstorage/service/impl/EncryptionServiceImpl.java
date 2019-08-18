package shaart.pstorage.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import shaart.pstorage.config.PStorageProperties;
import shaart.pstorage.crypto.Coder;
import shaart.pstorage.dto.CryptoDto;
import shaart.pstorage.dto.CryptoResult;
import shaart.pstorage.service.EncryptionService;

@Slf4j
@Component
@RequiredArgsConstructor
public class EncryptionServiceImpl implements EncryptionService {

  private final PStorageProperties pstorageProperties;
  private final Coder coder;

  @Override
  public CryptoResult encrypt(CryptoDto encryptionDto) {
    String key = pstorageProperties.getAes().getCommon().getKey();

    return encrypt(encryptionDto, key);
  }

  @Override
  public CryptoResult encrypt(CryptoDto encryptionDto, String key) {
    return CryptoResult.builder()
        .value(coder.encrypt(encryptionDto.getValue(), key))
        .encryptionType(coder.getEncryptionType())
        .build();
  }

  @Override
  public CryptoResult decrypt(CryptoDto decryptionDto) {
    String key = pstorageProperties.getAes().getCommon().getKey();

    return decrypt(decryptionDto, key);
  }

  @Override
  public CryptoResult decrypt(CryptoDto decryptionDto, String key) {
    return CryptoResult.builder()
        .value(coder.decrypt(decryptionDto.getValue(), key))
        .encryptionType(coder.getEncryptionType())
        .build();
  }
}
