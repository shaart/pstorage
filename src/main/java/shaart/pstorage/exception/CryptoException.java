package shaart.pstorage.exception;

public class CryptoException extends PstorageException {

  public CryptoException(Exception e) {
    super(e);
  }

  public CryptoException(String message) {
    super(message);
  }
}
