package vn.student_management.auth;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import vn.student_management.exception.GlobalExceptionHandler;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

class AuthControllerTest {

    @Mock
    private AuthService authService;

    @InjectMocks
    private AuthController authController;

    private MockMvc mockMvc;
    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders
                .standaloneSetup(authController)
                .setControllerAdvice(new GlobalExceptionHandler()) // Đưa exception handler vào đây
                .build();
        objectMapper = new ObjectMapper();
    }

    @Test
    void testLogin_Success() throws Exception {
        // Prepare data
        LoginRequestDTO loginRequestDTO = new LoginRequestDTO();
        loginRequestDTO.setUsername("user1@gmail.com");
        loginRequestDTO.setPassword("password");

        AuthResponseDTO authResponseDTO = new AuthResponseDTO("sampleToken");

        // Mocking behavior
        when(authService.login(loginRequestDTO)).thenReturn(authResponseDTO);

        // Perform request and check results
        mockMvc.perform(post("/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(loginRequestDTO)))
                .andExpect(status().isOk())  // Check for HTTP 200
                .andExpect(jsonPath("$.data.token").value("sampleToken"));  // Check for token in response
    }

    @Test
    void testLogin_Failure_InvalidCredentials() throws Exception {
        // Arrange
        LoginRequestDTO loginRequestDTO = new LoginRequestDTO();
        loginRequestDTO.setUsername("testuser@gmail.com");
        loginRequestDTO.setPassword("wrongpassword");

        // Mock: authService ném ra BadCredentialsException
        when(authService.login(any(LoginRequestDTO.class)))
                .thenThrow(new BadCredentialsException("Username or password is incorrect"));

        // Act & Assert
        mockMvc.perform(post("/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(loginRequestDTO)))
                .andExpect(status().isUnauthorized()) // HTTP 401
                .andExpect(jsonPath("$.code").value(401))
                .andExpect(jsonPath("$.message").value("Username or password is incorrect"))
                .andExpect(jsonPath("$.data").doesNotExist());
    }
}
