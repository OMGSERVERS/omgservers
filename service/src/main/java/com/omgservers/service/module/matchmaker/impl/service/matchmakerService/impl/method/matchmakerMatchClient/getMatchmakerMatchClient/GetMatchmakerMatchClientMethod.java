package com.omgservers.service.module.matchmaker.impl.service.matchmakerService.impl.method.matchmakerMatchClient.getMatchmakerMatchClient;

import com.omgservers.schema.module.matchmaker.GetMatchmakerMatchClientRequest;
import com.omgservers.schema.module.matchmaker.GetMatchmakerMatchClientResponse;
import io.smallrye.mutiny.Uni;

public interface GetMatchmakerMatchClientMethod {
    Uni<GetMatchmakerMatchClientResponse> getMatchmakerMatchClient(GetMatchmakerMatchClientRequest request);
}
