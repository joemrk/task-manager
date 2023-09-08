package taskmanager.taskmanager.exception.errors;

import lombok.Getter;
import org.springframework.http.HttpStatus;
import taskmanager.taskmanager.exception.CustomException;

@Getter
public class ConflictException extends CustomException {
  public ConflictException(String message) {
    super(message, HttpStatus.CONFLICT.value(), "Conflict");
  }
}

