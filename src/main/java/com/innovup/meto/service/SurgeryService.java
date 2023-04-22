package com.innovup.meto.service;

import com.innovup.meto.entity.Surgery;
import com.innovup.meto.entity.SurgeryDuration;
import com.innovup.meto.exception.SurgeryNotFoundException;
import com.innovup.meto.mapper.SurgeryMapper;
import com.innovup.meto.repository.SurgeryRepository;
import com.innovup.meto.request.SurgeryRequest;
import com.innovup.meto.result.SurgeryResult;
import com.innovup.meto.utils.DurationConverter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class SurgeryService {

    private final SurgeryRepository surgeryRepository;

    private final SurgeryMapper surgeryMapper;


    public List<SurgeryResult> findAllSurgeries() {
        return surgeryRepository.findAll().stream()
                .map(surgeryMapper::entityToResult)
                .toList();
    }

    public Optional<SurgeryResult> findSurgeryById(UUID id) {
        return surgeryRepository.findById(id)
                .map(surgeryMapper::entityToResult);
    }

    public List<Surgery> findSurgeriesByIds(List<UUID> ids) {
        return ids.stream()
                .map(this::surgeryById)
                .toList();
    }

    private Surgery surgeryById(UUID id) {
        return surgeryRepository.findById(id).orElse(null);
    }

    public SurgeryResult addSurgery(SurgeryRequest request) {
        var surgery = Surgery.builder()
                .withId(UUID.randomUUID())
                .withName(request.getName())
                .withDescription(request.getDescription())
                .withImage(request.getImage())
                .withDuration(
                        SurgeryDuration.builder()
                                .withDays(request.getDuration().getDays())
                                .withHours(request.getDuration().getHours())
                                .withMinutes(request.getDuration().getMinutes())
                                .withSeconds(request.getDuration().getSeconds())
                                .build()
                )
                .withDurationInSeconds(DurationConverter.toSeconds(request.getDuration()))
                .build();
        surgery = surgeryRepository.save(surgery);
        return surgeryMapper.entityToResult(surgery);
    }

    public SurgeryResult updateSurgery(UUID id, SurgeryRequest request) {
        var optional = surgeryRepository.findById(id);
        if (optional.isPresent()) {
            var surgery = optional.get();
            surgery.setName(request.getName());
            surgery.setDescription(request.getDescription());
            surgery.setDuration(
                    SurgeryDuration.builder()
                            .withDays(request.getDuration().getDays())
                            .withHours(request.getDuration().getHours())
                            .withMinutes(request.getDuration().getMinutes())
                            .withSeconds(request.getDuration().getSeconds())
                            .build()
            );
            surgery.setDurationInSeconds(DurationConverter.toSeconds(request.getDuration()));
            surgery.setImage(request.getImage());
            surgery = surgeryRepository.save(surgery);
            return surgeryMapper.entityToResult(surgery);
        } else {
            throw new SurgeryNotFoundException();
        }
    }

    public void deleteSurgery(UUID id) {
        surgeryRepository.deleteById(id);
    }
}
