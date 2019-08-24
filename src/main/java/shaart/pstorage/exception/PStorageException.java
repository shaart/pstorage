package shaart.pstorage.exception;

class PStorageException extends RuntimeException {

  PStorageException() {
  }

  PStorageException(String message) {
    super(message);
  }

  PStorageException(Exception e) {
    super(e);
  }
}
