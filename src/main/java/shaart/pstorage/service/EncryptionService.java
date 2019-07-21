package shaart.pstorage.service;

public interface EncryptionService {

  String encrypt(String value);

  String decrypt(String value);

}
