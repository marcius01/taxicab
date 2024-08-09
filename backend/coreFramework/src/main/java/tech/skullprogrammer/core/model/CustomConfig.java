package tech.skullprogrammer.core.model;

import io.quarkus.runtime.annotations.RegisterForReflection;
import io.smallrye.config.ConfigMapping;
import io.smallrye.config.WithDefault;
import io.smallrye.config.WithName;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.eclipse.microprofile.config.inject.ConfigProperties;

@ConfigMapping(prefix = "tech.skullprogrammer.taxicab")
public interface CustomConfig {
    Jwt jwt();
    ResetPassword resetPassword();

    Frontend frontend();

    interface Jwt {
        String issuer();
        int expiration();
    }

    interface ResetPassword {
        @WithName("otp.expiration.minutes")
        @WithDefault("5")
        int otpDuration();
    }

    interface Frontend {
        String url();

        String resetPasswordRoute();
    }

}
