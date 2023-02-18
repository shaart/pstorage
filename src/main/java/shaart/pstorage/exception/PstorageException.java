package shaart.pstorage.exception;

public class PstorageException extends RuntimeException {

  public PstorageException() {
  }

  public PstorageException(String message) {
    super(message);
  }

  public PstorageException(Exception e) {
    super(e);
  }
}
