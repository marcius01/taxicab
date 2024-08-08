package tech.skullprogrammer.taxicab.model.customer;

import lombok.*;
import org.bson.types.ObjectId;
import tech.skullprogrammer.taxicab.model.Location;
import tech.skullprogrammer.taxicab.model.enums.ERideStatus;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class RideRequest {

    private ObjectId _id;
    private ObjectId customerId;
    private ERideStatus status;
    private LocalDateTime requestedAt;
    private LocalDateTime pickupTime;
    private Location pickupLocation;
    private Location destination;
}
