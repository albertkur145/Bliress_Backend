package com.blibli.future.phase2.command.auth;

import com.blibli.future.phase2.command.auth.impl.LoginCommandImpl;
import com.blibli.future.phase2.component.CustomPasswordEncoder;
import com.blibli.future.phase2.component.JwtTokenProvider;
import com.blibli.future.phase2.entity.User;
import com.blibli.future.phase2.model.command.auth.LoginRequest;
import com.blibli.future.phase2.model.response.auth.LoginResponse;
import com.blibli.future.phase2.repository.UserRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import reactor.core.publisher.Mono;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.MockitoAnnotations.initMocks;

public class LoginCommandTest {
    @InjectMocks
    LoginCommandImpl loginCommand;

    @Mock
    UserRepository userRepository;

    @Mock
    CustomPasswordEncoder passwordEncoder;

    @Mock
    JwtTokenProvider jwtTokenProvider;

    private User user;
    private LoginResponse expectedSuccess;

    @BeforeEach
    void setUp(){
        initMocks(this);
        user = User.builder()
                .username("testing1")
                .usermail("testing1@mail.com")
                .password("password")
                .build();

        expectedSuccess = LoginResponse.builder()
                .token("token")
                .message("SUCCESS")
                .build();
    }

    @AfterEach
    void tearDown() {
        verifyNoMoreInteractions(userRepository);
    }

    @Test
    void executeSuccess_test(){
        given(userRepository.findByUsermail(user.getUsername()))
                .willReturn(Mono.just(user));

        given(jwtTokenProvider.generateToken(user))
                .willReturn("token");

        LoginRequest request = LoginRequest.builder()
                .usermail("testing1@mail.com")
                .password("password")
                .build();

        given(passwordEncoder.encode(request.getPassword()))
                .willReturn(request.getPassword());

        LoginResponse result = loginCommand.execute(request).block();

        assertEquals(expectedSuccess, result);
        verify(userRepository).findByUsermail(request.getUsermail());
    }

    @Test
    void executeWrongUsername_test(){
        given(jwtTokenProvider.generateToken(user))
                .willReturn("token");

        LoginRequest request = LoginRequest.builder()
                .usermail("testing11@mail.com")
                .password("password")
                .build();

        given(userRepository.findByUsermail(request.getUsermail()))
                .willReturn(Mono.error(new Exception("No user account was found with email: " + request.getUsermail())));

        given(passwordEncoder.encode(request.getPassword()))
                .willReturn(request.getPassword());

        LoginResponse result = loginCommand.execute(request).block();

        assertNotEquals(expectedSuccess, result);
        assertEquals(null, result.getToken());
        assertEquals("Username or Password is wrong", result.getMessage());
        verify(userRepository).findByUsermail(request.getUsermail());
    }

    @Test
    void executeWrongPassword_test(){
        given(userRepository.findByUsermail(user.getUsermail()))
                .willReturn(Mono.just(user));

        given(jwtTokenProvider.generateToken(user))
                .willReturn("token");

        LoginRequest request = LoginRequest.builder()
                .usermail("testing1@mail.com")
                .password("passwordd")
                .build();

        given(passwordEncoder.encode(request.getPassword()))
                .willReturn(request.getPassword());

        LoginResponse result = loginCommand.execute(request).block();

        assertNotEquals(expectedSuccess, result);
        assertEquals(null, result.getToken());
        assertEquals("Username or Password is wrong", result.getMessage());
        verify(userRepository).findByUsermail(request.getUsermail());
    }
}
