package tech.skullprogrammer.taxicab.model;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Location {

    private Double longitude;
    private Double latitude;
    private String locationName;
}
