package com.omgservers.service.server.cache.impl.service.inmemory.method;

import com.omgservers.service.server.cache.dto.GetClientsLastActivitiesRequest;
import com.omgservers.service.server.cache.dto.GetClientsLastActivitiesResponse;
import io.smallrye.mutiny.Uni;

public interface GetClientsLastActivitiesMethod {
    Uni<GetClientsLastActivitiesResponse> execute(GetClientsLastActivitiesRequest request);
}
