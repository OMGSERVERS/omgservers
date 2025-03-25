package com.omgservers.service.shard.matchmaker.impl.service.matchmakerService.impl.method.matchmakerMatchResource;

import com.omgservers.schema.module.matchmaker.matchmakerMatchResource.UpdateMatchmakerMatchResourceStatusRequest;
import com.omgservers.schema.module.matchmaker.matchmakerMatchResource.UpdateMatchmakerMatchResourceStatusResponse;
import io.smallrye.mutiny.Uni;

public interface UpdateMatchmakerMatchResourceStatusMethod {
    Uni<UpdateMatchmakerMatchResourceStatusResponse> execute(UpdateMatchmakerMatchResourceStatusRequest request);
}
