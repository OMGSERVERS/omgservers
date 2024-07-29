package com.omgservers.service.module.matchmaker.impl.service.matchmakerService.impl.method.matchmakerMatchClient.findMatchmakerMatchClient;

import com.omgservers.schema.module.matchmaker.FindMatchmakerMatchClientRequest;
import com.omgservers.schema.module.matchmaker.FindMatchmakerMatchClientResponse;
import io.smallrye.mutiny.Uni;

public interface FindMatchmakerMatchClientMethod {
    Uni<FindMatchmakerMatchClientResponse> findMatchmakerMatchClient(FindMatchmakerMatchClientRequest request);
}
