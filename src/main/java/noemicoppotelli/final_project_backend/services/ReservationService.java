package noemicoppotelli.final_project_backend.services;

import lombok.RequiredArgsConstructor;
import noemicoppotelli.final_project_backend.entities.Event;
import noemicoppotelli.final_project_backend.entities.Reservation;
import noemicoppotelli.final_project_backend.entities.User;
import noemicoppotelli.final_project_backend.exceptions.BadRequestException;
import noemicoppotelli.final_project_backend.exceptions.NotFoundException;
import noemicoppotelli.final_project_backend.exceptions.UnauthorizedException;
import noemicoppotelli.final_project_backend.payloads.ReservationRequest;
import noemicoppotelli.final_project_backend.repositories.EventRepository;
import noemicoppotelli.final_project_backend.repositories.ReservationRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ReservationService {

    private final ReservationRepository reservationRepository;
    private final EventRepository eventRepository;

    public List<Reservation> findByUtente(User utente) {
        return reservationRepository.findByUtenteId(utente.getId());
    }

    public Reservation create(ReservationRequest request, User utente) {
        Event event = eventRepository.findById(request.eventoId())
                .orElseThrow(() -> new NotFoundException("Evento non trovato con id: " + request.eventoId()));

        if (request.numeroPostiPrenotati() > event.getPostiDisponibili()) {
            throw new BadRequestException("Posti disponibili insufficienti");
        }

        event.setPostiDisponibili(event.getPostiDisponibili() - request.numeroPostiPrenotati());
        eventRepository.save(event);

        Reservation reservation = new Reservation();
        reservation.setUtente(utente);
        reservation.setEvento(event);
        reservation.setNumeroPostiPrenotati(request.numeroPostiPrenotati());
        reservation.setDataPrenotazione(LocalDate.now());
        reservation.setStato(Reservation.StatoPrenotazione.ATTIVA);

        return reservationRepository.save(reservation);
    }

    public void cancel(Long id, User utenteCorrente) {
        Reservation reservation = reservationRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Prenotazione non trovata con id: " + id));

        if (!reservation.getUtente().getId().equals(utenteCorrente.getId())) {
            throw new UnauthorizedException("Non sei autorizzato ad annullare questa prenotazione");
        }

        if (reservation.getStato() == Reservation.StatoPrenotazione.ANNULLATA) {
            throw new BadRequestException("Questa prenotazione è già stata annullata");
        }

        reservation.setStato(Reservation.StatoPrenotazione.ANNULLATA);

        Event event = reservation.getEvento();
        event.setPostiDisponibili(event.getPostiDisponibili() + reservation.getNumeroPostiPrenotati());
        eventRepository.save(event);

        reservationRepository.save(reservation);
    }
}
