package tech.skullprogrammer.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.bson.types.ObjectId;

public class User {

    private ObjectId id;
    private String username;
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String password;


}
