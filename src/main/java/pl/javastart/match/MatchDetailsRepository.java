package pl.javastart.match;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MatchDetailsRepository extends JpaRepository<MatchDetails, Long> {
}
