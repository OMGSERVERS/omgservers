package com.omgservers.service.server.cache.impl.method;

import com.omgservers.service.server.cache.dto.CacheIndexConfigRequest;
import com.omgservers.service.server.cache.dto.CacheIndexConfigResponse;
import io.smallrye.mutiny.Uni;

public interface CacheIndexConfigMethod {
    Uni<CacheIndexConfigResponse> execute(CacheIndexConfigRequest request);
}
