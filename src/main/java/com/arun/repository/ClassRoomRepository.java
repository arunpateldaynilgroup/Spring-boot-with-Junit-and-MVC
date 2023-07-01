package com.arun.repository;

import com.arun.entity.ClassRoom;
import com.arun.response.DropdownResponse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ClassRoomRepository extends JpaRepository<ClassRoom, Long> {

    boolean existsByName(String name);

    // COALESCE is apply on if value is not null
    @Query("SELECT c.id AS id, c.name AS name FROM ClassRoom c WHERE c.name LIKE COALESCE(CONCAT('%',:name,'%'), c.name)")
    List<DropdownResponse> findDropdownResponseByName(String name);
}
