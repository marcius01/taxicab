package tech.skullprogrammer.taxicab.auth.service;

import io.smallrye.jwt.build.Jwt;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.digest.DigestUtils;
import tech.skullprogrammer.core.Constants;
import tech.skullprogrammer.core.service.MailService;
import tech.skullprogrammer.taxicab.auth.model.LoginDTO;
import tech.skullprogrammer.taxicab.exception.SkullErrorImpl;
import tech.skullprogrammer.core.exception.SkullResourceException;
import tech.skullprogrammer.core.model.CustomConfig;
import tech.skullprogrammer.taxicab.auth.model.ResetPasswordDTO;
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
    UserRepository userRepo;
    @Inject
    CustomConfig config;
    @Inject
    MailService mailService;

    public String loginToken(LoginDTO loginDTO) {
        Optional<User> user = userRepo.findByEmailAndPassword(loginDTO.getEmail(), DigestUtils.sha256Hex(loginDTO.getPassword()));
        if (user.isEmpty()) throw SkullResourceException.builder().error(SkullErrorImpl.WRONG_CREDENTIALS).build();
        return generateJWT(user.get());
    }

    public void resetPassword(ResetPasswordDTO resetPasswordDTO) {
        Optional<User> userOPT = userRepo.findByEmailAndOTP(resetPasswordDTO.getEmail(), DigestUtils.sha256Hex(resetPasswordDTO.getOtp()));
        log.debug(userOPT.toString());
        if (userOPT.isEmpty()) throw SkullResourceException.builder().error(SkullErrorImpl.WRONG_OTP).build();
        if (LocalDateTime.now().isAfter(userOPT.get().getOtpExpiration())) throw SkullResourceException.builder().error(SkullErrorImpl.EXPIRED_OTP).build();
        User user = userOPT.get();
        user.setPassword(DigestUtils.sha256Hex(resetPasswordDTO.getPassword()));
        user.setResetPasswordOtp(null);
        user.setOtpExpiration(null);
        userRepo.update(user);
      }

    public void sendOtpEmail(String lang, String email) {
        userRepo.findByEmail(email)
                .ifPresent(user -> {
                    var otp = UUID.randomUUID().toString();
                    var otpExpiration = LocalDateTime.now()
                            .plusMinutes(config.resetPassword().otpDuration());
                    user.setResetPasswordOtp(DigestUtils.sha256Hex(otp));
                    user.setOtpExpiration(otpExpiration);
                    mailService.sendResetPasswordMail(user.getUsername(), lang, otp, user.getEmail());
                    userRepo.persistOrUpdate(user);
                });
    }
    private String generateJWT(User user) {
        return Jwt.issuer(config.jwt().issuer())
                .subject(user.getUsername())
                .groups(user.getRoles())
                .claim(Constants.CLAIM_USER_ID, user.getId())
                .expiresIn(Duration.ofSeconds(config.jwt().expiration()))
                .sign();
    }
}
