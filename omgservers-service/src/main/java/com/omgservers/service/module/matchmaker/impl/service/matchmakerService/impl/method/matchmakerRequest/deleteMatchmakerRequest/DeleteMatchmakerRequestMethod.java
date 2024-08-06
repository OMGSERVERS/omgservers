package com.omgservers.service.module.matchmaker.impl.service.matchmakerService.impl.method.matchmakerRequest.deleteMatchmakerRequest;

import com.omgservers.schema.module.matchmaker.DeleteMatchmakerRequestRequest;
import com.omgservers.schema.module.matchmaker.DeleteMatchmakerRequestResponse;
import io.smallrye.mutiny.Uni;

public interface DeleteMatchmakerRequestMethod {
    Uni<DeleteMatchmakerRequestResponse> deleteMatchmakerRequest(DeleteMatchmakerRequestRequest request);
}
