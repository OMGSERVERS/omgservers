package com.omgservers.service.server.cache.impl.service.inmemory.method;

import com.omgservers.service.server.cache.dto.GetRuntimeLastActivityRequest;
import com.omgservers.service.server.cache.dto.GetRuntimeLastActivityResponse;
import io.smallrye.mutiny.Uni;

public interface GetRuntimeLastActivityMethod {
    Uni<GetRuntimeLastActivityResponse> execute(GetRuntimeLastActivityRequest request);
}
