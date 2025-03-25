package com.omgservers.service.shard.matchmaker.impl.service.matchmakerService.impl.method.matchmakerRequest;

import com.omgservers.schema.module.matchmaker.matchmakerRequest.DeleteMatchmakerRequestRequest;
import com.omgservers.schema.module.matchmaker.matchmakerRequest.DeleteMatchmakerRequestResponse;
import io.smallrye.mutiny.Uni;

public interface DeleteMatchmakerRequestMethod {
    Uni<DeleteMatchmakerRequestResponse> execute(DeleteMatchmakerRequestRequest request);
}
