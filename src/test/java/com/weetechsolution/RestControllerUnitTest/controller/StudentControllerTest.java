package com.weetechsolution.RestControllerUnitTest.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.weetechsolution.RestControllerUnitTest.dto.Student;
import com.weetechsolution.RestControllerUnitTest.service.StudentService;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class StudentControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper mapper;

    @MockBean
    private StudentService studentService;

    @Test
    @Order(1)
    public void testGetExample() throws Exception {
        List<Student> students = new ArrayList<>();
        Student student = new Student();
        student.setId(1);
        student.setName("Arun");
        students.add(student);

        Mockito.when(studentService.getStudent()).thenReturn(students);

        mockMvc.perform(get("/student"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", Matchers.hasSize(1)))
                .andExpect(jsonPath("$[0].name", Matchers.equalTo("Arun")));
    }

    @Test
    @Order(2)
    public void testPostExample() throws Exception {
        Student student = new Student();
        student.setId(1);
        student.setName("Arun");

        Mockito.when(studentService.saveStudent(ArgumentMatchers.any())).thenReturn(student);
        String json = mapper.writeValueAsString(student);

        mockMvc.perform(post("/student").contentType(MediaType.APPLICATION_JSON).characterEncoding("utf-8").content(json).accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id", Matchers.equalTo(1)))
                .andExpect(jsonPath("$.name", Matchers.equalTo("Arun")));
    }

    @Test
    @Order(3)
    public void testPutExample() throws Exception {
        Student student = new Student();
        student.setId(2);
        student.setName("test");

        Mockito.when(studentService.updateStudent(ArgumentMatchers.any())).thenReturn(student);
        String json = mapper.writeValueAsString(student);

        mockMvc.perform(put("/student").contentType(MediaType.APPLICATION_JSON).characterEncoding("utf-8").content(json).accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", Matchers.equalTo(2)))
                .andExpect(jsonPath("$.name", Matchers.equalTo("test")));
    }

    @Test
    @Order(4)
    public void testDeleteExample() throws Exception {

        Mockito.when(studentService.deleteStudent(ArgumentMatchers.anyString())).thenReturn("Student is deleted");
        MvcResult requestResult = mockMvc.perform(delete("/student").param("student-id", "1"))
                .andExpect(status().isOk())
                .andExpect(status().isOk())
                .andReturn();

        String result = requestResult.getResponse().getContentAsString();
        assertEquals(result, "Student is deleted");
    }
}