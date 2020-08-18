package com.blibli.future.phase2.command.admin.batch;

import com.blibli.future.phase2.command.admin.batch.impl.CreateBatchCommandImpl;
import com.blibli.future.phase2.entity.Batch;
import com.blibli.future.phase2.model.command.admin.batch.CreateBatchRequest;
import com.blibli.future.phase2.model.response.admin.batch.CreateBatchResponse;
import com.blibli.future.phase2.repository.BatchRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.http.HttpStatus;
import reactor.core.publisher.Mono;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;
import static org.mockito.MockitoAnnotations.initMocks;

public class CreateBatchCommandTest {
    @InjectMocks
    CreateBatchCommandImpl createBatchCommand;

    @Mock
    BatchRepository batchRepository;

    @BeforeEach
    void setUp(){
        initMocks(this);
    }

    @AfterEach
    void tearDown(){
        verifyNoMoreInteractions(batchRepository);
    }

    @Test
    void creatBatchSuccess_test(){
        CreateBatchRequest request = CreateBatchRequest.builder()
                .batch("1")
                .year("2020")
                .build();

        Batch createdBatch = Batch.builder()
                .batchId(UUID.randomUUID().toString())
                .batchName("JAN-2020")
                .month("JANUARY")
                .year("2020")
                .build();

        given(batchRepository.save(any()))
                .willReturn(Mono.just(createdBatch));

        CreateBatchResponse response = createBatchCommand.execute(request).block();

        assertEquals(HttpStatus.ACCEPTED, response.getStatus());
        verify(batchRepository).save(any());
    }
}
