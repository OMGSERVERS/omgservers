package com.omgservers.service.server.bootstrap;

import com.omgservers.service.server.bootstrap.dto.BootstrapDefaultPoolRequest;
import com.omgservers.service.server.bootstrap.dto.BootstrapDefaultPoolResponse;
import com.omgservers.service.server.bootstrap.dto.BootstrapDefaultUserRequest;
import com.omgservers.service.server.bootstrap.dto.BootstrapDefaultUserResponse;
import com.omgservers.service.server.bootstrap.dto.BootstrapRootEntityRequest;
import com.omgservers.service.server.bootstrap.dto.BootstrapRootEntityResponse;
import io.smallrye.mutiny.Uni;
import jakarta.validation.Valid;

public interface BootstrapService {

    Uni<BootstrapRootEntityResponse> execute(@Valid BootstrapRootEntityRequest request);

    Uni<BootstrapDefaultUserResponse> execute(@Valid BootstrapDefaultUserRequest request);

    Uni<BootstrapDefaultPoolResponse> execute(@Valid BootstrapDefaultPoolRequest request);
}
