package tech.skullprogrammer.taxicab.auth.service;

import io.smallrye.jwt.build.Jwt;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.digest.DigestUtils;
import tech.skullprogrammer.taxicab.auth.model.LoginDTO;
import tech.skullprogrammer.taxicab.auth.exception.SkullErrorImpl;
import tech.skullprogrammer.core.exception.SkullResourceException;
import tech.skullprogrammer.taxicab.model.CustomConfig;
import tech.skullprogrammer.taxicab.model.User;
import tech.skullprogrammer.taxicab.repository.RepositoryUser;

import java.time.Duration;
import java.util.Optional;

@Slf4j
@ApplicationScoped
public class AuthService {

    @Inject
    private RepositoryUser repoUser;
    @Inject
    private CustomConfig config;

    public String generateToken(LoginDTO loginDTO) {
        Optional<User> user = repoUser.findByUsernameAndPassword(loginDTO.getUsername(), DigestUtils.sha256Hex(loginDTO.getPassword()));
        if (user.isEmpty()) throw SkullResourceException.builder().error(SkullErrorImpl.WRONG_CREDENTIALS).build();
        return generateJWT(user.get());
    }

    private String generateJWT(User user) {
        return Jwt.issuer(config.jwt().issuer())
                .subject(user.getUsername())
                .groups(user.getRoles())
                .expiresIn(Duration.ofSeconds(config.jwt().expiration()))
                .sign();
    }
}
