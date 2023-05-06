package com.innovup.meto.service;

import com.innovup.meto.entity.PatientOpinion;
import com.innovup.meto.enums.Role;
import com.innovup.meto.exception.UserNotFoundException;
import com.innovup.meto.mapper.OpinionMapper;
import com.innovup.meto.repository.PatientOpinionRepo;
import com.innovup.meto.repository.UserRepository;
import com.innovup.meto.request.OpinionRequest;
import com.innovup.meto.result.OpinionResult;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class OpinionService {
    private final PatientOpinionRepo patientOpinionRepo;
    private final OpinionMapper opinionMapper;
    private final UserRepository userRepository;
    public OpinionResult createPatientOpinion( OpinionRequest request) {

        var opinion = PatientOpinion.builder()
                .withId(UUID.randomUUID())
                .withImage(request.getImage())
                .withMessage(request.getMessage())
                .withIsEnabled(false)
                .withCreatedOn(LocalDateTime.now())

                .withPatient(request.getPatientId() != null ? userRepository.findById(request.getPatientId()).orElseThrow(() -> new UserNotFoundException(Role.PATIENT)) : null)
                .build();
        opinion= patientOpinionRepo.save(opinion);
        return opinionMapper.entityToResult(patientOpinionRepo.save(opinion));
    }
    public List<OpinionResult> getAllPatientOpinionAndIsEnabledFalse(){
        return patientOpinionRepo.findByIsEnabledFalse().stream()
                .map(opinionMapper::entityToResult)
                .toList();
    }
    public List<OpinionResult> getAllPatientOpinionAndIsEnabledTrue(){
        return patientOpinionRepo.findByIsEnabledTrue().stream()
                .map(opinionMapper::entityToResult)
                .toList();
    }

    public PatientOpinion accepter(UUID opinionId) {
        var opinion = patientOpinionRepo.findById(opinionId);

        if (opinion.isPresent() && opinion.get().isEnabled() == false) {
            var op = opinion.get();
            op.setEnabled((true));



            return patientOpinionRepo.save(op);
        } else {
            throw new RuntimeException();
        }
    }
    public void delete(UUID id ){
        patientOpinionRepo.deleteById(id);
    }

}
