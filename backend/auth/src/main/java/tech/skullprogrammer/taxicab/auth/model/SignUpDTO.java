package tech.skullprogrammer.taxicab.auth.model;

import io.quarkus.runtime.annotations.RegisterForReflection;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Past;
import lombok.Data;
import tech.skullprogrammer.taxicab.model.customer.Wallet;

import java.time.LocalDate;

@Data
@RegisterForReflection
public class SignUpDTO {

    @NotBlank
    private String username;
    @NotBlank
    @Email()
    private String email;
    @NotBlank
    private String password;
    private String name;
    private String lastName;
    @Past
    private LocalDate birth;
    private Wallet wallet;
}
