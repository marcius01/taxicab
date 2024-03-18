package tech.skullprogrammer.taxicab.model.customer;

import lombok.*;
import org.bson.types.ObjectId;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class AppliedPromo {

    //TODO maybe not necessary
    private ObjectId promoId;
    private int usageCount;
    private double usedValue;
    private String description;
}
