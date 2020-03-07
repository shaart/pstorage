package shaart.pstorage.exception;

public class UserNotFoundException extends PStorageException {

  public UserNotFoundException(String username) {
    super(String.format("User with name '%s' not found", username));
  }
}
