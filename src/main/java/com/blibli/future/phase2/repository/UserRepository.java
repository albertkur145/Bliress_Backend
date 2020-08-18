package com.blibli.future.phase2.repository;

import com.blibli.future.phase2.entity.User;
import com.blibli.future.phase2.entity.enumerate.Role;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Set;

public interface UserRepository extends ReactiveMongoRepository<User, String> {
    Mono<User> findByUsermail(String usermail);

    Flux<User> findAllByBatch(String batch);

    Flux<User> findAllByRolesOrderByUsernameAsc(Set<Role> roles);

    Flux<User> findAllByBatchOrderByUsernameAsc(String batch);
}
