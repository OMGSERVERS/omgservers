package com.omgservers.module.user.impl.service.userService;

import com.omgservers.dto.user.RespondClientRequest;
import com.omgservers.dto.user.SyncUserRequest;
import com.omgservers.dto.user.SyncUserResponse;
import com.omgservers.dto.user.ValidateCredentialsRequest;
import com.omgservers.dto.user.ValidateCredentialsResponse;
import io.smallrye.mutiny.Uni;

import java.time.Duration;

public interface UserService {

    Uni<SyncUserResponse> syncUser(SyncUserRequest request);

    Uni<ValidateCredentialsResponse> validateCredentials(ValidateCredentialsRequest request);

    Uni<Void> respondClient(RespondClientRequest request);

    default void respondClient(long timeout, RespondClientRequest request) {
        respondClient(request)
                .await().atMost(Duration.ofSeconds(timeout));
    }
}
