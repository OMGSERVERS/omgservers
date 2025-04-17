package com.omgservers.service.server.cache.impl.method;

import com.omgservers.service.server.cache.dto.GetCachedIndexConfigRequest;
import com.omgservers.service.server.cache.dto.GetCachedIndexConfigResponse;
import io.smallrye.mutiny.Uni;

public interface GetCachedIndexConfigMethod {
    Uni<GetCachedIndexConfigResponse> execute(GetCachedIndexConfigRequest request);
}
