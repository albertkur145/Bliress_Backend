package com.blibli.future.phase2.command.admin.employee.impl;

import com.blibli.future.phase2.command.admin.employee.CreateEmployeeCommand;
import com.blibli.future.phase2.component.CustomPasswordEncoder;
import com.blibli.future.phase2.entity.User;
import com.blibli.future.phase2.entity.enumerate.Role;
import com.blibli.future.phase2.model.command.admin.employee.CreateEmployeeRequest;
import com.blibli.future.phase2.model.response.admin.employee.CreateEmployeeResponse;
import com.blibli.future.phase2.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.UUID;

@Service
public class CreateEmployeeCommandImpl implements CreateEmployeeCommand {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CustomPasswordEncoder passwordEncoder;

    @Override
    public Mono<CreateEmployeeResponse> execute(CreateEmployeeRequest request) {
        return Mono.from(checkIfEmployeeIsExist(request))
                .flatMap(result -> (result) ? Mono.just(null) : Mono.just(createEmployee(request)))
                .flatMap(user -> (user == null) ? Mono.just(null) : userRepository.save(user))
                .map(user -> createResponse(HttpStatus.ACCEPTED, "Employee data has been created"))
                .onErrorReturn(createResponse(HttpStatus.BAD_REQUEST, "Employee cannot be created"));
    }

    public Mono<Boolean> checkIfEmployeeIsExist(CreateEmployeeRequest request){
        return Mono.from(userRepository.findByUsermail(request.getEmail())
                .switchIfEmpty(Mono.just(User.builder().build()))
                .map(user -> user.getUserId() != null));
    }

    private User createEmployee(CreateEmployeeRequest request){
        return User.builder()
                .userId(UUID.randomUUID().toString())
                .username(request.getName())
                .usermail(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .roles(Collections.singleton(Role.ROLE_EMPLOYEE))
                .division(request.getDivision())
                .gender(request.getGender().toUpperCase())
                .birthDate(request.getBirthdate())
                .phoneNumber(request.getPhoneNumber())
                .batch(request.getBatchId())
                .stage(1)
                .registeredAt(LocalDateTime.now())
                .build();
    }

    private CreateEmployeeResponse createResponse(HttpStatus status, String message){
        return CreateEmployeeResponse.builder()
                .status(status)
                .message(message)
                .build();
    }
}
