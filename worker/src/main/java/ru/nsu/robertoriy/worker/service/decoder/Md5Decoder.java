package ru.nsu.robertoriy.worker.service.decoder;

import java.util.List;
import ru.nsu.robertoriy.worker.model.Task;

public interface Md5Decoder {
    List<String> decode(Task task);
}
