package tech.skullprogrammer.taxicab.auth.model;

import io.quarkus.runtime.annotations.RegisterForReflection;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
@RegisterForReflection
public class ResetPasswordDTO {
    @NotBlank
    private String email;
    @NotBlank
    private String password;
    @NotBlank
    private String otp;
}