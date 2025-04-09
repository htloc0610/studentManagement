package vn.student_management.student;

import lombok.*;
import vn.student_management.studentInfor.StudentInfoRequestDTO;

@Data
public class StudentRequestDTO {
    private String studentName;
    private String studentCode;
    private StudentInfoRequestDTO studentInfo;
}
