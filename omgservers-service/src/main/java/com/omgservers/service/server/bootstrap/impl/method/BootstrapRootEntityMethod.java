package com.omgservers.service.server.bootstrap.impl.method;

import com.omgservers.service.server.bootstrap.dto.BootstrapRootEntityRequest;
import com.omgservers.service.server.bootstrap.dto.BootstrapRootEntityResponse;
import io.smallrye.mutiny.Uni;

public interface BootstrapRootEntityMethod {
    Uni<BootstrapRootEntityResponse> execute(BootstrapRootEntityRequest request);
}
