package taskmanager.taskmanager.exception.errors;

import lombok.Getter;
import org.springframework.http.HttpStatus;
import taskmanager.taskmanager.exception.CustomException;

@Getter
public class UnauthorizedException extends CustomException  {
  public UnauthorizedException(String message) {
    super(message, HttpStatus.UNAUTHORIZED.value(), "Unauthorized");
  }
}
