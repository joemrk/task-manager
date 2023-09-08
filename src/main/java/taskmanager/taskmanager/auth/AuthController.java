package taskmanager.taskmanager.auth;

import lombok.RequiredArgsConstructor;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

  private final AuthService authService;

  @PostMapping("/register")
  ResponseEntity<AuthResponse> register(@RequestBody RegisterDto dto) {
    return ResponseEntity.ok(this.authService.register(dto));
  }

  @PostMapping("/login")
  ResponseEntity<AuthResponse> login(@RequestBody LoginDto dto) {
    return ResponseEntity.ok(this.authService.login(dto));
  }
}
