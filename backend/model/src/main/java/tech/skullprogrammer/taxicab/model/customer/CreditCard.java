package tech.skullprogrammer.taxicab.model.customer;

import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class CreditCard {

    @NotNull
    @Size(min = 16, max = 16)
    public String cardNumber;

    @Future
    public LocalDate expirationDate;

    @NotNull
    @Size(min = 3, max = 4)
    public String cvv;
}
