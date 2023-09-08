package taskmanager.taskmanager.exception;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CustomException extends RuntimeException {
  private int statusCode;
  private String error;
  private String message;

  public CustomException(String message, int statusCode, String error) {
    super(message);
    this.message = message;
    this.statusCode = statusCode;
    this.error = error;
  }
}
