package com.arun.response;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.math.BigDecimal;

@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = lombok.AccessLevel.PRIVATE)
public class ClassRoomResponse {

    Long id;

    String name;

    BigDecimal fee;

    Integer studentCount;
}
