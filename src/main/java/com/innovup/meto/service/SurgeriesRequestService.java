package com.innovup.meto.service;

import com.innovup.meto.entity.SurgeriesRequest;
import com.innovup.meto.entity.SurgeryDuration;
import com.innovup.meto.enums.Role;
import com.innovup.meto.exception.UserNotFoundException;
import com.innovup.meto.repository.SurgeriesRequestRepository;
import com.innovup.meto.repository.UserRepository;
import com.innovup.meto.request.SurgeryRequest;
import com.innovup.meto.utils.DurationConverter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class SurgeriesRequestService {

    private final UserRepository userRepository;
    private final SurgeriesRequestRepository surgeriesRequestRepository;

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

    public List<SurgeriesRequest> findAllRequestedSurgeries() {
        return surgeriesRequestRepository.findAll();
    }
}
