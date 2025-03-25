package com.omgservers.service.service.cache.impl.service.inmemory.method;

import com.omgservers.service.service.cache.dto.GetClientLastActivityResponse;
import com.omgservers.service.service.cache.dto.GetClientLastActivityRequest;
import io.smallrye.mutiny.Uni;

public interface GetClientLastActivityMethod {
    Uni<GetClientLastActivityResponse> execute(GetClientLastActivityRequest request);
}
