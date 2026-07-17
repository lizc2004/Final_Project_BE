package noemicoppotelli.final_project_backend.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Table(name = "reservations")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Reservation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "utente_id", nullable = false)
    private User utente;

    @ManyToOne
    @JoinColumn(name = "evento_id", nullable = false)
    private Event evento;

    @Column(nullable = false)
    private LocalDate dataPrenotazione;

    @Column(nullable = false)
    private Integer numeroPostiPrenotati;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private StatoPrenotazione stato;

    public enum StatoPrenotazione {
        ATTIVA,
        ANNULLATA
    }
}