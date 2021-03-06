package shaart.pstorage.exception;

public class AliasAlreadyExistsException extends PStorageException {

  public AliasAlreadyExistsException(String alias) {
    super(String.format("Alias with value '%s' already exists", alias));
  }
}
