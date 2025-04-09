package vn.student_management.auth;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import vn.student_management.APIReponse.ApiResponse;
import vn.student_management.APIReponse.ResponseBuilder;
import vn.student_management.user.User;
import vn.student_management.user.UserRepository;
import vn.student_management.user.UserResponseDTO;
import vn.student_management.util.JwtUtil;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<ApiResponse<AuthResponseDTO>> login(@RequestBody LoginRequestDTO request) {
        AuthResponseDTO response = authService.login(request);
        return ResponseBuilder.build(HttpStatus.OK, "Login successfully", response);
    }

    @PostMapping("/refresh-token")
    public ResponseEntity<ApiResponse<AuthResponseDTO>> refreshToken(@RequestBody RefreshTokenRequestDTO request) {
        AuthResponseDTO response = authService.refreshToken(request);
        return ResponseBuilder.build(HttpStatus.OK, "Refresh token successfully", response);
    }
}
