package com.omgservers.dispatcher.service.service.impl.method;

import com.omgservers.dispatcher.service.service.dto.CalculateShardRequest;
import com.omgservers.dispatcher.service.service.dto.CalculateShardResponse;
import io.smallrye.mutiny.Uni;

public interface CalculateShardMethod {
    Uni<CalculateShardResponse> execute(CalculateShardRequest request);
}
