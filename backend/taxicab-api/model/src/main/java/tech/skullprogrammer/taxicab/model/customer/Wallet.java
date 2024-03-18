package tech.skullprogrammer.taxicab.model.customer;

import lombok.*;
import org.bson.types.ObjectId;
import tech.skullprogrammer.taxicab.model.enums.ECurrency;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Wallet {

    public ObjectId customerId;
    public String description;
    public List<AppliedPromo> appliedPromos;
    public List<CreditCard> creditCards;
    public Double balance;
    public ECurrency currency;

}
