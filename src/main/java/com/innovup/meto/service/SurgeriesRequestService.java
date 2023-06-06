package com.innovup.meto.service;

import com.innovup.meto.entity.SurgeriesRequest;
import com.innovup.meto.entity.Surgery;
import com.innovup.meto.entity.SurgeryDuration;
import com.innovup.meto.enums.Role;
import com.innovup.meto.exception.SurgeryNotFoundException;
import com.innovup.meto.exception.UserNotFoundException;
import com.innovup.meto.mapper.SurgeryMapper;
import com.innovup.meto.pojo.SurgeriesRequestData;
import com.innovup.meto.repository.SurgeriesRequestRepository;
import com.innovup.meto.repository.SurgeryRepository;
import com.innovup.meto.repository.UserRepository;
import com.innovup.meto.request.SurgeryRequest;
import com.innovup.meto.result.SurgeryResult;
import com.innovup.meto.utils.DurationConverter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
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

    public SurgeryResult approveAndAssignSurgeryRequest(UUID surgeryRequestId) {
        var surgeryRequestApprovalData = approveSurgeryRequest(surgeryRequestId);
        assignSurgery(surgeryRequestApprovalData.getSurgeriesRequest().getRequester().getId(), surgeryRequestApprovalData.getSurgery());
        return surgeryMapper.entityToResult(surgeryRequestApprovalData.getSurgery());
    }

    public List<SurgeriesRequest> findAllRequestedSurgeries() {
        return surgeriesRequestRepository.findAll();
    }


    private void assignSurgery(UUID doctorId, Surgery surgery) {
        var doctor = userRepository.findById(doctorId).orElseThrow(() -> new UserNotFoundException(Role.DOCTOR));
        doctor.getSpecialties().add(surgery);
        userRepository.save(doctor);
    }

    private SurgeriesRequestData approveSurgeryRequest(UUID surgeryRequestId) {
        var surgeriesRequest = surgeriesRequestRepository.findById(surgeryRequestId).orElseThrow(SurgeryNotFoundException::new);
        var surgery = Surgery.builder()
                .withId(surgeriesRequest.getId())
                .withName(surgeriesRequest.getName())
                .withDescription(surgeriesRequest.getDescription())
                .withImage(surgeriesRequest.getImage())
                .withDuration(
                        SurgeryDuration.builder()
                                .withDays(surgeriesRequest.getDuration().getDays())
                                .withHours(surgeriesRequest.getDuration().getHours())
                                .withMinutes(surgeriesRequest.getDuration().getMinutes())
                                .withSeconds(surgeriesRequest.getDuration().getSeconds())
                                .build()
                )
                .withDurationInSeconds(DurationConverter.toSeconds(surgeriesRequest.getDuration()))
                .build();

        surgeriesRequestRepository.deleteById(surgeryRequestId);
        surgery = surgeryRepository.save(surgery);

        return SurgeriesRequestData.builder()
                .withSurgeriesRequest(surgeriesRequest)
                .withSurgery(surgery)
                .build();
    }
}
