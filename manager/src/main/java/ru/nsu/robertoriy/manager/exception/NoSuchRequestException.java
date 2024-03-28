package ru.nsu.robertoriy.manager.exception;

public class NoSuchRequestException extends RuntimeException {
    public NoSuchRequestException() {
        super("No such request");
    }
}
