package com.innovup.meto.service;

import com.innovup.meto.entity.SurgeriesRequest;
import com.innovup.meto.entity.Surgery;
import com.innovup.meto.entity.SurgeryDuration;
import com.innovup.meto.enums.Role;
import com.innovup.meto.exception.SurgeryNotFoundException;
import com.innovup.meto.exception.UserNotFoundException;
import com.innovup.meto.mapper.SurgeryMapper;
import com.innovup.meto.repository.SurgeriesRequestRepository;
import com.innovup.meto.repository.SurgeryRepository;
import com.innovup.meto.repository.UserRepository;
import com.innovup.meto.request.SurgeryRequest;
import com.innovup.meto.result.SurgeryResult;
import com.innovup.meto.utils.DurationConverter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class SurgeriesRequestService {

    private final UserRepository userRepository;
    private final SurgeriesRequestRepository surgeriesRequestRepository;
    private final SurgeryRepository surgeryRepository;
    private final SurgeryMapper surgeryMapper;

    public SurgeriesRequest createSurgeryRequest(UUID doctorId, SurgeryRequest request) {
        var doctor = userRepository.findById(doctorId).orElseThrow(() -> new UserNotFoundException(Role.DOCTOR));
        var surgeryRequest = SurgeriesRequest.builder()
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
                .withRequester(doctor)
                .build();
        surgeryRequest = surgeriesRequestRepository.save(surgeryRequest);
        return surgeryRequest;
    }

    public SurgeryResult approveSurgeryRequest(UUID surgeryId) {
        var surgeryRequest = surgeriesRequestRepository.findById(surgeryId).orElseThrow(SurgeryNotFoundException::new);
        log.info("Surgery request found - surgeryRequest = {}", surgeryRequest.toString());
        var surgery = Surgery.builder()
                .withId(surgeryRequest.getId())
                .withName(surgeryRequest.getName())
                .withDescription(surgeryRequest.getDescription())
                .withImage(surgeryRequest.getImage())
                .withDuration(
                        SurgeryDuration.builder()
                                .withDays(surgeryRequest.getDuration().getDays())
                                .withHours(surgeryRequest.getDuration().getHours())
                                .withMinutes(surgeryRequest.getDuration().getMinutes())
                                .withSeconds(surgeryRequest.getDuration().getSeconds())
                                .build()
                )
                .withDurationInSeconds(DurationConverter.toSeconds(surgeryRequest.getDuration()))
                .build();
        surgeriesRequestRepository.deleteById(surgeryId);
        surgery = surgeryRepository.save(surgery);
        return surgeryMapper.entityToResult(surgery);
    }

    public List<SurgeriesRequest> findAllRequestedSurgeries() {
        return surgeriesRequestRepository.findAll();
    }
}
