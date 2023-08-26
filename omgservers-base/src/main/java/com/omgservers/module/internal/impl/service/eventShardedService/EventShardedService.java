package com.omgservers.module.internal.impl.service.eventShardedService;

import com.omgservers.dto.internalModule.FireEventShardRequest;
import com.omgservers.dto.internalModule.FireEventShardedResponse;
import io.smallrye.mutiny.Uni;

public interface EventShardedService {

    Uni<FireEventShardedResponse> fireEvent(FireEventShardRequest request);
}
