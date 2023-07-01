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
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

class StudentServiceImplTest {

    @Mock
    private StudentRepository studentRepository;

    @Mock
    private ClassRoomService classRoomService;

    @InjectMocks
    private StudentServiceImpl studentServiceImpl;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    private List<Student> getStudents() {
        List<Student> students = new ArrayList<>();
        students.add(new Student(1L, "name - 1", "address - 1", LocalDate.now(), ClassRoom.builder().id(1L).name("I").build(), new Date(), new Date()));
        students.add(new Student(2L, "name - 2", "address - 2", LocalDate.now(), ClassRoom.builder().id(2L).name("II").build(), new Date(), new Date()));
        students.add(new Student(3L, "name - 3", "address - 3", LocalDate.now(), ClassRoom.builder().id(1L).name("I").build(), new Date(), new Date()));
        return students;
    }

    @Test
    public void testFindAll() {
        List<Student> students = getStudents();

        Mockito.when(studentRepository.findAll()).thenReturn(students);

        List<StudentResponse> studentResponses = studentServiceImpl.findAll();

        Assertions.assertEquals(3, studentResponses.size());

        StudentResponse response1 = studentResponses.get(0);
        Assertions.assertEquals(1L, response1.getId());
        Assertions.assertEquals("name - 1", response1.getName());
        Assertions.assertEquals("address - 1", response1.getAddress());
        Assertions.assertEquals(LocalDate.now(), response1.getDob());
        Assertions.assertEquals(1L, response1.getClassRoomId());
        Assertions.assertEquals("I", response1.getClassRoomName());
    }

    @Test
    public void testFindByClassRoomId() {
        Long classRoomId = 1L;
        List<Student> students = getStudents().stream().filter(x -> x.getClassRoom().getId() == classRoomId ) .toList();
        Mockito.when(studentRepository.findByClassRoomId(classRoomId)).thenReturn(students);

        List<StudentResponse> studentResponses = studentServiceImpl.findByClassRoomId(1L);

        Assertions.assertEquals(2, studentResponses.size());

        StudentResponse response = studentResponses.get(0);
        Assertions.assertEquals(1L, response.getClassRoomId());
        Assertions.assertEquals("I", response.getClassRoomName());
    }

    @Test
    public void testGetById() throws MyCustomException {
        Student student = getStudents().get(0);

        Mockito.when(studentRepository.findById(1L)).thenReturn(Optional.of(student));

        StudentResponse studentResponse = studentServiceImpl.getById(1L);

        Assertions.assertNotNull(studentResponse);
        Assertions.assertEquals(1L, studentResponse.getId());
        Mockito.verify(studentRepository, Mockito.times(1)).findById(1L);
    }

    @Test
    public void testGetByIdNotExistingId() {
        Mockito.when(studentRepository.findById(1L)).thenReturn(Optional.empty());

        MyCustomException exception = Assertions.assertThrows(MyCustomException.class, () -> studentServiceImpl.getById(1L));

        Assertions.assertEquals(ExceptionCode.NOT_FOUND, exception.getCode());
        Assertions.assertEquals(ExceptionType.INFO, exception.getType());
        Assertions.assertEquals("Student not found", exception.getMessage());

        Mockito.verify(studentRepository, Mockito.times(1)).findById(1L);
    }

    @Test
    public void testSave() throws MyCustomException {
        StudentRequest request = new StudentRequest();
        request.setName("John");
        request.setAddress("123 Main St");
        request.setDob(LocalDate.now());
        request.setClassRoomId(1L);

        ClassRoom classRoom = ClassRoom.builder().id(1L).name("Math").build();
        Student studentToSave = new Student();
        studentToSave.setName("John");
        studentToSave.setAddress("123 Main St");
        studentToSave.setDob(LocalDate.now());
        studentToSave.setClassRoom(classRoom);

        Mockito.when(classRoomService.findById(1L)).thenReturn(classRoom);
        Mockito.when(studentRepository.save(Mockito.any(Student.class))).thenReturn(studentToSave);

        Long savedStudentId = studentServiceImpl.save(request);

        Assertions.assertEquals(studentToSave.getId(), savedStudentId);

        Mockito.verify(studentRepository, Mockito.times(1)).save(Mockito.any(Student.class));
        Mockito.verify(classRoomService, Mockito.times(1)).findById(1L);
    }

    @Test
    public void testUpdate() throws MyCustomException {
        StudentRequest request = new StudentRequest();
        request.setName("John");
        request.setAddress("123 Main St");
        request.setDob(LocalDate.now());
        request.setClassRoomId(1L);

        ClassRoom classRoom = ClassRoom.builder().id(1L).name("Math").build();
        ClassRoom classRoom2 = ClassRoom.builder().id(2L).name("Science").build();
        Student existingStudent = new Student(1L, "Jane", "456 Elm St", LocalDate.now(), classRoom2, new Date(), new Date());
        Student updatedStudent = new Student(1L, "John", "123 Main St", LocalDate.now(), classRoom,new Date(), new Date());

        Mockito.when(classRoomService.findById(1L)).thenReturn(classRoom);
        Mockito.when(studentRepository.save(Mockito.any(Student.class))).thenReturn(updatedStudent);
        Mockito.when(studentRepository.findById(1L)).thenReturn(Optional.of(existingStudent));

        Long updatedStudentId = studentServiceImpl.update(1L, request);

        Assertions.assertEquals(updatedStudent.getId(), updatedStudentId);

        Mockito.verify(studentRepository, Mockito.times(1)).findById(1L);
        Mockito.verify(studentRepository, Mockito.times(1)).save(Mockito.any(Student.class));
        Mockito.verify(classRoomService, Mockito.times(1)).findById(1L);
    }

    @Test
    public void testUpdateClassRoomNotExists() throws MyCustomException {
        StudentRequest request = new StudentRequest();
        request.setName("John");
        request.setAddress("123 Main St");
        request.setDob(LocalDate.now());
        request.setClassRoomId(3L);

        ClassRoom classRoom = ClassRoom.builder().id(1L).name("Math").build();
        Student existingStudent = new Student(1L, "Jane", "456 Elm St", LocalDate.now(), classRoom, new Date(), new Date());

        Mockito.when(studentRepository.findById(1L)).thenReturn(Optional.of(existingStudent));
        Mockito.when(classRoomService.findById(3L)).thenThrow(new MyCustomException(ExceptionCode.NOT_FOUND, ExceptionType.INFO, "Class room not found"));

        MyCustomException exception = Assertions.assertThrows(MyCustomException.class, () -> studentServiceImpl.update(1L, request));

        Assertions.assertEquals(ExceptionCode.NOT_FOUND, exception.getCode());
        Assertions.assertEquals(ExceptionType.INFO, exception.getType());
        Assertions.assertEquals("Class room not found", exception.getMessage());

        Mockito.verify(studentRepository, Mockito.times(1)).findById(1L);
        Mockito.verify(classRoomService, Mockito.times(1)).findById(3L);
    }

    @Test
    public void testDeleteById() throws MyCustomException {
        String result = studentServiceImpl.deleteById(1L);

        Assertions.assertEquals("Student deleted successfully", result);

        Mockito.verify(studentRepository, Mockito.times(1)).deleteById(1L);
    }
}