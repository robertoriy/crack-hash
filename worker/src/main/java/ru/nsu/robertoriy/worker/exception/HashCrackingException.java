package ru.nsu.robertoriy.worker.model.exception;

public class HashCrackingException extends RuntimeException {
    public HashCrackingException(String message) {
        super(message);
    }
}
