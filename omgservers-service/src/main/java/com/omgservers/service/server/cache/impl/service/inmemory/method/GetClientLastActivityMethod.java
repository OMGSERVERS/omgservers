package com.omgservers.service.server.cache.impl.service.inmemory.method;

import com.omgservers.service.server.cache.dto.GetClientLastActivityResponse;
import com.omgservers.service.server.cache.dto.GetClientLastActivityRequest;
import io.smallrye.mutiny.Uni;

public interface GetClientLastActivityMethod {
    Uni<GetClientLastActivityResponse> execute(GetClientLastActivityRequest request);
}
