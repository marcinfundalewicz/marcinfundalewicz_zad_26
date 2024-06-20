package pl.javastart.match;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MatchRepository extends JpaRepository<Match, Long> {
    List<Match> findAllByBetClosedIsFalse();

    @Query(value = "SELECT m.* FROM match m LEFT JOIN bet b ON m.id = b.match_id WHERE (m.bet_closed = false)  GROUP BY m.id ORDER BY COUNT(b.id) DESC LIMIT 3", nativeQuery = true)
    List<Match> findTop3ByOrderByBetsDesc();
}
