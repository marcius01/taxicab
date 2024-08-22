package tech.skullprogrammer.taxicab.auth.security;

import io.smallrye.jwt.build.Jwt;
import jakarta.inject.Inject;
import jakarta.inject.Singleton;
import tech.skullprogrammer.core.Constants;
import tech.skullprogrammer.core.model.CustomConfig;
import tech.skullprogrammer.taxicab.Roles;
import tech.skullprogrammer.taxicab.model.User;

import java.time.Duration;

@Singleton
public class JWTUtility {

    @Inject
    CustomConfig config;

    public String generateJWT(User user) {
        return Jwt.issuer(config.jwt().issuer())
                .subject(user.getUsername())
                .groups(user.getRoles())
                .claim(Constants.CLAIM_USER_ID, user.getId())
                .expiresIn(Duration.ofSeconds(config.jwt().expiration()))
                .sign();
    }

    public String generateJWTSystemUser() {
        return Jwt.issuer(config.jwt().issuer())
                .subject(Roles.SYSTEM)
                .groups(Roles.SYSTEM)
                .expiresIn(Duration.ofSeconds(5))
                .sign();
    }
}
