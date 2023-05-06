package com.innovup.meto.service;

import com.innovup.meto.entity.ChirurgieDuration;
import com.innovup.meto.entity.ChirurgieRequestDoctor;
import com.innovup.meto.enums.Role;
import com.innovup.meto.exception.UserNotFoundException;
import com.innovup.meto.repository.ChirurgieRequestRepository;
import com.innovup.meto.repository.UserRepository;
import com.innovup.meto.request.ChirurgieRequest;
import com.innovup.meto.util.DurationConverter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
@Service
@RequiredArgsConstructor

public class ChirurgieRequestService {
    private final UserRepository userRepository;
    private final ChirurgieRequestRepository surgeriesRequestRepository;

    public ChirurgieRequestDoctor createSurgeryRequest(UUID doctorId, ChirurgieRequest request) {
        var doctor = userRepository.findById(doctorId).orElseThrow(() -> new UserNotFoundException(Role.DOCTOR));
        var chirurgieReq = ChirurgieRequestDoctor.builder()
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
                .withRequester(doctor)
                .build();
        chirurgieReq = surgeriesRequestRepository.save(chirurgieReq);
        return chirurgieReq;
    }

    public List<ChirurgieRequestDoctor> findAllRequestedSurgeries() {
        return surgeriesRequestRepository.findAll();
    }
}
