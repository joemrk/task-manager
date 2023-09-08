package taskmanager.taskmanager.user;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import taskmanager.taskmanager.auth.RegisterDto;
import taskmanager.taskmanager.exception.errors.ConflictException;
import taskmanager.taskmanager.exception.errors.NotFoundException;

import java.util.Optional;

@Service
@AllArgsConstructor
@NoArgsConstructor
public class UserService {

  @Autowired
  private UserRepository userRepository;

  @Autowired
  private PasswordEncoder passwordEncoder;

  public Optional<User> findByEmail(String email) {
    Optional<User> exist = this.userRepository.findByEmail(email);
    return Optional.ofNullable(exist.orElseThrow(() -> new NotFoundException("User not found")));
  }

  public User createUser(RegisterDto dto)  {
    Optional<User> exist = this.userRepository.findByEmail(dto.getEmail());
    if(exist.isPresent()) {
      throw new ConflictException("User already exists");
    }

    User user = User.builder()
            .username(dto.getUsername())
            .email(dto.getEmail())
            .password(passwordEncoder.encode(dto.getPassword()))
            .build();

    return userRepository.save(user);
  }
}
