package TourismeMedical_BE.TypeChirurgie;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Lob;
import javax.persistence.Table;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "Chirurgie")
public class Chirurgie {
    @Id
    @GeneratedValue
    private Long id ;

    private String nomChirurgie;
    @Lob
    private String description ;
    @Lob
    private String imageChirurgie;
    private String dureeChirurgie;


}
