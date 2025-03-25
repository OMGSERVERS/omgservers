package com.omgservers.service.service.cache.impl.service.inmemory.method;

import com.omgservers.service.service.cache.dto.GetRuntimeLastActivityRequest;
import com.omgservers.service.service.cache.dto.GetRuntimeLastActivityResponse;
import io.smallrye.mutiny.Uni;

public interface GetRuntimeLastActivityMethod {
    Uni<GetRuntimeLastActivityResponse> execute(GetRuntimeLastActivityRequest request);
}
