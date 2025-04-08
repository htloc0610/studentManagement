package vn.student_management.studentInfor;

import lombok.*;

import java.time.LocalDateTime;

@Data
public class StudentInfoResponseDTO {
    private Integer infoId;
    private String address;
    private Double averageScore;
    private LocalDateTime dateOfBirth;
}
