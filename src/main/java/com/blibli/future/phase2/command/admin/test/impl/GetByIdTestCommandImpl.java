package com.blibli.future.phase2.command.admin.test.impl;

import com.blibli.future.phase2.command.admin.test.GetByIdTestCommand;
import com.blibli.future.phase2.entity.Test;
import com.blibli.future.phase2.model.command.admin.test.GetByIdTestRequest;
import com.blibli.future.phase2.repository.TestRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
public class GetByIdTestCommandImpl implements GetByIdTestCommand {
    @Autowired
    private TestRepository testRepository;

    @Override
    public Mono<Test> execute(GetByIdTestRequest request) {
        return getQuestion(request);
    }

    private Mono<Test> getQuestion(GetByIdTestRequest request){
        return testRepository.findByBatchIdAndStageAndAndMaterialId(
                request.getBatchId(),
                Integer.parseInt(request.getTraining()),
                request.getMaterialId()
        )
                .switchIfEmpty(Mono.just(Test.builder().build()));
    }
}
