package com.blibli.future.phase2.command.admin.trainer.impl;

import com.blibli.future.phase2.command.admin.trainer.GetAllTrainerCommand;
import com.blibli.future.phase2.entity.enumerate.Role;
import com.blibli.future.phase2.model.command.BlankRequest;
import com.blibli.future.phase2.model.response.admin.trainer.GetAllTrainerResponse;
import com.blibli.future.phase2.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

@Service
public class GetAllTrainerCommandImpl implements GetAllTrainerCommand {
    @Autowired
    private UserRepository userRepository;

    @Override
    public Mono<GetAllTrainerResponse> execute(BlankRequest request) {
        return Mono.fromCallable(() -> getAllTrainer());
    }

    private GetAllTrainerResponse getAllTrainer(){
        Set<Role> roles = Collections.singleton(Role.ROLE_TRAINER);

        return GetAllTrainerResponse.builder()
                .trainerList(
                        new HashSet<>(Collections.unmodifiableList(
                                userRepository.findAllByRolesOrderByUsernameAsc(roles).collectList().block()
                        ))
                )
                .build();
    }
}
