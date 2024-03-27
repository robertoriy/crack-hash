package ru.nsu.robertoriy.worker.service.exception;

public class HashCrackingException extends RuntimeException {
    public HashCrackingException(String message) {
        super(message);
    }
}
