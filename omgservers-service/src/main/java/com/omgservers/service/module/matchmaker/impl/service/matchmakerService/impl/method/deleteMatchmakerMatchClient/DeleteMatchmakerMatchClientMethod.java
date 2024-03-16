package com.omgservers.service.module.matchmaker.impl.service.matchmakerService.impl.method.deleteMatchmakerMatchClient;

import com.omgservers.model.dto.matchmaker.DeleteMatchmakerMatchClientRequest;
import com.omgservers.model.dto.matchmaker.DeleteMatchmakerMatchClientResponse;
import io.smallrye.mutiny.Uni;

public interface DeleteMatchmakerMatchClientMethod {
    Uni<DeleteMatchmakerMatchClientResponse> deleteMatchmakerMatchClient(DeleteMatchmakerMatchClientRequest request);
}
