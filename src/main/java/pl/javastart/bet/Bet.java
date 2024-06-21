package pl.javastart.bet;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import pl.javastart.match.Match;
import pl.javastart.match.MatchResult;

import java.math.BigDecimal;
import java.math.RoundingMode;

@Getter
@Setter
@Entity
public class Bet {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Enumerated(EnumType.STRING)
    private MatchResult matchResult;
    private Double stake;
    private Double possibleWin;
    @ManyToOne(cascade = CascadeType.REMOVE)
    private Match match;

    public Double calculatePossibleWin() {
        double possibleWin = stake;
        if (matchResult.equals(MatchResult.TEAM_A)) {
            possibleWin *= match.getMatchDetails().getOddTeamA();
        } else if (matchResult.equals(MatchResult.TEAM_B)) {
            possibleWin *= match.getMatchDetails().getOddTeamB();
        } else {
            possibleWin *= match.getMatchDetails().getOddDraw();
        }

        BigDecimal possibleWinBigDecimal = new BigDecimal(possibleWin);
        possibleWinBigDecimal = possibleWinBigDecimal.setScale(2, RoundingMode.HALF_UP);

        return possibleWinBigDecimal.doubleValue();
    }
}
