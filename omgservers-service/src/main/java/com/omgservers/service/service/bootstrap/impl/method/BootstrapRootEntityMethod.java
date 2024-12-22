package com.omgservers.service.service.bootstrap.impl.method;

import com.omgservers.service.service.bootstrap.dto.BootstrapRootEntityRequest;
import com.omgservers.service.service.bootstrap.dto.BootstrapRootEntityResponse;
import io.smallrye.mutiny.Uni;

public interface BootstrapRootEntityMethod {
    Uni<BootstrapRootEntityResponse> execute(BootstrapRootEntityRequest request);
}
