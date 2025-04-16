package com.omgservers.service.server.cache.impl.method;

import com.omgservers.service.server.cache.dto.SetRuntimeLastActivityRequest;
import com.omgservers.service.server.cache.dto.SetRuntimeLastActivityResponse;
import io.smallrye.mutiny.Uni;

public interface SetRuntimeLastActivityMethod {
    Uni<SetRuntimeLastActivityResponse> execute(SetRuntimeLastActivityRequest request);
}
