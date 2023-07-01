package com.arun.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDate;
import java.util.Date;

@Setter
@Getter
@Builder
@Entity
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Student {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    Long id;

    String name;

    String address;

    LocalDate dob;

    @ManyToOne(targetEntity = ClassRoom.class, fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "class_room_id", nullable = false)
    ClassRoom classRoom;

    @CreationTimestamp
    Date createdDate;

    @UpdateTimestamp
    Date updatedDate;

}
