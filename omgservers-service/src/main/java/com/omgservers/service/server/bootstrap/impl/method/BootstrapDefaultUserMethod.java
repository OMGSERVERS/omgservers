package com.omgservers.service.server.bootstrap.impl.method;

import com.omgservers.service.server.bootstrap.dto.BootstrapDefaultUserRequest;
import com.omgservers.service.server.bootstrap.dto.BootstrapDefaultUserResponse;
import io.smallrye.mutiny.Uni;

public interface BootstrapDefaultUserMethod {
    Uni<BootstrapDefaultUserResponse> execute(BootstrapDefaultUserRequest request);
}
