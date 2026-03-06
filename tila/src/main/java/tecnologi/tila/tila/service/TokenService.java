package tecnologi.tila.tila.service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Service;
import tecnologi.tila.tila.entity.Usuario;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

@Service
public class TokenService{

    @Value("${api.security.token.secret}")
    private String secret;

    public String gerarToken(Usuario usuario){

        Algorithm algoritimo = Algorithm.HMAC256(secret);
        return JWT.create()
                .withIssuer("TILA-APP")
                .withSubject(usuario.getEmail())
                .withClaim("role", usuario.getPerfil().toString())
                .withExpiresAt(dataExpiracao())
                .sign(algoritimo);

    }

    public String getSubject(String tokenJWT){
        Algorithm algoritimo = Algorithm.HMAC256(secret);
        return JWT.require(algoritimo)
                .withIssuer("TILA-API")
                .build()
                .verify(tokenJWT)
                .getSubject();
    }

    private Instant dataExpiracao(){
        return LocalDateTime.now().plusHours(1).toInstant(ZoneOffset.of("-03:00"));
    }

}
