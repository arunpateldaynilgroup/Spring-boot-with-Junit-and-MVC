package com.arun.service.impl;

import com.arun.entity.ClassRoom;
import com.arun.exception.ExceptionCode;
import com.arun.exception.ExceptionType;
import com.arun.exception.MyCustomException;
import com.arun.repository.ClassRoomRepository;
import com.arun.request.ClassRoomRequest;
import com.arun.response.ClassRoomResponse;
import com.arun.response.DropdownResponse;
import com.arun.service.ClassRoomService;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Locale;

@Service
@AllArgsConstructor
@FieldDefaults(level = lombok.AccessLevel.PRIVATE)
public class ClassRoomServiceImpl implements ClassRoomService {

    ClassRoomRepository classRoomRepository;

    @Override
    public List<ClassRoomResponse> findAll() {
        return classRoomRepository.findAll().stream()
                .map(this::getClassRoomResponse)
                .toList();
    }

    @Override
    public List<DropdownResponse> getDropdownResponseByName(String name) {
        return classRoomRepository.findDropdownResponseByName(name);
    }

    @Override
    public ClassRoomResponse getById(Long id) throws MyCustomException {
        return getClassRoomResponse(findById(id));
    }

    @Override
    public ClassRoom findById(Long id) throws MyCustomException {
        return classRoomRepository.findById(id).orElseThrow(() -> new MyCustomException(ExceptionCode.NOT_FOUND, ExceptionType.INFO, "ClassRoom not found"));
    }

    @Override
    public Long save(ClassRoomRequest request) throws MyCustomException {

        //Check if name already exists
        if(classRoomRepository.existsByName(request.getName()))
            throw new MyCustomException(ExceptionCode.ALREADY_EXISTS, ExceptionType.INFO, "ClassRoom Name alredy exists");

        ClassRoom classRoom = ClassRoom.builder().name(request.getName()).fee(request.getFee()).build();
        return classRoomRepository.save(classRoom).getId();
    }

    @Override
    public Long update(Long id, ClassRoomRequest request) throws MyCustomException {
        ClassRoom classRoom = findById(id);

        //Check if name is changed and new name already exists
        if(!classRoom.getName().equals(request.getName()) && classRoomRepository.existsByName(request.getName()))
            throw new MyCustomException(ExceptionCode.ALREADY_EXISTS, ExceptionType.INFO, "ClassRoom Name alredy exists");

        classRoom.setName(request.getName());
        classRoom.setFee(request.getFee());
        return classRoomRepository.save(classRoom).getId();
    }

    @Override
    public String deleteById(Long id) throws MyCustomException {
        ClassRoom classRoom = findById(id);
        if(!classRoom.getStudents().isEmpty()) {
            throw new MyCustomException(ExceptionCode.NOT_PROCESSED_FORWARD_BECAUSE_SOME_EXCEPTION_IS_OCCUR, ExceptionType.INFO, "ClassRoom has students. Please delete students first.");
        }
        classRoomRepository.delete(classRoom);
        return "ClassRoom deleted successfully";
    }

    private ClassRoomResponse getClassRoomResponse(ClassRoom classRoom) {
        return ClassRoomResponse.builder()
                .id(classRoom.getId())
                .name(classRoom.getName())
                .fee(classRoom.getFee())
                .studentCount(classRoom.getStudents().size())
                .build();
    }
}
