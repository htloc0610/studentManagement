package vn.student_management.user;

import jakarta.validation.constraints.*;
import lombok.*;

@Data
public class UserRequestDTO {
    @NotBlank(message = "Username is required")
    @Size(max = 20, message = "Username must be at most 20 characters")
    private String username;

    @NotBlank(message = "Password is required")
    @Size(min = 6, max = 15, message = "Password must be between 6 and 15 characters")
    private String password;
}

