package com.omgservers.service.module.matchmaker.impl.service.matchmakerService.impl.method.matchmakerRequest.deleteMatchmakerRequest;

import com.omgservers.model.dto.matchmaker.DeleteMatchmakerRequestRequest;
import com.omgservers.model.dto.matchmaker.DeleteMatchmakerRequestResponse;
import io.smallrye.mutiny.Uni;

public interface DeleteMatchmakerRequestMethod {
    Uni<DeleteMatchmakerRequestResponse> deleteMatchmakerRequest(DeleteMatchmakerRequestRequest request);
}
