package vn.student_management.student;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.Valid;
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
    @Operation(summary = "Get all students", description = "Retrieve a list of all students from the system")
    @io.swagger.v3.oas.annotations.responses.ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "Successfully retrieved student list",
                    content = @Content(mediaType = "application/json",
                            array = @ArraySchema(schema = @Schema(implementation = StudentResponseDTO.class)))),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "204", description = "No students found",
                    content = @Content(mediaType = "application/json"))
    })
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
    @Operation(summary = "Create a new student", description = "Create a new student in the system")
    @io.swagger.v3.oas.annotations.responses.ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "201", description = "Student created successfully",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = StudentResponseDTO.class))),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "400", description = "Invalid request",
                    content = @Content(mediaType = "application/json"))
    })
    public ResponseEntity<ApiResponse<StudentResponseDTO>> createStudent(@RequestBody @Valid StudentRequestDTO studentRequest) {
        Student student = studentMapper.toStudent(studentRequest);
        Student createdStudent = studentService.createStudent(student);
        StudentResponseDTO studentResponse = studentMapper.toStudentResponse(createdStudent);

        return ResponseBuilder.build(HttpStatus.CREATED, "Create successfully student", studentResponse);
    }

    @PutMapping("/{studentId}")
    @Operation(summary = "Update student", description = "Update an existing student's information")
    @io.swagger.v3.oas.annotations.responses.ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "Student updated successfully",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = StudentResponseDTO.class))),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "404", description = "Student not found",
                    content = @Content(mediaType = "application/json")),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "400", description = "Invalid request",
                    content = @Content(mediaType = "application/json"))
    })
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
    @Operation(summary = "Delete student", description = "Delete a student from the system by ID")
    @io.swagger.v3.oas.annotations.responses.ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "Student deleted successfully",
                    content = @Content(mediaType = "application/json")),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "404", description = "Student not found",
                    content = @Content(mediaType = "application/json"))
    })
    public ResponseEntity<ApiResponse<Void>> deleteStudent(@PathVariable int studentId) {
        studentService.deleteStudent(studentId);
        return ResponseBuilder.build(HttpStatus.OK, "Delete successfully student", null);
    }

}
