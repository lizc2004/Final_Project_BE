package noemicoppotelli.final_project_backend.repositories;

import noemicoppotelli.final_project_backend.entities.Event;
import org.springframework.data.jpa.repository.JpaRepository;
import java.time.LocalDate;

import java.util.List;

public interface EventRepository extends JpaRepository<Event, Long> {

    List<Event> findByCreatoreId(Long creatoreId);
    boolean existsByTitoloAndDataAndLuogoAndCreatoreId(String titolo, LocalDate data, String luogo, Long creatoreId);
}
