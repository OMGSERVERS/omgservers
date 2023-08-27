package com.omgservers.module.user.impl.service.userService;

import com.omgservers.module.user.impl.service.userService.request.RespondClientRequest;
import io.smallrye.mutiny.Uni;

import java.time.Duration;

public interface UserService {

    Uni<Void> respondClient(RespondClientRequest request);

    default void respondClient(long timeout, RespondClientRequest request) {
        respondClient(request)
                .await().atMost(Duration.ofSeconds(timeout));
    }
}
