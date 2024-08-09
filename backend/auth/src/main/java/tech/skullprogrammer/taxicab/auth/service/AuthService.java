package tech.skullprogrammer.taxicab.auth.service;

import io.smallrye.jwt.build.Jwt;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.digest.DigestUtils;
import tech.skullprogrammer.core.service.MailService;
import tech.skullprogrammer.taxicab.auth.model.LoginDTO;
import tech.skullprogrammer.taxicab.auth.exception.SkullErrorImpl;
import tech.skullprogrammer.core.exception.SkullResourceException;
import tech.skullprogrammer.core.model.CustomConfig;
import tech.skullprogrammer.taxicab.model.User;
import tech.skullprogrammer.taxicab.repository.UserRepository;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

@Slf4j
@ApplicationScoped
public class AuthService {

    @Inject
    private UserRepository userRepo;
    @Inject
    private CustomConfig config;
    @Inject
    private MailService mailService;

    public String generateToken(LoginDTO loginDTO) {
        Optional<User> user = userRepo.findByEmailAndPassword(loginDTO.getEmail(), DigestUtils.sha256Hex(loginDTO.getPassword()));
        if (user.isEmpty()) throw SkullResourceException.builder().error(SkullErrorImpl.WRONG_CREDENTIALS).build();
        return generateJWT(user.get());
    }

    public void sendOtpEmail(String lang, String email) {
        userRepo.findByEmail(email)
                .ifPresent(user -> {
                    var otp = UUID.randomUUID().toString();
                    var otpExpiration = LocalDateTime.now()
                            .plusMinutes(config.resetPassword().otpDuration());
                    user.setResetPasswordOtp(DigestUtils.sha256Hex(otp));
                    user.setOtpExpiration(otpExpiration);
                    mailService.sendResetPasswordMail(user.getName(), lang, otp, user.getEmail());
                    userRepo.persistOrUpdate(user);
                });
    }
    private String generateJWT(User user) {
        return Jwt.issuer(config.jwt().issuer())
                .subject(user.getUsername())
                .groups(user.getRoles())
                .expiresIn(Duration.ofSeconds(config.jwt().expiration()))
                .sign();
    }
}
