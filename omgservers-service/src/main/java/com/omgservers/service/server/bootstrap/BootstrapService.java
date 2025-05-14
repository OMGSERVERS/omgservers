package com.omgservers.service.server.bootstrap;

import com.omgservers.service.server.bootstrap.dto.BootstrapDefaultPoolRequest;
import com.omgservers.service.server.bootstrap.dto.BootstrapDefaultPoolResponse;
import com.omgservers.service.server.bootstrap.dto.BootstrapDefaultUserRequest;
import com.omgservers.service.server.bootstrap.dto.BootstrapDefaultUserResponse;
import io.smallrye.mutiny.Uni;
import jakarta.validation.Valid;

public interface BootstrapService {

    Uni<BootstrapDefaultUserResponse> execute(@Valid BootstrapDefaultUserRequest request);

    Uni<BootstrapDefaultPoolResponse> execute(@Valid BootstrapDefaultPoolRequest request);
}
