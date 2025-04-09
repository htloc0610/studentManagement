package vn.student_management.student;

import org.springframework.beans.factory.annotation.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import vn.student_management.APIReponse.ApiResponse;
import vn.student_management.APIReponse.ResponseBuilder;
import vn.student_management.mapper.StudentMapper;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/students")
public class StudentController {
    @Autowired
    private StudentService studentService;

    @Autowired
    private StudentMapper studentMapper;

    @GetMapping
    public ResponseEntity<ApiResponse<List<StudentResponseDTO>>> getAllStudents() {
        List<StudentResponseDTO> students = studentService.getAllStudents()
                .stream()
                .map(studentMapper::toStudentResponse)
                .collect(Collectors.toList());

        if (students.isEmpty()) {
            return ResponseBuilder.build(HttpStatus.NO_CONTENT, "No students found");
        }

        return ResponseBuilder.build(HttpStatus.OK, "Get successfully students list", students);
    }

    @GetMapping("/{studentId}")
    public ResponseEntity<ApiResponse<StudentResponseDTO>> getStudentById(@PathVariable int studentId) {
        Student student = studentService.getStudentById(studentId);
        StudentResponseDTO studentResponse = studentMapper.toStudentResponse(student);

        return ResponseBuilder.build(HttpStatus.OK, "Get successfully student", studentResponse);
    }

    @PostMapping
    public ResponseEntity<ApiResponse<StudentResponseDTO>> createStudent(@RequestBody StudentRequestDTO studentRequest) {
        Student student = studentMapper.toStudent(studentRequest);
        Student createdStudent = studentService.createStudent(student);
        StudentResponseDTO studentResponse = studentMapper.toStudentResponse(createdStudent);

        return ResponseBuilder.build(HttpStatus.CREATED, "Create successfully student", studentResponse);
    }
}
