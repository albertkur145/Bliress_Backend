package com.blibli.future.phase2.controller.admin;

import com.blibli.future.phase2.command.admin.batch.CreateBatchCommand;
import com.blibli.future.phase2.configuration.UserTokenProvider;
import com.blibli.future.phase2.entity.enumerate.Role;
import com.blibli.future.phase2.model.command.admin.batch.CreateBatchRequest;
import com.blibli.future.phase2.model.response.admin.batch.CreateBatchResponse;
import com.blibli.oss.command.CommandExecutor;
import com.blibli.oss.common.response.Response;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Mono;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.mockito.MockitoAnnotations.initMocks;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
@AutoConfigureWebTestClient
public class BatchControllerTest {
    @Autowired
    private WebTestClient webTestClient;

    @MockBean(name = "commandExecutor")
    private CommandExecutor commandExecutor;

    private CreateBatchRequest request;
    private Response<CreateBatchResponse> expectedResponse;

    @Before
    public void setUp(){
        initMocks(this);
        request = CreateBatchRequest.builder()
                .batch("1")
                .year("2020")
                .build();
        expectedResponse = Response.<CreateBatchResponse>builder().build();
    }

    @Test
    public void createBatchSuccess_test(){
        expectedResponse.setData(
                CreateBatchResponse.builder()
                        .status(HttpStatus.ACCEPTED)
                        .message("Batch has been created")
                        .build()
        );

        given(commandExecutor.execute(CreateBatchCommand.class, request))
                .willReturn(Mono.just(expectedResponse.getData()));

        webTestClient.post().uri("/api/admin/batch")
                .header("Authorization", "Bearer " + UserTokenProvider.getTokenFromRole(Role.ROLE_ADMIN.toString()))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .body(Mono.just(request), CreateBatchRequest.class)
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("$.code").isEqualTo(HttpStatus.ACCEPTED.value());

        verify(commandExecutor).execute(CreateBatchCommand.class, request);
    }

}
