package vn.student_management.studentInfor;


import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import jakarta.validation.constraints.*;
import lombok.*;

import java.time.LocalDateTime;

@Data
public class StudentInfoRequestDTO {
    @NotBlank(message = "Address is required")
    @Size(max = 50, message = "Address must be at most 50 characters")
    private String address;

    @NotNull(message = "Average score is required")
    @DecimalMin(value = "0.0", message = "Score must be at least 0.0")
    @DecimalMax(value = "10.0", message = "Score must be at most 10.0")
    private Double averageScore;

    @NotNull(message = "Date of birth is required")
    @PastOrPresent(message = "Date of birth cannot be in the future")
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    private LocalDateTime dateOfBirth;
}
