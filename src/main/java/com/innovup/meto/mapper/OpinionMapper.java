package com.innovup.meto.mapper;

import com.innovup.meto.entity.PatientOpinion;
import com.innovup.meto.result.OpinionResult;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class OpinionMapper {
    private final UserMapper userMapper;

    public OpinionResult entityToResult(PatientOpinion patientOpinion) {
        if (patientOpinion == null) {
            return null;}


            return OpinionResult.builder()
                .withId(patientOpinion.getId())
                .withImage(patientOpinion.getImage())
                .withMessage(patientOpinion.getMessage())
                    .withCreatedOn(patientOpinion.getCreatedOn())

                    .withPatient(userMapper.entityToPatient(patientOpinion.getPatient()))
                .build();
    }
}

