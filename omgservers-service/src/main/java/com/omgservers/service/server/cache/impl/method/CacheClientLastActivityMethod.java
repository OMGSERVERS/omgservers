package com.omgservers.service.server.cache.impl.method;

import com.omgservers.service.server.cache.dto.CacheClientLastActivityRequest;
import com.omgservers.service.server.cache.dto.CacheClientLastActivityResponse;
import io.smallrye.mutiny.Uni;

public interface CacheClientLastActivityMethod {
    Uni<CacheClientLastActivityResponse> execute(CacheClientLastActivityRequest request);
}
