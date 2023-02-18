package shaart.pstorage.exception;

/**
 * Error causes that user with that username not found.
 */
public class UserNotFoundException extends PstorageException {

  public UserNotFoundException(String username) {
    super(String.format("User with name '%s' not found", username));
  }
}
