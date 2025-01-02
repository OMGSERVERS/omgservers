package com.omgservers.service.entrypoint.dispatcher.impl.service.dispatcherService.impl.method;

import com.omgservers.schema.entrypoint.dispatcher.CalculateShardDispatcherRequest;
import com.omgservers.schema.entrypoint.dispatcher.CalculateShardDispatcherResponse;
import io.smallrye.mutiny.Uni;

public interface CalculateShardMethod {
    Uni<CalculateShardDispatcherResponse> execute(CalculateShardDispatcherRequest request);
}
