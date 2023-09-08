package taskmanager.taskmanager.auth.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import taskmanager.taskmanager.user.User;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.HashMap;
import java.util.function.Function;

@Service
public class JwtService {

  @Value("${app.security.jwt.secret-key}")
  private String SECRET_KEY;

  @Value("${app.security.jwt.expiration}")
  private Long EXPIRATION; // a day

  public String createToken(User userDetails) {
    HashMap<String, String> extra = new HashMap<>();
    extra.put("email", userDetails.getEmail());

    return this.createToken(extra, userDetails);
  }

  public String createToken(HashMap extra, User userDetails) {
    return Jwts.builder()
            .setClaims(extra)
            .setSubject(userDetails.getUsername())
            .setIssuedAt(new Date(System.currentTimeMillis()))
            .setExpiration(new Date(System.currentTimeMillis() + this.EXPIRATION))
            .signWith(getSecret(), SignatureAlgorithm.HS256)
            .compact();
  }

  private SecretKey getSecret() {
    byte[] secretBytes = Decoders.BASE64.decode(this.SECRET_KEY);
    return Keys.hmacShaKeyFor(secretBytes);
  }

  public boolean isTokenValid(String token, User userDetails) {
    final String username = this.extractSubject(token);
    return username.equals(userDetails.getUsername()) && !isTokenExpired(token);
  }

  public boolean isTokenExpired(String token) {
    return this.extractExpiration(token).before(new Date());
  }

  private Date extractExpiration(String token) {
    return extractClaims(token, Claims::getExpiration);
  }

  public String extractSubject(String token){
    return extractClaims(token, Claims::getSubject);
  }

  public String extractExtraClaim(String token, String extraClaim) {
    Claims claims = this.extractClaims(token);
    return (String) claims.get(extraClaim);
  }

  public <T> T extractClaims(String token, Function<Claims, T> claimResolver){
    final Claims claims = extractClaims(token);
    return claimResolver.apply(claims);
  }

  private Claims extractClaims(String token) {
    return Jwts
            .parserBuilder()
            .setSigningKey(this.getSecret())
            .build()
            .parseClaimsJws(token)
            .getBody();
  }
}
