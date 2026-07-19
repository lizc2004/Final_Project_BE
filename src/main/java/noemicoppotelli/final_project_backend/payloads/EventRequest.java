package noemicoppotelli.final_project_backend.payloads;

import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

public record EventRequest(
        @NotBlank(message = "Il titolo è obbligatorio")
        String titolo,

        String descrizione,

        @NotNull(message = "La data è obbligatoria")
        @Future(message = "La data deve essere futura")
        LocalDate data,

        @NotBlank(message = "Il luogo è obbligatorio")
        String luogo,

        @NotNull(message = "Il numero di posti totali è obbligatorio")
        @Min(value = 1, message = "Deve esserci almeno 1 posto disponibile")
        Integer postiTotali
) {}
