package com.arun.service.impl;

import com.arun.entity.ClassRoom;
import com.arun.entity.Student;
import com.arun.exception.ExceptionCode;
import com.arun.exception.MyCustomException;
import com.arun.repository.ClassRoomRepository;
import com.arun.request.ClassRoomRequest;
import com.arun.response.ClassRoomResponse;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.*;

class ClassRoomServiceImplTest {

    @Mock
    private ClassRoomRepository classRoomRepository;

    @InjectMocks
    private ClassRoomServiceImpl classRoomService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    private ClassRoom getClassRoom(Long id) {
        ClassRoom classRoom = new ClassRoom();
        classRoom.setId(1L);
        classRoom.setName("Class A");
        classRoom.setFee(BigDecimal.valueOf(1000));

        List<Student> students = new ArrayList<>();
        students.add(new Student(1l, "name 1", "address 1",LocalDate.now(), classRoom, new Date(), new Date()));
        students.add(new Student(2l, "name 2", "address 2",LocalDate.now(), classRoom, new Date(), new Date()));

        classRoom.setStudents(students);
        return classRoom;
    }

    @Test
    void testFindAll() {
        ClassRoom classRoom = getClassRoom(1L);

        Mockito.when(classRoomRepository.findAll()).thenReturn(Collections.singletonList(classRoom));

        List<ClassRoomResponse> responseList = classRoomService.findAll();

        Assertions.assertEquals(1, responseList.size());
        ClassRoomResponse response = responseList.get(0);
        Assertions.assertEquals(classRoom.getId(), response.getId());
        Assertions.assertEquals(classRoom.getName(), response.getName());
        Assertions.assertEquals(classRoom.getFee(), response.getFee());
        Assertions.assertEquals(classRoom.getStudents().size(), response.getStudentCount());
    }

    @Test
    void testGetByIdWithValidId() throws MyCustomException {
        Long id = 1L;
        ClassRoom classRoom = getClassRoom(id);

        Mockito.when(classRoomRepository.findById(id)).thenReturn(Optional.of(classRoom));

        ClassRoomResponse response = classRoomService.getById(id);

        Assertions.assertNotNull(response);
        Assertions.assertEquals(classRoom.getId(), response.getId());
        Assertions.assertEquals(classRoom.getName(), response.getName());
        Assertions.assertEquals(classRoom.getFee(), response.getFee());
        Assertions.assertEquals(classRoom.getStudents().size(), response.getStudentCount());
    }

    @Test
    void testGetByIdWithInValidId() {
        Long id = 1L;
        Mockito.when(classRoomRepository.findById(id)).thenReturn(Optional.empty());

        MyCustomException exception = Assertions.assertThrows(MyCustomException.class, () -> classRoomService.getById(id));
        Assertions.assertEquals(ExceptionCode.NOT_FOUND, exception.getCode());
        Assertions.assertEquals("ClassRoom not found", exception.getMessage());
    }

    private ClassRoomRequest getClassRoomRequest() {
        ClassRoomRequest request = new ClassRoomRequest();
        request.setName("Class A");
        request.setFee(BigDecimal.valueOf(1000));
        return request;
    }

    @Test
    void testSaveWithoutException() throws MyCustomException {

        ClassRoomRequest request = getClassRoomRequest();

        ClassRoom savedClassRoom = new ClassRoom();
        savedClassRoom.setId(1L);
        savedClassRoom.setName("Class A");
        request.setFee(BigDecimal.valueOf(1000));

        Mockito.when(classRoomRepository.existsByName(request.getName())).thenReturn(false);
        Mockito.when(classRoomRepository.save(Mockito.any(ClassRoom.class))).thenReturn(savedClassRoom);

        Long savedId = classRoomService.save(request);

        Assertions.assertNotNull(savedId);
        Assertions.assertEquals(savedClassRoom.getId(), savedId);
        Mockito.verify(classRoomRepository, Mockito.times(1)).existsByName(request.getName());
        Mockito.verify(classRoomRepository, Mockito.times(1)).save(Mockito.any(ClassRoom.class));
    }

    @Test
    void testSaveWithDuplicateClassName() {

        ClassRoomRequest request = getClassRoomRequest();

        Mockito.when(classRoomRepository.existsByName(request.getName())).thenReturn(true);

        MyCustomException exception = Assertions.assertThrows(MyCustomException.class, () -> classRoomService.save(request));
        Assertions.assertEquals(ExceptionCode.ALREADY_EXISTS, exception.getCode());
        Assertions.assertEquals("ClassRoom Name alredy exists", exception.getMessage());
    }

    @Test
    void testUpdate() throws MyCustomException {
        Long id = 2L;
        ClassRoomRequest request = new ClassRoomRequest();
        request.setName("Class B");
        request.setFee(BigDecimal.valueOf(1500));

        ClassRoom existingClassRoom = getClassRoom(1l);

        ClassRoom updatedClassRoom = new ClassRoom();
        updatedClassRoom.setId(2l);
        updatedClassRoom.setName("Class B");
        updatedClassRoom.setFee(BigDecimal.valueOf(1500));

        Mockito.when(classRoomRepository.findById(id)).thenReturn(Optional.of(existingClassRoom));
        Mockito.when(classRoomRepository.existsByName(request.getName())).thenReturn(false);
        Mockito.when(classRoomRepository.save(Mockito.any(ClassRoom.class))).thenReturn(updatedClassRoom);

        Long updatedId = classRoomService.update(id, request);

        Assertions.assertNotNull(updatedId);
        Assertions.assertEquals(updatedClassRoom.getId(), updatedId);
        Assertions.assertEquals(request.getName(), updatedClassRoom.getName());
        Assertions.assertEquals(request.getFee(), updatedClassRoom.getFee());
        Mockito.verify(classRoomRepository, Mockito.times(1)).findById(id);
        Mockito.verify(classRoomRepository, Mockito.times(1)).existsByName(request.getName());
        Mockito.verify(classRoomRepository, Mockito.times(1)).save(Mockito.any(ClassRoom.class));
    }

    @Test
    void updateWithNonExistingId() {
        Long id = 1L;
        ClassRoomRequest request = new ClassRoomRequest();
        request.setName("Class B");
        request.setFee(BigDecimal.valueOf(1500));

        Mockito.when(classRoomRepository.findById(id)).thenReturn(Optional.empty());

        Assertions.assertThrows(MyCustomException.class, () -> classRoomService.update(id, request));
        Mockito.verify(classRoomRepository, Mockito.times(1)).findById(id);
        Mockito.verifyNoMoreInteractions(classRoomRepository);
    }

    @Test
    void testUpdateWithDuplicateClassName() {
        Long id = 1L;
        ClassRoomRequest request = new ClassRoomRequest();
        request.setName("Class B");
        request.setFee(BigDecimal.valueOf(1500));

        ClassRoom existingClassRoom = getClassRoom(id);

        Mockito.when(classRoomRepository.findById(id)).thenReturn(Optional.of(existingClassRoom));
        Mockito.when(classRoomRepository.existsByName(request.getName())).thenReturn(true);

        Assertions.assertThrows(MyCustomException.class, () -> classRoomService.update(id, request));
        Mockito.verify(classRoomRepository, Mockito.times(1)).findById(id);
        Mockito.verify(classRoomRepository, Mockito.times(1)).existsByName(request.getName());
        Mockito.verifyNoMoreInteractions(classRoomRepository);
    }

    @Test
    void testDeleteById() throws MyCustomException {
        Long id = 1L;
        ClassRoom classRoom = getClassRoom(id);
        classRoom.setStudents(Collections.emptyList());

        Mockito.when(classRoomRepository.findById(id)).thenReturn(Optional.of(classRoom));
        Mockito.when(classRoomRepository.existsByName(Mockito.anyString())).thenReturn(false);

        String message = classRoomService.deleteById(id);

        Assertions.assertNotNull(message);
        Assertions.assertEquals("ClassRoom deleted successfully", message);
        Mockito.verify(classRoomRepository, Mockito.times(1)).findById(id);
        Mockito.verify(classRoomRepository, Mockito.times(1)).delete(classRoom);
    }

    @Test
    void testDeleteByIdWithValidIdAndStudentsExist() {
        Long id = 1L;
        ClassRoom classRoom = getClassRoom(id);
        Mockito.when(classRoomRepository.findById(id)).thenReturn(Optional.of(classRoom));

        Assertions.assertThrows(MyCustomException.class, () -> classRoomService.deleteById(id));
        Mockito.verify(classRoomRepository, Mockito.times(1)).findById(id);
        Mockito.verifyNoMoreInteractions(classRoomRepository);
    }

    @Test
    void testDeleteByIdWithNonExistingId() {
        Long id = 1L;

        Mockito.when(classRoomRepository.findById(id)).thenReturn(Optional.empty());

        Assertions.assertThrows(MyCustomException.class, () -> classRoomService.deleteById(id));
        Mockito.verify(classRoomRepository, Mockito.times(1)).findById(id);
        Mockito.verifyNoMoreInteractions(classRoomRepository);
    }
}