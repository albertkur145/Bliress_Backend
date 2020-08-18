package com.blibli.future.phase2.command.admin.employee.impl;

import com.blibli.future.phase2.command.admin.employee.DeleteEmployeeCommand;
import com.blibli.future.phase2.model.response.admin.employee.DeleteEmployeeResponse;
import com.blibli.future.phase2.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
public class DeleteEmployeeCommandImpl implements DeleteEmployeeCommand {
    @Autowired
    private UserRepository userRepository;

    @Override
    public Mono<DeleteEmployeeResponse> execute(String request) {

        return Mono.from(userRepository.findById(request))
                .switchIfEmpty(Mono.error(NullPointerException::new))
                .flatMap(user -> userRepository.delete(user))
                .thenReturn(createResponse(HttpStatus.OK, "Employee data has been deleted"))
                .onErrorReturn(createResponse(HttpStatus.BAD_REQUEST, "Employee data does not exist"));
    }

    private DeleteEmployeeResponse createResponse(HttpStatus status, String message){
        return DeleteEmployeeResponse.builder()
                        .status(status)
                        .message(message)
                        .build();
    }
}
