package com.blibli.future.phase2.command.employee.training.impl;

import com.blibli.future.phase2.command.employee.training.SubmitAttendanceTrainingCommand;
import com.blibli.future.phase2.component.AuthenticatedUserProvider;
import com.blibli.future.phase2.entity.TrainingAttendance;
import com.blibli.future.phase2.model.command.user.training.SubmitAttendanceTrainingRequest;
import com.blibli.future.phase2.model.response.user.training.SubmitAttendanceTrainingResponse;
import com.blibli.future.phase2.repository.TrainingAttendanceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
public class SubmitAttendanceTrainingCommandImpl implements SubmitAttendanceTrainingCommand {
    @Autowired
    private TrainingAttendanceRepository trainingAttendanceRepository;

    @Autowired
    private AuthenticatedUserProvider authenticatedUserProvider;

    @Override
    public Mono<SubmitAttendanceTrainingResponse> execute(SubmitAttendanceTrainingRequest request) {
        return Mono.fromCallable(() -> checkAttendance(request))
                .flatMap(result -> {
                    if (result) {
                        return getAttendance(request.getToken());
                    } else return Mono.just(null);
                })
                .map(attendance -> changeAttendance(attendance))
                .flatMap(updated -> trainingAttendanceRepository.save(updated))
                .thenReturn(createResponse(HttpStatus.ACCEPTED, "User has been attended"))
                .onErrorReturn(createResponse(HttpStatus.BAD_REQUEST, "User cannot submit attendance"));
    }

    private Boolean checkAttendance(SubmitAttendanceTrainingRequest request){
        if ((((String) authenticatedUserProvider.getValueFromUserData(request.getToken(), "userId")).equals(request.getEmployeeId()))
        && ((Integer) authenticatedUserProvider.getValueFromUserData(request.getToken(), "stage")) == Integer.parseInt(request.getTraining()))
        {
            return Boolean.TRUE;
        }
        return Boolean.FALSE;
    }

    private Mono<TrainingAttendance> getAttendance(String token){
        return trainingAttendanceRepository.findByEmployeeId(
                (String) authenticatedUserProvider.getValueFromUserData(token, "userId")
        );
    }

    private TrainingAttendance changeAttendance(TrainingAttendance trainingAttendance){
        TrainingAttendance updated = trainingAttendance;
        updated.setStatus(Boolean.TRUE);
        return updated;
    }

    private SubmitAttendanceTrainingResponse createResponse(HttpStatus status, String message){
        return SubmitAttendanceTrainingResponse.builder()
                .status(status)
                .message(message)
                .build();
    }
}
