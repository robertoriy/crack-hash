package ru.nsu.robertoriy.manager.dao;

import java.util.UUID;
import org.springframework.context.annotation.Profile;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import ru.nsu.robertoriy.manager.dao.entity.CrackingTask;

@Repository
@Profile("distributed")
public interface CrackingTaskRepository extends MongoRepository<CrackingTask, UUID> {
}
