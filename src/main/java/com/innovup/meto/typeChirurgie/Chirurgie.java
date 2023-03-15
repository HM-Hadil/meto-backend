package com.innovup.meto.typeChirurgie;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

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