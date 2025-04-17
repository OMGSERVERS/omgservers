package com.omgservers.service.server.cache.impl.method;

import com.omgservers.service.server.cache.dto.GetCachedClientLastActivityResponse;
import com.omgservers.service.server.cache.dto.GetCachedClientLastActivityRequest;
import io.smallrye.mutiny.Uni;

public interface GetCachedClientLastActivityMethod {
    Uni<GetCachedClientLastActivityResponse> execute(GetCachedClientLastActivityRequest request);
}
