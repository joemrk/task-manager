package taskmanager.taskmanager.exception.errors;

import lombok.Getter;
import org.springframework.http.HttpStatus;
import taskmanager.taskmanager.exception.CustomException;

@Getter
public class NotFoundException extends CustomException {
  public NotFoundException(String message) {
    super(message, HttpStatus.NOT_FOUND.value(), "Not found");
  }
}
