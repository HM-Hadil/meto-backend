package com.innovup.meto.typeChirurgie;


import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Controller
@AllArgsConstructor
@NoArgsConstructor
@CrossOrigin( origins = {"http://localhost:4200"})
@RequestMapping("/api/")
public class ChirurgieController {
    @Autowired
    private ChirurgieService service;
    @Autowired
    private ChirurgieRepo repo;

    //Add Chirurgie
    @PostMapping("/addChirurgie")
    public Chirurgie AddChirurgie(@RequestBody Chirurgie chirurgie){
        return this.service.SaveChirurgie(chirurgie);
    }

    //GetAllChirugie
    @GetMapping("getAllChirurgies")
    public ResponseEntity<List<Chirurgie>> getAllChirurgies() {
        List<Chirurgie> chirurgies = repo.findAll();
        if (chirurgies.isEmpty()) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.ok(chirurgies);
        }
    }

    //Get Chirurgie By Id
    @GetMapping("/GetChirurgieById/{id}")
    public ResponseEntity<Chirurgie> GetChirurgieByName(@PathVariable("id") Long id) throws Exception {
        Optional<Chirurgie> typeCh = service.ChirurgieByName(id);

        if(typeCh.isPresent())  {

            return new ResponseEntity<>(typeCh.get(), HttpStatus.OK);
        } else {
            throw new Exception();
        }

    }

    //Delete Chirugie By Id
    @DeleteMapping("/DeleteChirurgie/{id}")
    public void deleteChirurgie(@PathVariable Long id) {
        if (repo.existsById(id)) {
            service.deleteChirurgieById(id);
        } else {
            throw new RuntimeException("Chirurgie not found for id: " + id);
        }
    }

    //Update chirurgie By id
    @PutMapping ("/UpdateChirurgie/{id}")

    public ResponseEntity<Chirurgie> updateChirurgie(@PathVariable("id") Long id ,@RequestBody Chirurgie chirurgie)
    {
        Optional<Chirurgie> optionalChirurgie = repo.findById(id);

        if (optionalChirurgie.isPresent()) {
            Chirurgie existingChirurgie = optionalChirurgie.get();
            existingChirurgie.setNomChirurgie(chirurgie.getNomChirurgie());
            existingChirurgie.setDescription(chirurgie.getDescription());
            existingChirurgie.setImageChirurgie(chirurgie.getImageChirurgie());
            existingChirurgie.setDureeChirurgie(chirurgie.getDureeChirurgie());

            Chirurgie updatedChirurgie = repo.save(existingChirurgie);
            return ResponseEntity.ok(updatedChirurgie);
        } else {
            return ResponseEntity.notFound().build();
        }


    }



}