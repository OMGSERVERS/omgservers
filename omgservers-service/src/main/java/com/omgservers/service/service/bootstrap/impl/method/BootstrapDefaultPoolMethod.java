package com.omgservers.service.service.bootstrap.impl.method;

import com.omgservers.service.service.bootstrap.dto.BootstrapDefaultPoolRequest;
import com.omgservers.service.service.bootstrap.dto.BootstrapDefaultPoolResponse;
import io.smallrye.mutiny.Uni;

public interface BootstrapDefaultPoolMethod {
    Uni<BootstrapDefaultPoolResponse> execute(BootstrapDefaultPoolRequest request);
}
