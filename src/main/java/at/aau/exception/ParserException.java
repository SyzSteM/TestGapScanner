package at.aau.exception;

public class ParserException extends Exception {

  private ParserException(String message) {
    super(message);
  }

  private ParserException(String message, Exception cause) {
    super(message, cause);
  }

  public static ParserException of(String message) {
    return new ParserException(message);
  }

  public static ParserException withCause(String message, Exception cause) {
    return new ParserException(message, cause);
  }

}
