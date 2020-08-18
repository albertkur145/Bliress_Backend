package com.blibli.future.phase2.command.admin.trainer.impl;

import com.blibli.future.phase2.command.admin.trainer.GetByIdTrainerCommand;
import com.blibli.future.phase2.entity.User;
import com.blibli.future.phase2.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
public class GetByIdTrainerCommandImpl implements GetByIdTrainerCommand {
    @Autowired
    private UserRepository userRepository;

    @Override
    public Mono<User> execute(String request) {
        return userRepository.findById(request)
                .switchIfEmpty(Mono.just(User.builder().build()));
    }
}
