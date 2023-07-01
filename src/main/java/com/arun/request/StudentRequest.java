package com.arun.request;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;

@Setter
@Getter
@FieldDefaults(level = lombok.AccessLevel.PRIVATE)
public class StudentRequest {

    @NotEmpty(message = "name is mandatory")
    String name;

    String address;

    @NotNull(message = "DOB is mandatory")
    LocalDate dob;

    @NotNull(message = "ClassRoomId is mandatory")
    Long classRoomId;

}
