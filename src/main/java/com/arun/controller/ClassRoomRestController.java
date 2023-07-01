package com.arun.controller;

import com.arun.exception.MyCustomException;
import com.arun.request.ClassRoomRequest;
import com.arun.service.ClassRoomService;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
@RequestMapping("/api/classroom")
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ClassRoomRestController {

    ClassRoomService classRoomService;

    @GetMapping
    public ResponseEntity<Object> finaAll() {
        return ResponseEntity.ok(classRoomService.findAll());
    }

    @GetMapping("/getClassRoomDropdownResponse")
    public ResponseEntity<Object> getClassRoomDropdownResponse(String name) {
        return ResponseEntity.ok(classRoomService.getDropdownResponseByName(name));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> getById(@PathVariable Long id) throws MyCustomException {
        return ResponseEntity.ok(classRoomService.getById(id));
    }

    @PostMapping
    public ResponseEntity<Object> save(@Valid @RequestBody ClassRoomRequest request) throws MyCustomException {
        return new ResponseEntity(classRoomService.save(request), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Object> save(@PathVariable Long id, @Valid @RequestBody ClassRoomRequest request) throws MyCustomException {
        return ResponseEntity.ok(classRoomService.update(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteById(@PathVariable Long id) throws MyCustomException {
        return ResponseEntity.ok(classRoomService.deleteById(id));
    }

}
