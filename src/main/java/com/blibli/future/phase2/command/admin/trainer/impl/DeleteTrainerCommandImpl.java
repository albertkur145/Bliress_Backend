package com.blibli.future.phase2.command.admin.trainer.impl;

import com.blibli.future.phase2.command.admin.trainer.DeleteTrainerCommand;
import com.blibli.future.phase2.model.response.admin.trainer.DeleteTrainerResponse;
import com.blibli.future.phase2.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
public class DeleteTrainerCommandImpl implements DeleteTrainerCommand {
    @Autowired
    private UserRepository userRepository;

    @Override
    public Mono<DeleteTrainerResponse> execute(String request) {
        return Mono.from(userRepository.findById(request))
                .switchIfEmpty(Mono.error(NullPointerException::new))
                .flatMap(user -> userRepository.delete(user))
                .thenReturn(createResponse(HttpStatus.OK, "Trainer has been deleted"))
                .onErrorReturn(createResponse(HttpStatus.BAD_REQUEST, "Trainer does not exist"));
    }

    private DeleteTrainerResponse createResponse(HttpStatus status, String message){
        return DeleteTrainerResponse.builder()
                .status(status)
                .message(message)
                .build();
    }
}
