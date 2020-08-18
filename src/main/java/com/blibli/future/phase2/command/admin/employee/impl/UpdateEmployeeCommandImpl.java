package com.blibli.future.phase2.command.admin.employee.impl;

import com.blibli.future.phase2.command.admin.employee.UpdateEmployeeCommand;
import com.blibli.future.phase2.entity.User;
import com.blibli.future.phase2.model.command.admin.employee.UpdateEmployeeRequest;
import com.blibli.future.phase2.model.response.admin.employee.UpdateEmployeeResponse;
import com.blibli.future.phase2.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.sql.Date;
import java.sql.Timestamp;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

@Service
public class UpdateEmployeeCommandImpl implements UpdateEmployeeCommand {
    @Autowired
    private UserRepository userRepository;

    @Override
    public Mono<UpdateEmployeeResponse> execute(UpdateEmployeeRequest request) {
        return Mono.from(getEmployee(request.getId()))
                .map(user -> updateEmployee(user, request))
                .flatMap(user -> userRepository.save(user))
                .map(user -> createResponse(HttpStatus.ACCEPTED, "Employee's data has been updated"))
                .onErrorReturn(createResponse(HttpStatus.BAD_REQUEST, "Employee's data cannot be updated"));
    }

    private Mono<User> getEmployee(String userId){
        return userRepository.findById(userId);
    }

    private User updateEmployee(User user, UpdateEmployeeRequest request){
        user.setUsername(request.getName());
        user.setUsermail(request.getEmail());
        user.setDivision(request.getDivision());
        user.setPhoneNumber(request.getPhoneNumber());
        user.setBirthDate(request.getBirthdate());
        user.setGender(request.getGender().toUpperCase());

        return user;
    }

    private UpdateEmployeeResponse createResponse(HttpStatus status, String message){
        return UpdateEmployeeResponse.builder()
                .status(status)
                .message(message)
                .build();
    }

    private Instant convertStringDateToInstant(String date){
        return LocalDate.parse(date, DateTimeFormatter.ofPattern("dd-MM-yyyy"))
                .atStartOfDay(ZoneId.systemDefault()).toInstant();
    }
}
