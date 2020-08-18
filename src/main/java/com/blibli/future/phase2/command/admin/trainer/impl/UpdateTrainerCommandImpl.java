package com.blibli.future.phase2.command.admin.trainer.impl;

import com.blibli.future.phase2.command.admin.trainer.UpdateTrainerCommand;
import com.blibli.future.phase2.entity.User;
import com.blibli.future.phase2.model.command.admin.trainer.UpdateTrainerRequest;
import com.blibli.future.phase2.model.response.admin.trainer.UpdateTrainerResponse;
import com.blibli.future.phase2.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
public class UpdateTrainerCommandImpl implements UpdateTrainerCommand {
    @Autowired
    private UserRepository userRepository;

    @Override
    public Mono<UpdateTrainerResponse> execute(UpdateTrainerRequest request) {
        return Mono.from(getTrainer(request.getId()))
                .map(user -> updateTrainer(user, request))
                .flatMap(user -> userRepository.save(user))
                .map(user -> createResponse(HttpStatus.ACCEPTED, "Trainer's data has been updated"))
                .onErrorReturn(createResponse(HttpStatus.BAD_REQUEST, "Trainer's data cannot be updated"));
    }

    private Mono<User> getTrainer(String userId){
        return userRepository.findById(userId);
    }

    private User updateTrainer(User user, UpdateTrainerRequest request){
        user.setUsername(request.getName());
        user.setDivision(request.getDivision());
        user.setUsermail(request.getEmail());

        return user;
    }

    private UpdateTrainerResponse createResponse(HttpStatus status, String message){
        return UpdateTrainerResponse.builder()
                .status(status)
                .message(message)
                .build();
    }
}
