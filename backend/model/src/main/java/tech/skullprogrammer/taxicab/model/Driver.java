package tech.skullprogrammer.taxicab.model;

import lombok.*;
import org.bson.types.ObjectId;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Driver {

    private ObjectId id;
    private ObjectId userId;



}
