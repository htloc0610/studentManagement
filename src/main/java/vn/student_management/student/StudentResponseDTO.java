package vn.student_management.student;

import lombok.*;
import vn.student_management.studentInfor.StudentInfoResponseDTO;

import java.time.LocalDateTime;

@Data
public class StudentResponseDTO {
    private Integer studentId;
    private String studentName;

    private StudentInfoResponseDTO studentInfo;
}
