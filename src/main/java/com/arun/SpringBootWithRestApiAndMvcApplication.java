package com.arun;

import com.arun.entity.ClassRoom;
import com.arun.entity.Student;
import com.arun.repository.ClassRoomRepository;
import com.arun.repository.StudentRepository;
import com.arun.request.StudentRequest;
import com.arun.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.LinkedList;
import java.util.List;

@SpringBootApplication
public class SpringBootWithRestApiAndMvcApplication
//        implements CommandLineRunner
{

    public static void main(String[] args) {
        SpringApplication.run(SpringBootWithRestApiAndMvcApplication.class, args);
    }


    //for frontend testing
//    @Autowired
//    ClassRoomRepository classRoomRepository;
//    @Override
//    public void run(String... args) throws Exception {
//        List<ClassRoom> classRooms = new LinkedList<>();
//        for(int i=0;i<20;i++) {
//            ClassRoom build = ClassRoom.builder().name("ClassRoom - " + i).fee(BigDecimal.valueOf(11101010)).build();
//            List<Student> students = new LinkedList<>();
//            students.add(Student.builder().name("Student A - " + i).address("Address A - " + i).dob(LocalDate.now()).classRoom(build).build());
//            students.add(Student.builder().name("Student B - " + i).address("Address B- " + i).dob(LocalDate.now()).classRoom(build).build());
//            build.setStudents(students);
//            classRooms.add(build);
//        }
//        classRoomRepository.saveAll(classRooms);
//    }
}
