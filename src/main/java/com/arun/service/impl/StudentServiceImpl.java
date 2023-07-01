package com.arun.service.impl;

import com.arun.entity.ClassRoom;
import com.arun.entity.Student;
import com.arun.exception.ExceptionCode;
import com.arun.exception.ExceptionType;
import com.arun.exception.MyCustomException;
import com.arun.repository.StudentRepository;
import com.arun.request.StudentRequest;
import com.arun.response.StudentResponse;
import com.arun.service.ClassRoomService;
import com.arun.service.StudentService;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
@FieldDefaults(level = lombok.AccessLevel.PRIVATE)
public class StudentServiceImpl implements StudentService {

    StudentRepository studentRepository;
    ClassRoomService classRoomService;

    @Override
    public List<StudentResponse> findAll() {
        return studentRepository.findAll().stream()
                .map(this::getStudentResponse)
                .toList();
    }

    @Override
    public List<StudentResponse> findByClassRoomId(Long classRoomId) {
        return studentRepository.findByClassRoomId(classRoomId).stream()
                .map(this::getStudentResponse)
                .toList();
    }

    @Override
    public StudentResponse getById(Long id) throws MyCustomException {
        return getStudentResponse(findById(id));
    }

    private Student findById(Long id) throws MyCustomException {
        return studentRepository.findById(id).orElseThrow(() -> new MyCustomException(ExceptionCode.NOT_FOUND, ExceptionType.INFO, "Student not found"));
    }

    @Override
    public Long save(StudentRequest request) throws MyCustomException {
        Student student = new Student();
        return studentRepository.save(getStudent(student, request)).getId();
    }

    private Student getStudent(Student student, StudentRequest request) throws MyCustomException {
        student.setName(request.getName());
        student.setAddress(request.getAddress());
        student.setDob(request.getDob());
        student.setClassRoom(classRoomService.findById(request.getClassRoomId()));
        return student;
    }

    @Override
    public Long update(Long id, StudentRequest request) throws MyCustomException {
        return studentRepository.save(getStudent(findById(id), request)).getId();
    }

    @Override
    public String deleteById(Long id) {
        studentRepository.deleteById(id);
        return "Student deleted successfully";
    }

    private StudentResponse getStudentResponse(Student student) {
        ClassRoom classRoom = student.getClassRoom();
        return StudentResponse.builder()
                .id(student.getId())
                .name(student.getName())
                .address(student.getAddress())
                .dob(student.getDob())
                .createdDate(student.getCreatedDate())
                .classRoomId(classRoom.getId())
                .classRoomName(classRoom.getName())
                .build();
    }
}
