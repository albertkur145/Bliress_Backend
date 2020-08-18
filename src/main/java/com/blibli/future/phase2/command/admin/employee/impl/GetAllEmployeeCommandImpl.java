package com.blibli.future.phase2.command.admin.employee.impl;

import com.blibli.future.phase2.command.admin.employee.GetAllEmployeeCommand;
import com.blibli.future.phase2.entity.enumerate.Role;
import com.blibli.future.phase2.model.command.BlankRequest;
import com.blibli.future.phase2.model.response.admin.employee.GetAllEmployeeResponse;
import com.blibli.future.phase2.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

@Service
public class GetAllEmployeeCommandImpl implements GetAllEmployeeCommand {
    @Autowired
    private UserRepository userRepository;

    @Override
    public Mono<GetAllEmployeeResponse> execute(BlankRequest request) {
        return Mono.fromCallable(() -> getAllEmployee());
    }

    private GetAllEmployeeResponse getAllEmployee(){
        Set<Role> roles = Collections.singleton(Role.ROLE_EMPLOYEE);

        return GetAllEmployeeResponse.builder()
                .employeeList(
                        new HashSet<>(Collections.unmodifiableList(
                                userRepository.findAllByRolesOrderByUsernameAsc(roles).collectList().block()
                        ))
                )
                .build();
    }
}
