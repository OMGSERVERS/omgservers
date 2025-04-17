package com.omgservers.service.server.cache.impl.method;

import com.omgservers.service.server.cache.dto.GetCachedRuntimeLastActivityRequest;
import com.omgservers.service.server.cache.dto.GetCachedRuntimeLastActivityResponse;
import io.smallrye.mutiny.Uni;

public interface GetCachedRuntimeLastActivityMethod {
    Uni<GetCachedRuntimeLastActivityResponse> execute(GetCachedRuntimeLastActivityRequest request);
}
