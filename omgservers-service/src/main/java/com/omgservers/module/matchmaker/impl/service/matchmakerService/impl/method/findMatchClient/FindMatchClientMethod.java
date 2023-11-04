package com.omgservers.module.matchmaker.impl.service.matchmakerService.impl.method.findMatchClient;

import com.omgservers.model.dto.matchmaker.FindMatchClientRequest;
import com.omgservers.model.dto.matchmaker.FindMatchClientResponse;
import io.smallrye.mutiny.Uni;

public interface FindMatchClientMethod {
    Uni<FindMatchClientResponse> findMatchClient(FindMatchClientRequest request);
}
