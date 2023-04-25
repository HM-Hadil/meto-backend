package com.innovup.meto.exception;
import com.innovup.meto.core.web.RestResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(AppointmentNotFoundException.class)
    public ResponseEntity<RestResponse<Void>> surgeryNotFoundHandler(AppointmentNotFoundException e) {
        return new ResponseEntity<>(RestResponse.empty(404, "Appointment not found"), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(SurgeryNotFoundException.class)
    public ResponseEntity<RestResponse<Void>> surgeryNotFoundHandler(SurgeryNotFoundException e) {
        return new ResponseEntity<>(RestResponse.empty(404, "Surgery not found"), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<RestResponse<Void>> userNotFoundHandler(UserNotFoundException e) {
        var message = "User with role " + e.getRole() + " not found";
        if (e.getRole() == null) {
            message = "User not found";
        }
        return new ResponseEntity<>(RestResponse.empty(404, message), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(UnauthorizedUserException.class)
    public ResponseEntity<RestResponse<Void>> unauthorizedUserHandler(UnauthorizedUserException e) {
        return new ResponseEntity<>(RestResponse.empty(404, "Cannot retrieve authenticated user"), HttpStatus.NOT_FOUND);
    }
}