package tech.skullprogrammer.taxicab.model.customer;

import lombok.*;
import org.bson.types.ObjectId;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class ExpiredPromo {

    private ObjectId _id;
    private ObjectId promoId;
    private ObjectId customerId;
    private LocalDateTime archivedAt;

}
