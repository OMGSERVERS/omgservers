package com.omgservers.service.server.cache.impl.service.inmemory.method;

import com.omgservers.service.server.cache.dto.SetClientLastActivityRequest;
import com.omgservers.service.server.cache.dto.SetClientLastActivityResponse;
import io.smallrye.mutiny.Uni;

public interface SetClientLastActivityMethod {
    Uni<SetClientLastActivityResponse> execute(SetClientLastActivityRequest request);
}
