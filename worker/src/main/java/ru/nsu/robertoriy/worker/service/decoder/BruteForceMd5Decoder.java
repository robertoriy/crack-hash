package ru.nsu.robertoriy.worker.service.decoder;

import jakarta.xml.bind.DatatypeConverter;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.List;
import java.util.Locale;
import java.util.stream.IntStream;
import java.util.stream.Stream;
import lombok.extern.slf4j.Slf4j;
import org.paukov.combinatorics3.Generator;
import org.springframework.stereotype.Service;
import ru.nsu.robertoriy.worker.configuration.ApplicationConfig;
import ru.nsu.robertoriy.worker.exception.HashCrackingException;
import ru.nsu.robertoriy.worker.model.Task;

@Slf4j
@Service
public class BruteForceMd5Decoder implements Md5Decoder {
    private final int maxWordLength;

    public BruteForceMd5Decoder(ApplicationConfig applicationConfig) {
        maxWordLength = applicationConfig.maxWordLength();
    }

    @Override
    public List<String> decode(Task task) {
        checkMaxWordLength(task.maxLength());

        CrackingProperties properties = getCrackingProperties(task);
        log.info("Passwords search begins");

        return passwords(properties.alphabet(), task.maxLength())
            .skip(properties.toSkip())
            .limit(properties.toTake())
            .filter(password -> task.hash().equals(getMd5Hash(password)))
            .toList();
    }

    private void checkMaxWordLength(int value) {
        if (value > maxWordLength) {
            log.error("Max word length is greater than the config one");
            throw new HashCrackingException("Max word length is greater than " + maxWordLength);
        }
    }

    private CrackingProperties getCrackingProperties(Task task) {
        List<Character> alphabet = task.alphabet()
            .chars()
            .mapToObj(character -> (char) character)
            .toList();

        long numberOfPasswords = getNumberOfAllPasswords(alphabet, task.maxLength());
        log.info("Number of all possible passwords: {}", numberOfPasswords);

        long passwordsToTake = (long) Math.ceil((double) numberOfPasswords / task.partCount());
        long passwordsToSkip = task.partNumber() * passwordsToTake;

        return new CrackingProperties(
            alphabet,
            passwordsToSkip,
            passwordsToTake
        );
    }

    private Stream<String> passwords(List<Character> alphabet, int maxLength) {
        return IntStream.range(1, maxLength + 1)
            .mapToObj(i -> Generator.permutation(alphabet)
                .withRepetitions(i)
                .stream()
                .map(this::concatenate)
            ).flatMap(stream -> stream);
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
