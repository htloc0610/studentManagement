package vn.student_management.mapper;

import org.mapstruct.*;
import vn.student_management.user.User;
import vn.student_management.user.UserRequestDTO;
import vn.student_management.user.UserResponseDTO;

@Mapper(componentModel = "spring")
public interface UserMapper {

    @Mapping(source = "username", target = "userName")
    User toUser(UserRequestDTO request);

    @Mapping(source = "userName", target = "username")
    UserResponseDTO toUserResponse(User user);

    // Dùng khi update: cập nhật từ request vào user đã có
    @Mapping(source = "username", target = "userName")
    void updateUserFromRequest(UserRequestDTO request, @MappingTarget User user);
}
