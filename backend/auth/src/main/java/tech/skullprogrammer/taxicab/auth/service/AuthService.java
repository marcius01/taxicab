package tech.skullprogrammer.taxicab.auth.service;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.validation.Valid;
import jakarta.ws.rs.core.Response;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.digest.DigestUtils;
import org.eclipse.microprofile.rest.client.inject.RestClient;
import tech.skullprogrammer.core.exception.SkullResourceException;
import tech.skullprogrammer.core.model.CustomConfig;
import tech.skullprogrammer.core.service.MailService;
import tech.skullprogrammer.taxicab.Roles;
import tech.skullprogrammer.taxicab.auth.client.IProfileClient;
import tech.skullprogrammer.taxicab.auth.model.LoginDTO;
import tech.skullprogrammer.taxicab.auth.model.ResetPasswordDTO;
import tech.skullprogrammer.taxicab.auth.model.SignUpDTO;
import tech.skullprogrammer.taxicab.auth.security.JWTUtility;
import tech.skullprogrammer.taxicab.exception.SkullErrorImpl;
import tech.skullprogrammer.taxicab.model.Profile;
import tech.skullprogrammer.taxicab.model.User;
import tech.skullprogrammer.taxicab.model.customer.Wallet;
import tech.skullprogrammer.taxicab.repository.UserRepository;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

@Slf4j
@ApplicationScoped
public class AuthService {

    @Inject
    UserRepository userRepo;
    @Inject
    CustomConfig config;
    @Inject
    JWTUtility jwtUtility;
    @Inject
    MailService mailService;
    @Inject
    @RestClient
    IProfileClient profileClient;

    public String loginToken(LoginDTO loginDTO) {
        Optional<User> user = userRepo.findByEmailAndPassword(loginDTO.getEmail(), hashValue(loginDTO.getPassword()));
        if (user.isEmpty()) throw SkullResourceException.builder().error(SkullErrorImpl.WRONG_CREDENTIALS).build();
        return jwtUtility.generateJWT(user.get());
    }

    public void resetPassword(ResetPasswordDTO resetPasswordDTO) {
        Optional<User> userOPT = userRepo.findByEmailAndOTP(resetPasswordDTO.getEmail(), hashValue(resetPasswordDTO.getOtp()));
        log.debug(userOPT.toString());
        if (userOPT.isEmpty()) throw SkullResourceException.builder().error(SkullErrorImpl.WRONG_OTP).build();
        if (LocalDateTime.now().isAfter(userOPT.get().getOtpExpiration()))
            throw SkullResourceException.builder().error(SkullErrorImpl.EXPIRED_OTP).build();
        User user = userOPT.get();
        user.setPassword(hashValue(resetPasswordDTO.getPassword()));
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
                    user.setResetPasswordOtp(hashValue(otp));
                    user.setOtpExpiration(otpExpiration);
                    mailService.sendResetPasswordMail(user.getUsername(), lang, otp, user.getEmail());
                    userRepo.persistOrUpdate(user);
                });
    }

    public void signUp(@Valid SignUpDTO signUpDTO) {
        if (userRepo.findByEmail(signUpDTO.getEmail()).isPresent())
            throw SkullResourceException.builder().error(SkullErrorImpl.EMAIL_ALREADY_IN_DB).build();
        Profile profile = createPartialProfile(signUpDTO);
        checkProfile(profile);
        User user = createUser(signUpDTO);
        userRepo.persist(user);
        profile.setUserId(user.getId());
        try (Response response = profileClient.createProfile(profile)) {
        } catch (RuntimeException e) {
            userRepo.delete(user);
        }
    }

    private User createUser(SignUpDTO signUpDTO) {
        return User.builder()
                .username(signUpDTO.getUsername())
                .roles(Set.of(Roles.USER))
                .email(signUpDTO.getEmail())
                .password(hashValue(signUpDTO.getPassword()))
                .build();
    }

    private void checkProfile(Profile profile) {
        try (Response response = profileClient.checkProfile(profile);) {
            log.debug("Response on profile check: {}", response.getStatusInfo());
        } catch (SkullResourceException ex) {
            throw ex;
        } catch (Exception ex) {
            throw SkullResourceException.builder().error(SkullErrorImpl.NO_VALID_PROFILE).build();
        }
    }

    private Profile createPartialProfile(SignUpDTO signUpDTO) {
        return Profile.builder()
                .name(signUpDTO.getName())
                .lastName(signUpDTO.getLastName())
                .birth(signUpDTO.getBirth())
                .wallet(new Wallet())
                .build();
    }

    private String hashValue(String value) {
        return DigestUtils.sha256Hex(value);
    }
}
