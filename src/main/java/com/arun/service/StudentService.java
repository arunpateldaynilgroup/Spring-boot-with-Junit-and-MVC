package com.arun.service;

import com.arun.exception.MyCustomException;
import com.arun.request.StudentRequest;
import com.arun.response.StudentResponse;

import java.util.List;

public interface StudentService {

    List<StudentResponse> findAll();

    List<StudentResponse> findByClassRoomId(Long classRoomId);

    StudentResponse getById(Long id) throws MyCustomException;

    Long save(StudentRequest request) throws MyCustomException;

    Long update(Long id, StudentRequest request) throws MyCustomException;

    String deleteById(Long id);

}
