package noemicoppotelli.final_project_backend.services;

import lombok.RequiredArgsConstructor;
import noemicoppotelli.final_project_backend.entities.Event;
import noemicoppotelli.final_project_backend.entities.User;
import noemicoppotelli.final_project_backend.exceptions.NotFoundException;
import noemicoppotelli.final_project_backend.exceptions.UnauthorizedException;
import noemicoppotelli.final_project_backend.exceptions.BadRequestException;
import noemicoppotelli.final_project_backend.payloads.EventRequest;
import noemicoppotelli.final_project_backend.repositories.EventRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class EventService {

    private final EventRepository eventRepository;

    public List<Event> findAll() {
        return eventRepository.findAll();
    }

    public Event findById(Long id) {
        return eventRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Evento non trovato con id: " + id));
    }

    public Event create(EventRequest request, User creatore) {
        boolean esisteGia = eventRepository.existsByTitoloAndDataAndLuogoAndCreatoreId(
                request.titolo(), request.data(), request.luogo(), creatore.getId()
        );

        if (esisteGia) {
            throw new BadRequestException("Hai già creato un evento identico (stesso titolo, data e luogo)");
        }

        Event event = new Event();
        event.setTitolo(request.titolo());
        event.setDescrizione(request.descrizione());
        event.setData(request.data());
        event.setLuogo(request.luogo());
        event.setPostiTotali(request.postiTotali());
        event.setPostiDisponibili(request.postiTotali());
        event.setCreatore(creatore);

        return eventRepository.save(event);
    }

    public Event update(Long id, EventRequest request, User utenteCorrente) {
        Event event = findById(id);
        checkOwnership(event, utenteCorrente);

        int differenzaPosti = request.postiTotali() - event.getPostiTotali();
        int nuoviPostiDisponibili = event.getPostiDisponibili() + differenzaPosti;

        if (nuoviPostiDisponibili < 0) {
            throw new BadRequestException("Impossibile ridurre i posti totali sotto il numero di posti già prenotati");
        }

        event.setTitolo(request.titolo());
        event.setDescrizione(request.descrizione());
        event.setData(request.data());
        event.setLuogo(request.luogo());
        event.setPostiTotali(request.postiTotali());
        event.setPostiDisponibili(nuoviPostiDisponibili);

        return eventRepository.save(event);
    }

    public void delete(Long id, User utenteCorrente) {
        Event event = findById(id);
        checkOwnership(event, utenteCorrente);
        eventRepository.delete(event);
    }

    private void checkOwnership(Event event, User utenteCorrente) {
        if (!event.getCreatore().getId().equals(utenteCorrente.getId())) {
            throw new UnauthorizedException("Non sei autorizzato a modificare questo evento");
        }
    }
}