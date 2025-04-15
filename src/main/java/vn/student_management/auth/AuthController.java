package vn.student_management.auth;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import vn.student_management.APIReponse.ApiResponse;
import vn.student_management.APIReponse.ResponseBuilder;
import vn.student_management.mapper.UserMapper;
import vn.student_management.user.*;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final UserMapper userMapper;
    private final AuthService authService;
    private final UserService userService;

        @PostMapping("/login")
        @Operation(summary = "Login", description = "Login to get JWT token")
        @io.swagger.v3.oas.annotations.responses.ApiResponses(value = {
                @io.swagger.v3.oas.annotations.responses.ApiResponse(
                        responseCode = "200",
                        description = "Login successfully",
                        content = @Content(mediaType = "application/json",
                                schema = @Schema(implementation = AuthResponseDTO.class))
                ),
                @io.swagger.v3.oas.annotations.responses.ApiResponse(
                        responseCode = "401",
                        description = "Invalid credentials",
                        content = @Content)
        })
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
        if (userService.exitUserByUsername(request.getUsername())) {
            return ResponseBuilder.build(HttpStatus.CONFLICT, "Username already exists", null);
        }
        User user = userMapper.toUser(request);
        User savedUser = userService.saveUser(user);
        return ResponseBuilder.build(HttpStatus.CREATED, "User created successfully", userMapper.toUserResponse(savedUser));
    }
}
