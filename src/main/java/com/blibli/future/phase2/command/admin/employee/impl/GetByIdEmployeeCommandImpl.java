package com.blibli.future.phase2.command.admin.employee.impl;

import com.blibli.future.phase2.command.admin.employee.GetByIdEmployeeCommand;
import com.blibli.future.phase2.entity.User;
import com.blibli.future.phase2.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
public class GetByIdEmployeeCommandImpl implements GetByIdEmployeeCommand {
    @Autowired
    private UserRepository userRepository;

    @Override
    public Mono<User> execute(String request) {
        return userRepository.findById(request)
                .switchIfEmpty(Mono.just(User.builder().build()));
    }
}
