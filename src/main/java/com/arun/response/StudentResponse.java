package com.arun.response;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;
import java.util.Date;

@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = lombok.AccessLevel.PRIVATE)
public class StudentResponse {

    Long id;

    String name;

    String address;

    LocalDate dob;

    Long classRoomId;

    String classRoomName;

    Date createdDate;

}
