package com.omgservers.module.user.impl.service.userService;

import com.omgservers.dto.user.RespondClientRequest;
import com.omgservers.dto.user.SyncUserShardedRequest;
import com.omgservers.dto.user.SyncUserShardedResponse;
import com.omgservers.dto.user.ValidateCredentialsShardedRequest;
import com.omgservers.dto.user.ValidateCredentialsShardedResponse;
import io.smallrye.mutiny.Uni;

import java.time.Duration;

public interface UserService {

    Uni<SyncUserShardedResponse> syncUser(SyncUserShardedRequest request);

    Uni<ValidateCredentialsShardedResponse> validateCredentials(ValidateCredentialsShardedRequest request);

    Uni<Void> respondClient(RespondClientRequest request);

    default void respondClient(long timeout, RespondClientRequest request) {
        respondClient(request)
                .await().atMost(Duration.ofSeconds(timeout));
    }
}
