package ru.nsu.robertoriy.worker.model;

public record Task(
    String hash,
    String alphabet,
    int maxLength,
    int partCount,
    int partNumber
) {
}
