package com.innovup.meto.service;


import com.innovup.meto.entity.Chirurgie;
import com.innovup.meto.entity.ChirurgieDuration;
import com.innovup.meto.mapper.ChirurgieMapper;
import com.innovup.meto.repository.ChirurgieRepo;
import com.innovup.meto.request.ChirurgieRequest;
import com.innovup.meto.result.ChirurgieResult;
import com.innovup.meto.util.DurationConverter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class ChirurgieService {
    @Autowired
    private ChirurgieRepo repo;

    public List<ChirurgieResult> findAllSurgeries() {
        return repo.findAll().stream()
                .map(ChirurgieMapper::entityToResult)
                .toList();
    }

    public Optional<ChirurgieResult> findSurgeryById(UUID id) {
        return repo.findById(id)
                .map(ChirurgieMapper::entityToResult);
    }

    public ChirurgieResult addSurgery(ChirurgieRequest request) {
        var chirurgie = Chirurgie.builder()
                .withId(UUID.randomUUID())
                .withName(request.getName())
                .withDescription(request.getDescription())
                .withImage(request.getImage())
                .withDuration(
                        ChirurgieDuration.builder()
                                .withDays(request.getDuration().getDays())
                                .withHours(request.getDuration().getHours())
                                .withMinutes(request.getDuration().getMinutes())
                                .withSeconds(request.getDuration().getSeconds())
                                .build()
                )
                .withDurationInSeconds(DurationConverter.toSeconds(request.getDuration()))
                .build();
        chirurgie = repo.save(chirurgie);
        return ChirurgieMapper.entityToResult(chirurgie);
    }

    public ChirurgieResult updateSurgery(UUID id, ChirurgieRequest request) {
        var optional = repo.findById(id);
        if (optional.isPresent()) {
            var chirurgie = optional.get();
            chirurgie.setName(request.getName());
            chirurgie.setDescription(request.getDescription());
            chirurgie.setDuration(
                    ChirurgieDuration.builder()
                            .withDays(request.getDuration().getDays())
                            .withHours(request.getDuration().getHours())
                            .withMinutes(request.getDuration().getMinutes())
                            .withSeconds(request.getDuration().getSeconds())
                            .build()
            );
            chirurgie.setDurationInSeconds(DurationConverter.toSeconds(request.getDuration()));
            chirurgie.setImage(request.getImage());
            chirurgie = repo.save(chirurgie);
            return ChirurgieMapper.entityToResult(chirurgie);
        } else { // else throw new SurgeryNotFoundException
            throw new RuntimeException();
        }
    }

    public void deleteSurgery(UUID id) {
        repo.deleteById(id);
        log.info("Deleted surgery with ID: {}",id );
    }

    public int getChirurgieCount() {
        return repo.getChirurgieCount();
    }
}