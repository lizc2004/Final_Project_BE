package noemicoppotelli.final_project_backend.repositories;

import noemicoppotelli.final_project_backend.entities.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReservationRepository extends JpaRepository<Reservation, Long> {

    List<Reservation> findByUtenteId(Long utenteId);

    List<Reservation> findByEventoId(Long eventoId);
}