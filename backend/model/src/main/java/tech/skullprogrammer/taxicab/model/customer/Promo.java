package tech.skullprogrammer.taxicab.model.customer;

import lombok.*;
import org.bson.types.ObjectId;
import tech.skullprogrammer.taxicab.model.enums.ECurrency;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Promo {

    private ObjectId _id;
    private String description;
    private String area;
    private ECurrency currency;
    private Double maxValue;
    private Double discountPercentage;
    private Integer maxUsages;

    private LocalDateTime expireAt;

}
