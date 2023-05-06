package com.innovup.meto.exception;
public class AppointmentAlreadyExistsException extends RuntimeException {
    public AppointmentAlreadyExistsException() {
        super("An appointment already exists for the requested date and doctor.");
    }
}