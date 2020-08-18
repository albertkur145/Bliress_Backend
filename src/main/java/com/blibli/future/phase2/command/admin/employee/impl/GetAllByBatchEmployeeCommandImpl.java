package com.blibli.future.phase2.command.admin.employee.impl;

import com.blibli.future.phase2.command.admin.employee.GetAllByBatchEmployeeCommand;
import com.blibli.future.phase2.model.response.admin.employee.GetAllByBatchEmployeeResponse;
import com.blibli.future.phase2.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.Collections;
import java.util.HashSet;

@Service
public class GetAllByBatchEmployeeCommandImpl implements GetAllByBatchEmployeeCommand {
    @Autowired
    private UserRepository userRepository;

    @Override
    public Mono<GetAllByBatchEmployeeResponse> execute(String request) {
        return Mono.fromCallable(() -> getAllEmployeeByBatch(request));
    }

    public GetAllByBatchEmployeeResponse getAllEmployeeByBatch(String request){
        return GetAllByBatchEmployeeResponse.builder()
                .employeeList(
                        new HashSet<>(Collections.unmodifiableList(
                                userRepository.findAllByBatchOrderByUsernameAsc(request).collectList().block()
                        ))
                )
                .build();
    }
}
