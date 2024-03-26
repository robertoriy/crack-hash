package ru.nsu.robertoriy.manager.service.exception;

public class NoSuchRequestException extends RuntimeException {
    public NoSuchRequestException() {
        super("No such request");
    }
}
