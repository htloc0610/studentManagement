package vn.student_management.user;

import lombok.*;

@Getter
@Setter
public class UserResponseDTO {
    private Integer userId;
    private String username;
    private String password;
}