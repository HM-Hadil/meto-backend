package com.innovup.meto.service;

import com.innovup.meto.entity.Surgery;
import com.innovup.meto.repository.SurgeryRepository;
import com.innovup.meto.request.SurgeryRequest;
import com.innovup.meto.utils.DurationConverter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class SurgeryService {

    private final SurgeryRepository surgeryRepository;


    public List<Surgery> findAllSurgeries() {
        return surgeryRepository.findAll();
    }

    public Optional<Surgery> findSurgeryById(UUID id) {
        return surgeryRepository.findById(id);
    }

    public Surgery addSurgery(SurgeryRequest request) {
        var surgery = Surgery.builder()
                .withId(UUID.randomUUID())
                .withName(request.getName())
                .withDescription(request.getDescription())
                .withImage(request.getImage())
                .withDuration(Duration.of(DurationConverter.toSeconds(request.getDuration()), ChronoUnit.SECONDS))
                .build();
        return surgeryRepository.save(surgery);
    }

    public Surgery updateSurgery(UUID id, SurgeryRequest request) {
        var surgery = Surgery.builder()
                .withId(id)
                .withName(request.getName())
                .withDescription(request.getDescription())
                .withImage(request.getImage())
                .withDuration(Duration.of(DurationConverter.toSeconds(request.getDuration()), ChronoUnit.SECONDS))
                .build();
        return surgeryRepository.save(surgery);
    }

    public void deleteSurgery(UUID id) {
        surgeryRepository.deleteById(id);
    }
}
