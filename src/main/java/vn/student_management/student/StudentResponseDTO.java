package vn.student_management.student;

import lombok.*;
import vn.student_management.studentInfor.StudentInfoResponseDTO;


@Data
public class StudentResponseDTO {
    private Integer studentId;
    private String studentName;
    private String studentCode;
    private StudentInfoResponseDTO studentInfo;
}
