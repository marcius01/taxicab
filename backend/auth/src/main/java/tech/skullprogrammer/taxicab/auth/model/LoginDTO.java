package tech.skullprogrammer.taxicab.auth.model;

import io.quarkus.runtime.annotations.RegisterForReflection;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
@RegisterForReflection
public class LoginDTO {

    @NotBlank
    private String username;
    private String password;
}
