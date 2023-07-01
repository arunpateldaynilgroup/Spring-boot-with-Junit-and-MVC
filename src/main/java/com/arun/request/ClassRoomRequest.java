package com.arun.request;

import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

import java.math.BigDecimal;

@Setter
@Getter
@FieldDefaults(level = lombok.AccessLevel.PRIVATE)
public class ClassRoomRequest {

    @NotEmpty(message = "name is mandatory")
    String name;

    BigDecimal fee = BigDecimal.ZERO;

}
