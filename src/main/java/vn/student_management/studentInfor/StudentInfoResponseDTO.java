package vn.student_management.studentInfor;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;

import java.time.LocalDateTime;

@Data
public class StudentInfoResponseDTO {
    private Integer infoId;
    private String address;
    private Double averageScore;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime dateOfBirth;
}
