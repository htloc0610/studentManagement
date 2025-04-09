package vn.student_management.mapper;

import org.mapstruct.*;
import vn.student_management.user.User;
import vn.student_management.user.UserRequestDTO;
import vn.student_management.user.UserResponseDTO;

@Mapper(componentModel = "spring")
public interface UserMapper {

    User toUser(UserRequestDTO request);

    UserResponseDTO toUserResponse(User user);

    // Dùng khi update: cập nhật từ request vào user đã có
    void updateUserFromRequest(UserRequestDTO request, @MappingTarget User user);
}
