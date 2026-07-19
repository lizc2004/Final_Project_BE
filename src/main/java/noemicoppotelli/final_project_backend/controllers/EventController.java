package noemicoppotelli.final_project_backend.controllers;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import noemicoppotelli.final_project_backend.entities.Event;
import noemicoppotelli.final_project_backend.entities.User;
import noemicoppotelli.final_project_backend.payloads.EventRequest;
import noemicoppotelli.final_project_backend.services.EventService;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/eventi")
@RequiredArgsConstructor
public class EventController {

    private final EventService eventService;

    @GetMapping
    public List<Event> getAll() {
        return eventService.findAll();
    }

    @GetMapping("/{id}")
    public Event getById(@PathVariable Long id) {
        return eventService.findById(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Event create(@RequestBody @Valid EventRequest request,
                        @AuthenticationPrincipal User utenteCorrente) {
        return eventService.create(request, utenteCorrente);
    }

    @PutMapping("/{id}")
    public Event update(@PathVariable Long id,
                        @RequestBody @Valid EventRequest request,
                        @AuthenticationPrincipal User utenteCorrente) {
        return eventService.update(id, request, utenteCorrente);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id,
                       @AuthenticationPrincipal User utenteCorrente) {
        eventService.delete(id, utenteCorrente);
    }
}
