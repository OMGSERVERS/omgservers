package com.omgservers.service.service.bootstrap.impl;

import com.omgservers.service.service.bootstrap.BootstrapService;
import com.omgservers.service.service.bootstrap.dto.BootstrapDefaultPoolRequest;
import com.omgservers.service.service.bootstrap.dto.BootstrapDefaultPoolResponse;
import com.omgservers.service.service.bootstrap.dto.BootstrapDefaultUserRequest;
import com.omgservers.service.service.bootstrap.dto.BootstrapDefaultUserResponse;
import com.omgservers.service.service.bootstrap.dto.BootstrapRootEntityRequest;
import com.omgservers.service.service.bootstrap.dto.BootstrapRootEntityResponse;
import com.omgservers.service.service.bootstrap.impl.method.BootstrapDefaultPoolMethod;
import com.omgservers.service.service.bootstrap.impl.method.BootstrapDefaultUserMethod;
import com.omgservers.service.service.bootstrap.impl.method.BootstrapRootEntityMethod;
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
    final BootstrapRootEntityMethod bootstrapRootEntityMethod;

    @Override
    public Uni<BootstrapRootEntityResponse> execute(@Valid final BootstrapRootEntityRequest request) {
        return bootstrapRootEntityMethod.execute(request);
    }

    @Override
    public Uni<BootstrapDefaultUserResponse> execute(@Valid final BootstrapDefaultUserRequest request) {
        return bootstrapDefaultUserMethod.execute(request);
    }

    @Override
    public Uni<BootstrapDefaultPoolResponse> execute(@Valid final BootstrapDefaultPoolRequest request) {
        return bootstrapDefaultPoolMethod.execute(request);
    }
}
