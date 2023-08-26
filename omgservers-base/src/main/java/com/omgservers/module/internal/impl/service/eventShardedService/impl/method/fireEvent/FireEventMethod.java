package com.omgservers.module.internal.impl.service.eventShardedService.impl.method.fireEvent;

import com.omgservers.dto.internalModule.FireEventShardRequest;
import com.omgservers.dto.internalModule.FireEventShardedResponse;
import io.smallrye.mutiny.Uni;

public interface FireEventMethod {
    Uni<FireEventShardedResponse> fireEvent(FireEventShardRequest request);
}
