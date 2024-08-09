package tech.skullprogrammer.taxicab.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import org.bson.types.ObjectId;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
public class User {

    private ObjectId id;
    private String username;
    private String name;
    private String email;
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String password;
    @Builder.Default
    private Set<String> roles = new HashSet<>();
    @Builder.Default
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private boolean disabled = false;
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String resetPasswordOtp;
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private LocalDateTime otpExpiration;


}
