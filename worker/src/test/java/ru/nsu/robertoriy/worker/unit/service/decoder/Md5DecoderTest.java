package ru.nsu.robertoriy.worker.unit.service.decoder;

import java.util.List;
import java.util.stream.Stream;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import ru.nsu.robertoriy.worker.model.Task;
import ru.nsu.robertoriy.worker.service.decoder.Md5Decoder;
import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class Md5DecoderTest {
    private final Md5Decoder decoder;

    @Autowired
    Md5DecoderTest(Md5Decoder decoder) {
        this.decoder = decoder;
    }

    @ParameterizedTest
    @DisplayName("Тест декодирования md5 хэша")
    @MethodSource("provideMd5HashTasks")
    void testDecodingMd5Hash(Task task, List<String> expected) {
        List<String> actual = decoder.decode(task);

        assertThat(actual).containsExactlyInAnyOrderElementsOf(expected);
    }

    private static Stream<Arguments> provideMd5HashTasks() {
        return Stream.of(
            Arguments.of(
                new Task(
                    "d077f244def8a70e5ea758bd8352fcd8",
                    "abcdefghijklmnopqrstuvwxyz0123456789",
                    4,
                    1,
                    0
                ),
                List.of("cat")
            ),
            Arguments.of(
                new Task(
                    "440ee02a61d9a7fde22d55af8b5dfbf7",
                    "abcdefghijklmnopqrstuvwxyz0123456789",
                    4,
                    1,
                    0
                ),
                List.of("4urx")
            )
        );
    }
}
