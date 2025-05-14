package com.omgservers.service.server.bootstrap.impl;

import com.omgservers.service.server.bootstrap.BootstrapService;
import com.omgservers.service.server.bootstrap.dto.BootstrapDefaultPoolRequest;
import com.omgservers.service.server.bootstrap.dto.BootstrapDefaultPoolResponse;
import com.omgservers.service.server.bootstrap.dto.BootstrapDefaultUserRequest;
import com.omgservers.service.server.bootstrap.dto.BootstrapDefaultUserResponse;
import com.omgservers.service.server.bootstrap.impl.method.BootstrapDefaultPoolMethod;
import com.omgservers.service.server.bootstrap.impl.method.BootstrapDefaultUserMethod;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor(access = AccessLevel.PACKAGE)
class BootstrapServiceImpl implements BootstrapService {

    final BootstrapDefaultPoolMethod bootstrapDefaultPoolMethod;
    final BootstrapDefaultUserMethod bootstrapDefaultUserMethod;

    @Override
    public Uni<BootstrapDefaultUserResponse> execute(@Valid final BootstrapDefaultUserRequest request) {
        return bootstrapDefaultUserMethod.execute(request);
    }

    @Override
    public Uni<BootstrapDefaultPoolResponse> execute(@Valid final BootstrapDefaultPoolRequest request) {
        return bootstrapDefaultPoolMethod.execute(request);
    }
}
