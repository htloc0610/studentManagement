package vn.student_management.APIReponse;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class ResponseBuilder {

    public static <T> ResponseEntity<ApiResponse<T>> build(HttpStatus status, String message) {
        ApiResponse<T> response = new ApiResponse<>(status.value(), message);
        return new ResponseEntity<>(response, status);
    }

    public static <T> ResponseEntity<ApiResponse<T>> build(HttpStatus status, String message, T data) {
        ApiResponse<T> response = new ApiResponse<>(status.value(), message, data);
        return new ResponseEntity<>(response, status);
    }
}
