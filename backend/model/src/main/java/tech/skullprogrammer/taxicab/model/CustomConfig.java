package tech.skullprogrammer.taxicab.model;

import io.quarkus.runtime.annotations.RegisterForReflection;
import io.smallrye.config.ConfigMapping;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.eclipse.microprofile.config.inject.ConfigProperties;

@ConfigMapping(prefix = "tech.skullprogrammer.taxicab")
public interface CustomConfig {
    Jwt jwt();

    interface Jwt {
        String issuer();
        int expiration();
    }
}
