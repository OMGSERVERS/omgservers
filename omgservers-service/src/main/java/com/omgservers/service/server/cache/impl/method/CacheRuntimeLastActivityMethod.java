package com.omgservers.service.server.cache.impl.method;

import com.omgservers.service.server.cache.dto.CacheRuntimeLastActivityRequest;
import com.omgservers.service.server.cache.dto.CacheRuntimeLastActivityResponse;
import io.smallrye.mutiny.Uni;

public interface CacheRuntimeLastActivityMethod {
    Uni<CacheRuntimeLastActivityResponse> execute(CacheRuntimeLastActivityRequest request);
}
