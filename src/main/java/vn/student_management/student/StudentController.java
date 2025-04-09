package vn.student_management.student;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import vn.student_management.APIReponse.ApiResponse;
import vn.student_management.APIReponse.ResponseBuilder;
import vn.student_management.mapper.StudentMapper;
import vn.student_management.studentInfor.StudentInfo;
import vn.student_management.studentInfor.StudentInfoRequestDTO;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/students")
@AllArgsConstructor
public class StudentController {

    private final StudentService studentService;
    private final StudentMapper studentMapper;

    @GetMapping
    public ResponseEntity<ApiResponse<List<StudentResponseDTO>>> getAllStudents() {
        List<StudentResponseDTO> students = studentService.getAllStudents()
                .stream()
                .map(studentMapper::toStudentResponse)
                .collect(Collectors.toList());

        if (students.isEmpty()) {
            return ResponseBuilder.build(HttpStatus.NO_CONTENT, "No students found", null);
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

    @PutMapping("/{studentId}")
    public ResponseEntity<ApiResponse<StudentResponseDTO>> updateStudent(
            @PathVariable int studentId,
            @RequestBody StudentRequestDTO studentRequest) {

        Student student = studentService.getStudentById(studentId);
        student.setStudentName(studentRequest.getStudentName());
        student.setStudentCode(studentRequest.getStudentCode());

        StudentInfoRequestDTO infoDTO = studentRequest.getStudentInfo();
        StudentInfo currentInfo = student.getStudentInfo();
        currentInfo.setAddress(infoDTO.getAddress());
        currentInfo.setAverageScore(infoDTO.getAverageScore());
        currentInfo.setDateOfBirth(infoDTO.getDateOfBirth());

        Student updatedStudent = studentService.updateStudent(studentId, student);

        StudentResponseDTO studentResponse = studentMapper.toStudentResponse(updatedStudent);

        return ResponseBuilder.build(HttpStatus.OK, "Update successfully student", studentResponse);
    }

    @DeleteMapping("/{studentId}")
    public ResponseEntity<ApiResponse<Void>> deleteStudent(@PathVariable int studentId) {
        studentService.deleteStudent(studentId);
        return ResponseBuilder.build(HttpStatus.OK, "Delete successfully student", null);
    }

}
