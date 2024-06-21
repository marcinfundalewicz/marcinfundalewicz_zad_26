package pl.javastart.match;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;
import pl.javastart.bet.Bet;

import java.time.LocalDate;

@Getter
@Setter
@Entity
public class Match {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String teamA;
    private String teamB;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate date;
    private boolean betClosed;
    @OneToOne
    @JoinColumn(name = "match_details_id", unique = true)
    private MatchDetails matchDetails;
    private String winner;
}
