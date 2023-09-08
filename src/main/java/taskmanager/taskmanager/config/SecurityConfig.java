package taskmanager.taskmanager.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import taskmanager.taskmanager.auth.jwt.JwtGuard;
import taskmanager.taskmanager.exception.errors.UnauthorizedException;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

  private final JwtGuard jwtGuard;
  private final AuthenticationProvider authProvider;

  @Bean
  public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
    http
            .csrf(AbstractHttpConfigurer::disable)
            .authorizeHttpRequests(r -> r
                    .requestMatchers(
                            "/auth/**"
                    )
                    .permitAll()
                    .anyRequest()
                    .authenticated()
            )
//            .exceptionHandling((e) -> {
//              e.authenticationEntryPoint((req, res, ee) -> {throw new UnauthorizedException("wwd");})
//                      .accessDeniedHandler((req, res, ee) -> {throw new UnauthorizedException("wwd");});
//            })
            .sessionManagement(s -> s.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
            .authenticationProvider(authProvider)
            .addFilterBefore(jwtGuard, UsernamePasswordAuthenticationFilter.class);

    return http.build();
  }
}
