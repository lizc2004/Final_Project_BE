package noemicoppotelli.final_project_backend.repositories;

import noemicoppotelli.final_project_backend.entities.Event;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface EventRepository extends JpaRepository<Event, Long> {

    List<Event> findByCreatoreId(Long creatoreId);
}
