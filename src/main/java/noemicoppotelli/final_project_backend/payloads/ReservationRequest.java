package noemicoppotelli.final_project_backend.payloads;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

public record ReservationRequest(
        @NotNull(message = "L'id dell'evento è obbligatorio")
        Long eventoId,

        @NotNull(message = "Il numero di posti richiesti è obbligatorio")
        @Min(value = 1, message = "Devi prenotare almeno 1 posto")
        Integer numeroPostiPrenotati
) {}