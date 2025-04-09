package vn.student_management.student;

import jakarta.validation.*;
import jakarta.validation.constraints.*;
import lombok.*;
import vn.student_management.studentInfor.StudentInfoRequestDTO;

@Data
public class StudentRequestDTO {

    @NotBlank(message = "Student name is required")
    @Size(max = 20, message = "Student name must be at most 20 characters")
    private String studentName;

    @NotBlank(message = "Student code is required")
    @Size(max = 10, message = "Student code must be at most 10 characters")
    private String studentCode;

    @Valid
    private StudentInfoRequestDTO studentInfo;
}
