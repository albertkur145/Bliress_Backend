package com.blibli.future.phase2.command.auth.impl;

import com.blibli.future.phase2.command.auth.CreateUserCommand;
import com.blibli.future.phase2.component.CustomPasswordEncoder;
import com.blibli.future.phase2.entity.enumerate.Role;
import com.blibli.future.phase2.entity.User;
import com.blibli.future.phase2.model.command.auth.CreateUserRequest;
import com.blibli.future.phase2.model.response.auth.CreateUserResponse;
import com.blibli.future.phase2.repository.UserRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Service
public class CreateUserCommandImpl implements CreateUserCommand {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CustomPasswordEncoder passwordEncoder;

    @Override
    public Mono<CreateUserResponse> execute(CreateUserRequest request) {
        return Mono.fromCallable(() -> createUser(request))
                .flatMap(user -> userRepository.save(user))
                .map(user -> createResponse(user));
    }

    private User createUser(CreateUserRequest request){
        User user = User.builder()
                .userId(UUID.randomUUID().toString())
                .build();
        BeanUtils.copyProperties(request, user);

        user.setPassword(passwordEncoder.encode(user.getPassword()));

        Set<Role> roles = new HashSet<>();
        roles.add(Role.ROLE_EMPLOYEE);
        user.setRoles(roles);
        return user;
    }

    private CreateUserResponse createResponse(User user){
        CreateUserResponse response = new CreateUserResponse();
        BeanUtils.copyProperties(user, response);
        return response;
    }
}
