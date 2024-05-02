package ru.nsu.robertoriy.manager.dao.entity;

import java.util.List;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.springframework.context.annotation.Profile;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import ru.nsu.robertoriy.manager.model.CrackStatus;

@ToString
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Document(collection = "tasks")
@Profile("distributed")
public class CrackingTask {
    @Id
    private UUID id;
    private List<String> data;
    private int updatedCount;
    private CrackStatus status;
}
