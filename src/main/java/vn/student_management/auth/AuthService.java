package vn.student_management.auth;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import vn.student_management.exception.AuthenticationException;
import vn.student_management.user.User;
import vn.student_management.user.UserRepository;
import vn.student_management.util.JwtUtil;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    public AuthResponseDTO login(LoginRequestDTO request) {
        User user = userRepository.findByUserName(request.getUsername())
                .orElseThrow(() -> new RuntimeException("User not found"));

        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new AuthenticationException("Username or password is incorrect");
        }

        String token = jwtUtil.generateToken(user.getUserName());

        return new AuthResponseDTO(token);
    }

    public AuthResponseDTO refreshToken(RefreshTokenRequestDTO request) {
        String token = request.getToken();

        if (!jwtUtil.validateToken(token) && !jwtUtil.shouldRefreshToken(token)) {
            throw new RuntimeException("Token expired too long, cannot refresh");
        }

        String username = jwtUtil.extractUsernameHandleExpired(token);
        String newToken = jwtUtil.generateToken(username);

        return new AuthResponseDTO(newToken);
    }

}
