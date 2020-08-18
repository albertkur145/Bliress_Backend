package com.blibli.future.phase2.command.admin.trainer.impl;

import com.blibli.future.phase2.command.admin.trainer.CreateTrainerCommand;
import com.blibli.future.phase2.component.CustomPasswordEncoder;
import com.blibli.future.phase2.entity.User;
import com.blibli.future.phase2.entity.enumerate.Role;
import com.blibli.future.phase2.model.command.admin.trainer.CreateTrainerRequest;
import com.blibli.future.phase2.model.response.admin.trainer.CreateTrainerResponse;
import com.blibli.future.phase2.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.HashSet;
import java.util.UUID;

@Service
public class CreateTrainerCommandImpl implements CreateTrainerCommand {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CustomPasswordEncoder passwordEncoder;

    @Override
    public Mono<CreateTrainerResponse> execute(CreateTrainerRequest request) {
        return Mono.from(checkIfTrainerIsExist(request))
                .flatMap(result -> (result) ? Mono.just(null) : Mono.just(createTrainerUser(request)))
                .flatMap(user -> (user == null) ? Mono.just(null) : userRepository.save(user))
                .map(user -> createResponse(HttpStatus.ACCEPTED, "Trainer data has been created"))
                .onErrorReturn(createResponse(HttpStatus.BAD_REQUEST, "Trainer cannot be created"));
    }

    public Mono<Boolean> checkIfTrainerIsExist(CreateTrainerRequest request){
        return Mono.from(userRepository.findByUsermail(request.getEmail())
                .switchIfEmpty(Mono.just(User.builder().build()))
                .map(user -> user.getUserId() != null));
    }

    public User createTrainerUser(CreateTrainerRequest request){
        return User.builder()
                .userId(UUID.randomUUID().toString())
                .username(request.getName())
                .usermail(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .division(request.getDivision())
                .roles(new HashSet<>(Collections.singleton(Role.ROLE_TRAINER)))
                .registeredAt(LocalDateTime.now())
                .build();
    }

    public CreateTrainerResponse createResponse(HttpStatus status, String message){
        return CreateTrainerResponse.builder()
                .status(status)
                .message(message)
                .build();
    }
}
