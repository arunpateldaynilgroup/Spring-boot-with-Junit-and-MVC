package com.arun.controller;

import com.arun.exception.MyCustomException;
import com.arun.request.StudentRequest;
import com.arun.service.StudentService;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
@RequestMapping("/api/student")
@FieldDefaults(level = AccessLevel.PRIVATE)
public class StudentRestController {

    StudentService studentService;

    @GetMapping
    public ResponseEntity<Object> finaAll() {
        return ResponseEntity.ok(studentService.findAll());
    }

    @GetMapping("/findByClassRoomId/{classRoomId}")
    public ResponseEntity<Object> findByClassRoomId(@PathVariable Long classRoomId) {
        return ResponseEntity.ok(studentService.findByClassRoomId(classRoomId));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> getById(@PathVariable Long id) throws MyCustomException {
        return ResponseEntity.ok(studentService.getById(id));
    }

    @PostMapping
    public ResponseEntity<Object> save(@Valid @RequestBody StudentRequest request) throws MyCustomException {
        return new ResponseEntity(studentService.save(request), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Object> save(@PathVariable Long id, @Valid @RequestBody StudentRequest request) throws MyCustomException {
        return ResponseEntity.ok(studentService.update(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteById(@PathVariable Long id) throws MyCustomException {
        return ResponseEntity.ok(studentService.deleteById(id));
    }

}
