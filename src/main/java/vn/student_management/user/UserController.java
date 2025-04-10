package vn.student_management.user;


import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import vn.student_management.APIReponse.ApiResponse;
import vn.student_management.APIReponse.ResponseBuilder;
import vn.student_management.mapper.UserMapper;
import vn.student_management.util.JwtUtil;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final UserMapper userMapper;
    private final JwtUtil jwtUtil;

    @GetMapping("/{userId}")
    public ResponseEntity<ApiResponse<UserResponseDTO>> getUserById(@PathVariable int userId) {
        User user = userService.findUserById(userId);
        return ResponseBuilder.build(HttpStatus.OK, "Get successfully user", userMapper.toUserResponse(user));
    }

    @GetMapping("/infor")
    public ResponseEntity<ApiResponse<UserResponseDTO>> getUserByToken() {
        String token = jwtUtil.getCurrentToken();
        String username = jwtUtil.extractUsername(token);
        User user = userService.findUserByUsername(username);
        return ResponseBuilder.build(HttpStatus.OK, "Get successfully user", userMapper.toUserResponse(user));
    }

}
