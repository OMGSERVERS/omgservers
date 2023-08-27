package com.omgservers.module.internal.impl.service.eventShardedService;

import com.omgservers.dto.internal.FireEventShardedRequest;
import com.omgservers.dto.internal.FireEventShardedResponse;
import io.smallrye.mutiny.Uni;

public interface EventShardedService {

    Uni<FireEventShardedResponse> fireEvent(FireEventShardedRequest request);
}
