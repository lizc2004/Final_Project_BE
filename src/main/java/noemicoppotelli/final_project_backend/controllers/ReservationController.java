package noemicoppotelli.final_project_backend.controllers;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import noemicoppotelli.final_project_backend.entities.Reservation;
import noemicoppotelli.final_project_backend.entities.User;
import noemicoppotelli.final_project_backend.payloads.ReservationRequest;
import noemicoppotelli.final_project_backend.services.ReservationService;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/prenotazioni")
@RequiredArgsConstructor
public class ReservationController {

    private final ReservationService reservationService;

    @GetMapping
    public List<Reservation> getMyReservations(@AuthenticationPrincipal User utenteCorrente) {
        return reservationService.findByUtente(utenteCorrente);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Reservation create(@RequestBody @Valid ReservationRequest request,
                              @AuthenticationPrincipal User utenteCorrente) {
        return reservationService.create(request, utenteCorrente);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void cancel(@PathVariable Long id,
                       @AuthenticationPrincipal User utenteCorrente) {
        reservationService.cancel(id, utenteCorrente);
    }
}