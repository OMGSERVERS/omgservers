package com.omgservers.service.service.cache.impl.service.inmemory.method;

import com.omgservers.service.service.cache.dto.GetClientsLastActivitiesRequest;
import com.omgservers.service.service.cache.dto.GetClientsLastActivitiesResponse;
import io.smallrye.mutiny.Uni;

public interface GetClientsLastActivitiesMethod {
    Uni<GetClientsLastActivitiesResponse> execute(GetClientsLastActivitiesRequest request);
}
