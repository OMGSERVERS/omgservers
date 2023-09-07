package com.omgservers.module.matchmaker.impl.service.matchmakerService.impl.method.executeMatchmaker;

import com.omgservers.dto.matchmaker.ExecuteMatchmakerRequest;
import com.omgservers.dto.matchmaker.ExecuteMatchmakerResponse;
import io.smallrye.mutiny.Uni;

import java.time.Duration;

public interface ExecuteMatchmakerMethod {
    Uni<ExecuteMatchmakerResponse> executeMatchmaker(ExecuteMatchmakerRequest request);

    default ExecuteMatchmakerResponse executeMatchmaker(long timeout, ExecuteMatchmakerRequest request) {
        return executeMatchmaker(request)
                .await().atMost(Duration.ofSeconds(timeout));
    }
}
