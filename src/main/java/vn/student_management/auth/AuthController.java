package vn.student_management.auth;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import vn.student_management.APIReponse.ApiResponse;
import vn.student_management.APIReponse.ResponseBuilder;
import vn.student_management.mapper.UserMapper;
import vn.student_management.user.*;
import vn.student_management.util.JwtUtil;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final UserMapper userMapper;
    private final AuthService authService;
    private final UserService userService;

    @PostMapping("/login")
    public ResponseEntity<ApiResponse<AuthResponseDTO>> login(@RequestBody @Valid  LoginRequestDTO request) {
        AuthResponseDTO response = authService.login(request);
        return ResponseBuilder.build(HttpStatus.OK, "Login successfully", response);
    }

    @PostMapping("/refresh-token")
    public ResponseEntity<ApiResponse<AuthResponseDTO>> refreshToken(@RequestBody RefreshTokenRequestDTO request) {
        AuthResponseDTO response = authService.refreshToken(request);
        return ResponseBuilder.build(HttpStatus.OK, "Refresh token successfully", response);
    }

    @PostMapping("/register")
    public ResponseEntity<ApiResponse<UserResponseDTO>> createUser(@RequestBody @Valid UserRequestDTO request) {
        User user = userMapper.toUser(request);
        User savedUser = userService.saveUser(user);
        return ResponseBuilder.build(HttpStatus.CREATED, "User created successfully", userMapper.toUserResponse(savedUser));
    }
}
