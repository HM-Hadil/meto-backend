package TourismeMedical_BE.TypeChirurgie;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ChirurgieRepo extends JpaRepository<Chirurgie, Long> {



}
