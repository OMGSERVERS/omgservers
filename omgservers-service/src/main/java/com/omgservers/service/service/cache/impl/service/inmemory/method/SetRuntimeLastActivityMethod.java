package com.omgservers.service.service.cache.impl.service.inmemory.method;

import com.omgservers.service.service.cache.dto.SetRuntimeLastActivityRequest;
import com.omgservers.service.service.cache.dto.SetRuntimeLastActivityResponse;
import io.smallrye.mutiny.Uni;

public interface SetRuntimeLastActivityMethod {
    Uni<SetRuntimeLastActivityResponse> execute(SetRuntimeLastActivityRequest request);
}
