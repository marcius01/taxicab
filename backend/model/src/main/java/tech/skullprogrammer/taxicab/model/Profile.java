package tech.skullprogrammer.taxicab.model;

import io.quarkus.runtime.annotations.RegisterForReflection;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import lombok.Builder;
import lombok.Data;
import org.bson.types.ObjectId;
import tech.skullprogrammer.taxicab.model.customer.Wallet;

import java.time.LocalDate;

@Data
@Builder
@RegisterForReflection
public class Profile {

    private ObjectId id;
    @NotNull
    private ObjectId userId;
    @NotBlank
    private String name;
    @NotBlank
    private String lastName;
    @NotNull
    @Past
    private LocalDate birth;
    @NotNull
    private Wallet wallet;
}
