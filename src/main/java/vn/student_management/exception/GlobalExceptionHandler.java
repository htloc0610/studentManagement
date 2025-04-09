package vn.student_management.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import vn.student_management.APIReponse.ApiResponse;
import vn.student_management.APIReponse.ResponseBuilder;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    // Xử lý Exception chung chung
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiResponse<Object>> handleAllExceptions(Exception ex) {
        return ResponseBuilder.build(HttpStatus.INTERNAL_SERVER_ERROR, ex.getMessage());
    }

    // Xử lý lỗi request không hợp lệ (ví dụ @Valid fail)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiResponse<Object>> handleValidationException(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getFieldErrors().forEach(error ->
                errors.put(error.getField(), error.getDefaultMessage())
        );

        return ResponseBuilder.build(HttpStatus.BAD_REQUEST, "Validation failed", errors);
    }

    // Bạn có thể thêm Exception riêng theo ý muốn, ví dụ:
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ApiResponse<Object>> handleIllegalArgument(IllegalArgumentException ex) {
        return ResponseBuilder.build(HttpStatus.BAD_REQUEST, ex.getMessage());
    }
}
