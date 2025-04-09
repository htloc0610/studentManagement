package vn.student_management.auth;

import lombok.Data;

@Data
public class RefreshTokenRequestDTO {
    private String token;
}
