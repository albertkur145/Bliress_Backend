package com.blibli.future.phase2.controller.auth;

import com.blibli.future.phase2.command.auth.LoginCommand;
import com.blibli.future.phase2.model.command.auth.LoginRequest;
import com.blibli.future.phase2.model.response.auth.LoginResponse;
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
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Mono;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.mockito.MockitoAnnotations.initMocks;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureWebTestClient
public class AuthControllerTest {
    @Autowired
    private WebTestClient webTestClient;

    @MockBean(name = "commandExecutor")
    private CommandExecutor commandExecutor;

    private LoginRequest request;
    private Response<LoginResponse> expectedResponse;

    @Before
    public void setUp(){
        initMocks(this);
        request = LoginRequest.builder()
                .usermail("testing1@mail.com")
                .password("password")
                .build();
        expectedResponse = Response.<LoginResponse>builder().build();
    }

    @Test
    public void loginSuccess_test() throws Exception {
        expectedResponse.setData(
                LoginResponse.builder()
                        .token("token")
                        .message("SUCCESSS")
                        .build()
        );

        given(commandExecutor.execute(LoginCommand.class, request))
                .willReturn(Mono.just(expectedResponse.getData()));

        webTestClient.post().uri("/api/auth/login")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .body(Mono.just(request), LoginRequest.class)
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("$.code").isEqualTo(HttpStatus.OK.value())
                .jsonPath("$.data.token").isEqualTo(expectedResponse.getData().getToken())
                .jsonPath("$.data.message").isEqualTo(expectedResponse.getData().getMessage());

        verify(commandExecutor).execute(LoginCommand.class, request);
    }

    @Test
    public void loginFailedUsernameWrong_test(){
        expectedResponse.setData(
                LoginResponse.builder()
                        .token(null)
                        .message("Username or Password is wrong")
                        .build()
        );

        given(commandExecutor.execute(LoginCommand.class, request))
                .willReturn(Mono.just(expectedResponse.getData()));

        webTestClient.post().uri("/api/auth/login")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .body(Mono.just(request), LoginRequest.class)
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("$.code").isEqualTo(HttpStatus.BAD_REQUEST.value())
                .jsonPath("$.data.token").isEqualTo(expectedResponse.getData().getToken())
                .jsonPath("$.data.message").isEqualTo(expectedResponse.getData().getMessage());

        verify(commandExecutor).execute(LoginCommand.class, request);
    }
}
