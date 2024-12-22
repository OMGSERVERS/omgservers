package com.omgservers.service.service.bootstrap.impl.method;

import com.omgservers.service.service.bootstrap.dto.BootstrapDefaultUserRequest;
import com.omgservers.service.service.bootstrap.dto.BootstrapDefaultUserResponse;
import io.smallrye.mutiny.Uni;

public interface BootstrapDefaultUserMethod {
    Uni<BootstrapDefaultUserResponse> execute(BootstrapDefaultUserRequest request);
}
