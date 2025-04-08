package vn.student_management.student;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.*;
import org.springframework.web.bind.annotation.*;
import vn.student_management.mapper.StudentMapper;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/students")
public class StudentController {

    private final StudentService studentService;
//    private final StudentMapper studentMapper;

//    @GetMapping
//    public List<StudentResponseDTO> getAllStudents() {
//        return studentService.getAllStudents()
//                .stream()
//                .map(studentMapper::toStudentResponse)
//                .collect(Collectors.toList());
//    }

    @GetMapping
    public List<Student> getAllStudents() {
        return studentService.getAllStudents();
    }
}
