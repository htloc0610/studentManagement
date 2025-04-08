package vn.student_management.studentInfor;


import lombok.*;

import java.time.LocalDateTime;

@Data
public class StudentInfoRequestDTO {
    private String address;
    private Double averageScore;
    private LocalDateTime dateOfBirth;
}
