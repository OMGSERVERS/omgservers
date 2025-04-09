package com.omgservers.service.server.bootstrap.impl.method;

import com.omgservers.service.server.bootstrap.dto.BootstrapDefaultPoolRequest;
import com.omgservers.service.server.bootstrap.dto.BootstrapDefaultPoolResponse;
import io.smallrye.mutiny.Uni;

public interface BootstrapDefaultPoolMethod {
    Uni<BootstrapDefaultPoolResponse> execute(BootstrapDefaultPoolRequest request);
}
