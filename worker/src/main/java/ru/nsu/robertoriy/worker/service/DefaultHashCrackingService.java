package ru.nsu.robertoriy.worker.service;

import jakarta.xml.bind.DatatypeConverter;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.List;
import java.util.Locale;
import java.util.stream.IntStream;
import java.util.stream.Stream;
import lombok.extern.slf4j.Slf4j;
import org.paukov.combinatorics3.Generator;
import org.springframework.stereotype.Component;
import ru.nsu.robertoriy.worker.dto.request.TaskRequest;
import ru.nsu.robertoriy.worker.dto.request.WorkerResponse;
import ru.nsu.robertoriy.worker.service.exception.HashCrackingException;

@Slf4j
@Component
public class DefaultHashCrackingService implements HashCrackingService {
    @Override
    public WorkerResponse completeTask(TaskRequest taskRequest) {
        return new WorkerResponse(
            taskRequest.requestId(),
            getDecodedPasswords(taskRequest)
        );
        // if (decodedList != null) {send data to manager}
    }

    private List<String> getDecodedPasswords(TaskRequest taskRequest) {
        CrackingProperties properties = getCrackingProperties(taskRequest);

        return passwords(properties.alphabet(), taskRequest.maxLength())
            .skip(properties.toSkip())
            .limit(properties.toTake())
            .filter(pass -> taskRequest.hash().equals(getMd5Hash(pass)))
            .toList();
    }

    private CrackingProperties getCrackingProperties(TaskRequest taskRequest) {
        List<Character> alphabet = taskRequest.alphabet()
            .chars()
            .mapToObj(c -> (char) c)
            .toList();

        long numberOfPasswords = getNumberOfAllPasswords(alphabet, taskRequest.maxLength());

        long passwordsToTake = (long) Math.ceil((double) numberOfPasswords / taskRequest.partCount());
        long passwordsToSkip = taskRequest.partNumber() * passwordsToTake;

        return new CrackingProperties(
            alphabet,
            passwordsToSkip,
            passwordsToTake
        );
    }

    private long getNumberOfAllPasswords(List<Character> alphabet, int maxLength) {
        return IntStream.range(1, maxLength + 1)
            .mapToLong(i -> Generator.permutation(alphabet)
                .withRepetitions(i)
                .stream()
                .count()
            )
            .sum();
    }

    private Stream<String> passwords(List<Character> alphabet, int maxLength) {
        return IntStream.range(1, maxLength + 1)
            .mapToObj(i -> Generator.permutation(alphabet)
                .withRepetitions(i)
                .stream()
                .map(this::concatenate)
            ).flatMap(stream -> stream);
    }

    private String concatenate(List<Character> characters) {
        StringBuilder result = new StringBuilder();
        for (char character : characters) {
            result.append(character);
        }
        return result.toString();
    }

    private String getMd5Hash(String input) {
        try {
            MessageDigest messageDigest = MessageDigest.getInstance("MD5");
            messageDigest.update(input.getBytes());
            byte[] digest = messageDigest.digest();
            return DatatypeConverter.printHexBinary(digest).toLowerCase(Locale.ENGLISH);
        } catch (NoSuchAlgorithmException e) {
            log.error("No Such Algorithm Exception");
            throw new HashCrackingException("MessageDigest algorithm error");
        }
    }

    record CrackingProperties(List<Character> alphabet, long toSkip, long toTake) {
    }
}
