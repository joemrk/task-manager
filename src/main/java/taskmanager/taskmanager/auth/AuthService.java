package taskmanager.taskmanager.auth;

import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;
import taskmanager.taskmanager.auth.jwt.JwtService;
import taskmanager.taskmanager.user.User;
import taskmanager.taskmanager.user.UserService;

@Service
@NoArgsConstructor
public class AuthService {

  private UserService userService;
  private JwtService jwtService;
  private AuthenticationManager authenticationManager;


  @Autowired
  public AuthService(
          UserService userService,
          JwtService jwtService,
          AuthenticationManager authenticationManager
  ) {
    this.userService = userService;
    this.jwtService = jwtService;
    this.authenticationManager = authenticationManager;
  }

  public AuthResponse register(RegisterDto dto)  {
    User user = this.userService.createUser(dto);

    return AuthResponse.builder()
            .token(jwtService.createToken(user))
            .build();
  }

  public AuthResponse login(LoginDto dto) {
    // ця хуйня сама під капотом парсить паролі і перевіряє
    this.authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(dto.getEmail(), dto.getPassword())
    );
    // проблема тільки в тому, що виконується 2 запити в бд
    // мб є варік в скоуп реквеста закинути юзера
    User user = this.userService.findByEmail(dto.getEmail())
            .orElseThrow();

    return AuthResponse.builder()
            .token(jwtService.createToken(user))
            .build();
  }
}
