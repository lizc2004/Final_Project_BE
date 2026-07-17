package noemicoppotelli.final_project_backend.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "events")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Event {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String titolo;

    @Column(length = 1000)
    private String descrizione;

    @Column(nullable = false)
    private LocalDate data;

    @Column(nullable = false)
    private String luogo;

    @Column(nullable = false)
    private Integer postiTotali;

    @Column(nullable = false)
    private Integer postiDisponibili;

    @ManyToOne
    @JoinColumn(name = "creatore_id", nullable = false)
    private User creatore;

    @OneToMany(mappedBy = "evento")
    private List<Reservation> prenotazioni;
}
