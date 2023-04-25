package com.innovup.meto.exception;

import com.innovup.meto.enums.Role;
import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;
@Getter
@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class UserNotFoundException extends RuntimeException {

    private final Role role;

    public UserNotFoundException() {
        super();
        this.role = null;
    }

    public UserNotFoundException(Role role) {
        super();
        this.role = role;
    }
}