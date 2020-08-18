package com.blibli.future.phase2.command.auth.impl;

import com.blibli.future.phase2.command.auth.ChangePasswordCommand;
import com.blibli.future.phase2.entity.User;
import com.blibli.future.phase2.exception.CustomBadRequestException;
import com.blibli.future.phase2.model.command.auth.ChangePasswordRequest;
import com.blibli.future.phase2.model.response.auth.ChangePasswordResponse;
import com.blibli.future.phase2.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
public class ChangePasswordCommandImpl implements ChangePasswordCommand {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public Mono<ChangePasswordResponse> execute(ChangePasswordRequest request) {
        return getUser(request.getId())
                .flatMap(user -> {
                    try {
                        return changePassword(user, request);
                    } catch (CustomBadRequestException e) {
                        return Mono.error(e);
                    }
                })
                .flatMap(user -> userRepository.save(user))
                .map(user -> createResponse(HttpStatus.ACCEPTED, "Password has been changed"))
                .onErrorResume(error -> Mono.just(createResponse(HttpStatus.BAD_REQUEST, error.getMessage())));

    }

    private Mono<User> getUser(String id){
        return userRepository.findById(id)
                .switchIfEmpty(Mono.error(new Throwable(new CustomBadRequestException("User does not found"))));
    }

    private Mono<User> changePassword (User user, ChangePasswordRequest request) throws CustomBadRequestException{
        if(passwordEncoder.encode(request.getOldPassword()).equals(user.getPassword())){
            user.setPassword(passwordEncoder.encode(request.getCurrentPassword()));
            return Mono.just(user);
        } else {
            return Mono.error(new Throwable(new CustomBadRequestException("Old password does not match")));
        }
    }

    private ChangePasswordResponse createResponse(HttpStatus status, String message){
        return ChangePasswordResponse.builder()
                .status(status)
                .message(message)
                .build();
    }
}
