package com.omgservers.service.service.cache.impl.service.inmemory.method;

import com.omgservers.service.service.cache.dto.SetClientLastActivityRequest;
import com.omgservers.service.service.cache.dto.SetClientLastActivityResponse;
import io.smallrye.mutiny.Uni;

public interface SetClientLastActivityMethod {
    Uni<SetClientLastActivityResponse> execute(SetClientLastActivityRequest request);
}
