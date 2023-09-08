package taskmanager.taskmanager.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.web.authentication.Http403ForbiddenEntryPoint;
import org.springframework.web.bind.annotation.*;
import taskmanager.taskmanager.exception.errors.BadRequestException;
import taskmanager.taskmanager.exception.errors.ConflictException;
import taskmanager.taskmanager.exception.errors.NotFoundException;
import taskmanager.taskmanager.exception.errors.UnauthorizedException;

@RestControllerAdvice
@ResponseBody
@RequestMapping(produces = "application/json")
public class GlobalExceptionHandler {

  @ExceptionHandler(ConflictException.class)
  @ResponseStatus(HttpStatus.CONFLICT)
  public ResponseEntity<ErrorResponse> handleConflictException(ConflictException ex) {
    return new ResponseEntity<>(new ErrorResponse(ex), HttpStatus.CONFLICT);
  }

  @ExceptionHandler(NotFoundException.class)
  @ResponseStatus(HttpStatus.NOT_FOUND)
  public ResponseEntity<ErrorResponse> handleNotFoundException(NotFoundException ex) {
    return new ResponseEntity<>(new ErrorResponse(ex), HttpStatus.NOT_FOUND);
  }

  @ExceptionHandler(BadRequestException.class)
  @ResponseStatus(HttpStatus.BAD_REQUEST)
  public ResponseEntity<ErrorResponse> handleBadRequestException(BadRequestException ex) {
    return new ResponseEntity<>(new ErrorResponse(ex), HttpStatus.BAD_REQUEST);
  }

  @ExceptionHandler(UnauthorizedException.class)
  @ResponseStatus(HttpStatus.UNAUTHORIZED)
  public ResponseEntity<ErrorResponse> handleUnauthorizedException(UnauthorizedException ex) {
    return new ResponseEntity<>(new ErrorResponse(ex), HttpStatus.UNAUTHORIZED);
  }

  @ExceptionHandler(AccessDeniedException.class)
  @ResponseStatus(HttpStatus.FORBIDDEN)
  public ResponseEntity<ErrorResponse> handleAccessDeniedException(AccessDeniedException ex){
    return new ResponseEntity<>(
            new ErrorResponse(HttpStatus.FORBIDDEN.value(), "Forbidden", ex.getMessage()),
            HttpStatus.FORBIDDEN
    );
  }

  @ExceptionHandler(BadCredentialsException.class)
  @ResponseStatus(HttpStatus.FORBIDDEN)
  public ResponseEntity<ErrorResponse> handleBadCredentialsException(BadCredentialsException ex){
    return new ResponseEntity<>(
            new ErrorResponse(HttpStatus.FORBIDDEN.value(), "Forbidden", ex.getMessage()),
            HttpStatus.FORBIDDEN
    );
  }

  @Getter
  public static class ErrorResponse {
    private final int statusCode;
    private final String error;
    private final String message;

    public ErrorResponse(int statusCode, String error, String message) {
      this.statusCode = statusCode;
      this.error = error;
      this.message = message;
    }

    public ErrorResponse(CustomException ex) {
      this.statusCode = ex.getStatusCode();
      this.error = ex.getError();
      this.message = ex.getMessage();
    }
  }
}
