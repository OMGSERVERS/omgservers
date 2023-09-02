package com.omgservers.module.matchmaker.impl.service.matchmakerShardedService.impl.method.executeMatchmaker;

import com.omgservers.dto.matchmaker.ExecuteMatchmakerShardedRequest;
import com.omgservers.dto.matchmaker.ExecuteMatchmakerShardedResponse;
import io.smallrye.mutiny.Uni;

import java.time.Duration;

public interface ExecuteMatchmakerMethod {
    Uni<ExecuteMatchmakerShardedResponse> executeMatchmaker(ExecuteMatchmakerShardedRequest request);

    default ExecuteMatchmakerShardedResponse executeMatchmaker(long timeout, ExecuteMatchmakerShardedRequest request) {
        return executeMatchmaker(request)
                .await().atMost(Duration.ofSeconds(timeout));
    }
}
