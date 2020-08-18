package com.blibli.future.phase2.command.auth.impl;

import com.blibli.future.phase2.command.auth.ResetPasswordCommand;
import com.blibli.future.phase2.entity.User;
import com.blibli.future.phase2.model.response.auth.ResetPasswordResponse;
import com.blibli.future.phase2.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
public class ResetPasswordCommandImpl implements ResetPasswordCommand {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Value("${app.defaultPasswordUser}")
    private String defaultPassword;

    @Override
    public Mono<ResetPasswordResponse> execute(String request) {
        return Mono.from(getUser(request))
                .map(user -> resetPasswordUser(user))
                .flatMap(user -> userRepository.save(user))
                .map(user -> createResponse(HttpStatus.ACCEPTED, "User's password has been deleted. " +
                        "Password turns into \"blibliprobation\" as default"))
                .onErrorReturn(createResponse(HttpStatus.BAD_REQUEST, "User's password cannot be deleted"));
    }

    private Mono<User> getUser(String userId){
        return userRepository.findById(userId);
    }

    private User resetPasswordUser(User user){
        user.setPassword(passwordEncoder.encode(defaultPassword));

        return user;
    }

    private ResetPasswordResponse createResponse(HttpStatus status, String message){
        return ResetPasswordResponse.builder()
                .status(status)
                .message(message)
                .build();
    }
}
