package shaart.pstorage.exception;

/**
 * Cannot determine user.
 */
public class UnauthorizedException extends PstorageException {

  public UnauthorizedException(String message) {
    super(message);
  }
}
