package com.omgservers.module.internal.impl.service.eventShardedService.impl.method.fireEvent;

import com.omgservers.dto.internal.FireEventShardedRequest;
import com.omgservers.dto.internal.FireEventShardedResponse;
import io.smallrye.mutiny.Uni;

public interface FireEventMethod {
    Uni<FireEventShardedResponse> fireEvent(FireEventShardedRequest request);
}
