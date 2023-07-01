package com.arun.service;

import com.arun.entity.ClassRoom;
import com.arun.exception.MyCustomException;
import com.arun.request.ClassRoomRequest;
import com.arun.response.ClassRoomResponse;
import com.arun.response.DropdownResponse;

import java.util.List;

public interface ClassRoomService {

    List<ClassRoomResponse> findAll();

    List<DropdownResponse> getDropdownResponseByName(String name);

    ClassRoomResponse getById(Long id) throws MyCustomException;

    Long save(ClassRoomRequest request) throws MyCustomException;

    Long update(Long id, ClassRoomRequest request) throws MyCustomException;

    String deleteById(Long id) throws MyCustomException;

    ClassRoom findById(Long classRoomId) throws MyCustomException;
}
