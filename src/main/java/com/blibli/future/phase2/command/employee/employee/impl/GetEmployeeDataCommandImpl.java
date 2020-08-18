package com.blibli.future.phase2.command.employee.employee.impl;

import com.blibli.future.phase2.command.employee.employee.GetEmployeeDataCommand;
import com.blibli.future.phase2.component.AuthenticatedUserProvider;
import com.blibli.future.phase2.entity.User;
import com.blibli.future.phase2.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
public class GetEmployeeDataCommandImpl implements GetEmployeeDataCommand {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AuthenticatedUserProvider authenticatedUserProvider;

    @Override
    public Mono<User> execute(String request) {
        return Mono.from(
                userRepository.findById(
                        (String) authenticatedUserProvider.getValueFromUserData(request, "userId")
                )
        ).switchIfEmpty(Mono.just(User.builder().build()));
    }
}
