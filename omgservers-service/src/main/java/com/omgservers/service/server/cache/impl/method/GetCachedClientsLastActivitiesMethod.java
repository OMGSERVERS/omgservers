package com.omgservers.service.server.cache.impl.method;

import com.omgservers.service.server.cache.dto.GetCachedClientsLastActivitiesRequest;
import com.omgservers.service.server.cache.dto.GetCachedClientsLastActivitiesResponse;
import io.smallrye.mutiny.Uni;

public interface GetCachedClientsLastActivitiesMethod {
    Uni<GetCachedClientsLastActivitiesResponse> execute(GetCachedClientsLastActivitiesRequest request);
}
