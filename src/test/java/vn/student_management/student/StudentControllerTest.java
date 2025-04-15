package vn.student_management.student;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import vn.student_management.exception.GlobalExceptionHandler;
import vn.student_management.mapper.StudentMapper;
import vn.student_management.studentInfor.StudentInfo;
import vn.student_management.studentInfor.StudentInfoRequestDTO;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


class StudentControllerTest {

    @Mock
    private StudentService studentService;

    @Mock
    private StudentMapper studentMapper;



    @InjectMocks
    private StudentController studentController;

    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders
                .standaloneSetup(studentController)
                .setControllerAdvice(new GlobalExceptionHandler())
                .build();
        objectMapper = new ObjectMapper();
    }

    @Test
    void testGetAllStudents_ReturnsList() throws Exception {
        Student student = new Student();
        student.setStudentId(1);

        StudentResponseDTO dto = new StudentResponseDTO();
        dto.setStudentId(1);

        when(studentService.getAllStudents()).thenReturn(List.of(student));
        when(studentMapper.toStudentResponse(student)).thenReturn(dto);

        mockMvc.perform(get("/students"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data[0].studentId").value(1));
    }

    @Test
    void testGetAllStudents_Empty_ReturnsNoContent() throws Exception {
        when(studentService.getAllStudents()).thenReturn(Collections.emptyList());

        mockMvc.perform(get("/students"))
                .andExpect(status().isNoContent())
                .andExpect(jsonPath("$.data").doesNotExist());
    }

    @Test
    void testGetStudentById_ReturnsStudent() throws Exception {
        int studentId = 1;
        Student student = new Student(); student.setStudentId(studentId);
        StudentResponseDTO dto = new StudentResponseDTO(); dto.setStudentId(studentId);

        when(studentService.getStudentById(studentId)).thenReturn(student);
        when(studentMapper.toStudentResponse(student)).thenReturn(dto);

        mockMvc.perform(get("/students/{id}", studentId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.studentId").value(studentId));
    }

    @Test
    void testCreateStudent_Success() throws Exception {
        StudentRequestDTO request = new StudentRequestDTO();
        request.setStudentName("John");
        request.setStudentCode("S123");

        StudentInfoRequestDTO infoRequest = new StudentInfoRequestDTO();
        infoRequest.setAddress("Hanoi");
        infoRequest.setAverageScore(8.5);
        infoRequest.setDateOfBirth(LocalDateTime.of(2000, 1, 1, 0, 0));
        request.setStudentInfo(infoRequest);

        Student mapped = new Student(); mapped.setStudentId(1);
        Student created = new Student(); created.setStudentId(1);

        StudentResponseDTO dto = new StudentResponseDTO(); dto.setStudentId(1);

        when(studentMapper.toStudent(request)).thenReturn(mapped);
        when(studentService.createStudent(mapped)).thenReturn(created);
        when(studentMapper.toStudentResponse(created)).thenReturn(dto);

        mockMvc.perform(post("/students")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.data.studentId").value(1));
    }

    @Test
    void testUpdateStudent_Success() throws Exception {
        int studentId = 1;

        StudentRequestDTO request = new StudentRequestDTO();
        request.setStudentName("Updated");
        request.setStudentCode("U123");
        StudentInfoRequestDTO infoRequest = new StudentInfoRequestDTO();
        infoRequest.setAddress("New Address");
        infoRequest.setAverageScore(9.0);
        infoRequest.setDateOfBirth(LocalDateTime.of(2001, 2, 2, 0, 0));
        request.setStudentInfo(infoRequest);

        Student existing = new Student();
        existing.setStudentId(studentId);
        StudentInfo info = new StudentInfo();
        existing.setStudentInfo(info);

        Student updated = new Student();
        updated.setStudentId(studentId);
        StudentResponseDTO dto = new StudentResponseDTO();
        dto.setStudentId(studentId);

        when(studentService.getStudentById(studentId)).thenReturn(existing);
        when(studentService.updateStudent(eq(studentId), any(Student.class))).thenReturn(updated);
        when(studentMapper.toStudentResponse(updated)).thenReturn(dto);

        mockMvc.perform(put("/students/{id}", studentId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.studentId").value(studentId));
    }

    @Test
    void testDeleteStudent_Success() throws Exception {
        int studentId = 1;

        doNothing().when(studentService).deleteStudent(studentId);

        mockMvc.perform(delete("/students/{id}", studentId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value("Delete successfully student"));
    }
}
