package com.tiki.profile.entity;

import lombok.*;
import lombok.experimental.FieldDefaults;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
@Document(collation = "profile")
public class Profile {

    String id;
    String userId;
    String email;
    String username;
    String firstName;
    String lastName;
    String avatar;
    LocalDate dob;
    String city;
}
