package TourismeMedical_BE.TypeChirurgie;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
@NoArgsConstructor
public class ChirurgieService {
    @Autowired
    private ChirurgieRepo repo;

    //add chirurgie
    public Chirurgie SaveChirurgie(Chirurgie chirurgie){
        return this.repo.save(chirurgie);
    }

    //Get all Chirurgie
    public List<Chirurgie> AllChirurgie(){

        return this.repo.findAll();
    }

    //Get Chirurgie By Name
    public Optional<Chirurgie> ChirurgieByName(Long id){
        return this.repo.findById(id);

    }

    //Delete Chirurgie By Id
    public void deleteChirurgieById(Long id){
        this.repo.deleteById(id);
    }

    //Update Chirurgie By Id
    public Chirurgie UpdateChirurgieById(Chirurgie chirurgie){
        return repo.save(chirurgie);
    }
}
