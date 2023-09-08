package taskmanager.taskmanager.auth;

import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LoginDto {
  private String email;
  @Getter
  private String password;

}
