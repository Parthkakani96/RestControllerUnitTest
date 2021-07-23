package com.weetechsolution.RestControllerUnitTest.controller;

import com.weetechsolution.RestControllerUnitTest.dto.Student;
import com.weetechsolution.RestControllerUnitTest.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/student")
public class StudentController {

    @Autowired
    private StudentService studentService;

    @GetMapping
    public ResponseEntity<List<Student>> getStudents() {
        List<Student> students = studentService.getStudent();
        return new ResponseEntity<>(students, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Student> saveStudent(@RequestBody Student student) {
        student = studentService.saveStudent(student);
        return new ResponseEntity<>(student, HttpStatus.CREATED);
    }

    @PutMapping
    public ResponseEntity<Student> updateStudent(@RequestBody Student student) {
        student = studentService.updateStudent(student);
        return new ResponseEntity<>(student,HttpStatus.OK);
    }

    @DeleteMapping
    public ResponseEntity<String> deleteStudent(@RequestParam("student-id") String studentId) {
        String response = studentService.deleteStudent(studentId);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
