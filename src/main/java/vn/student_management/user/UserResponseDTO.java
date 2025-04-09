package vn.student_management.user;

import lombok.*;

@Getter
@Setter
public class UserResponseDTO {
    private Integer userId;
    private String userName;
    private String password;
}