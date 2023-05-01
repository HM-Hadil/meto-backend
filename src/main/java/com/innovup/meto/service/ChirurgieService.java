package com.innovup.meto.service;


import com.innovup.meto.entity.Chirurgie;
import com.innovup.meto.entity.ChirurgieDuration;
import com.innovup.meto.exception.SurgeryNotFoundException;
import com.innovup.meto.mapper.ChirurgieMapper;
import com.innovup.meto.repository.ChirurgieRepo;
import com.innovup.meto.request.ChirurgieRequest;
import com.innovup.meto.result.ChirurgieResult;
import com.innovup.meto.util.DurationConverter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class ChirurgieService {
    @Autowired
    private ChirurgieRepo repo;

    public List<Chirurgie> findSurgeriesByIds(List<UUID> id) {
        return id.stream()
                .map(this::surgeryById)
                .toList();
    }
    public List<String> findNameByIds(List<UUID> ids) {
        return ids.stream()
                .map(this::surgeryById)
                .filter(Objects::nonNull)
                .map(Chirurgie::getName)
                .collect(Collectors.toList());
    }
    private Chirurgie surgeryById(UUID id) {
        return repo.findById(id).orElse(null);
    }

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
            var surgery = optional.get();
            surgery.setName(request.getName());
            surgery.setDescription(request.getDescription());
            surgery.setDuration(
                    ChirurgieDuration.builder()
                            .withDays(request.getDuration().getDays())
                            .withHours(request.getDuration().getHours())
                            .withMinutes(request.getDuration().getMinutes())
                            .withSeconds(request.getDuration().getSeconds())
                            .build()
            );
            surgery.setDurationInSeconds(DurationConverter.toSeconds(request.getDuration()));
            surgery.setImage(request.getImage());
            surgery = repo.save(surgery);
            return ChirurgieMapper.entityToResult(surgery);
        } else {
            throw new SurgeryNotFoundException();
        }
    }
    public void deleteSurgery(UUID id) {
        repo.deleteById(id);
        log.info("Deleted surgery with ID: {}",id );
    }

    public int getChirurgieCount() {
        return repo.getChirurgieCount();
    }

    public List<Map<String, Object>> getNumDoctorsPerSurgery() {
        List<Object[]> rows = repo.getNumDoctorsPerSurgery();
        List<Map<String, Object>> result = new ArrayList<>();
        for (Object[] row : rows) {
            Map<String, Object> map = new HashMap<>();
            map.put("surgeryId", row[0]);
            map.put("numDoctors", row[1]);
            result.add(map);
        }
        return result;
    }
}