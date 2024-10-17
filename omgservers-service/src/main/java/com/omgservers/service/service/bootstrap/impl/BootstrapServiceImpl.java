package com.omgservers.service.service.bootstrap.impl;

import com.omgservers.service.service.bootstrap.BootstrapService;
import com.omgservers.service.service.bootstrap.impl.method.bootstrapAdminUser.BootstrapAdminUserMethod;
import com.omgservers.service.service.bootstrap.impl.method.bootstrapBuilderUser.BootstrapBuilderUserMethod;
import com.omgservers.service.service.bootstrap.impl.method.bootstrapDefaultPool.BootstrapDefaultPoolMethod;
import com.omgservers.service.service.bootstrap.impl.method.bootstrapDockerHost.BootstrapDockerHostMethod;
import com.omgservers.service.service.bootstrap.impl.method.bootstrapRegistryUser.BootstrapRegistryUserMethod;
import com.omgservers.service.service.bootstrap.impl.method.bootstrapServerIndex.BootstrapServerIndexMethod;
import com.omgservers.service.service.bootstrap.impl.method.bootstrapServiceRoot.BootstrapServiceRootMethod;
import com.omgservers.service.service.bootstrap.impl.method.bootstrapServiceUser.BootstrapServiceUserMethod;
import com.omgservers.service.service.bootstrap.impl.method.bootstrapSupportUser.BootstrapSupportUserMethod;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor(access = AccessLevel.PACKAGE)
class BootstrapServiceImpl implements BootstrapService {

    final BootstrapRegistryUserMethod bootstrapRegistryUserMethod;
    final BootstrapBuilderUserMethod bootstrapBuilderUserMethod;
    final BootstrapServiceUserMethod bootstrapServiceUserMethod;
    final BootstrapServerIndexMethod bootstrapServerIndexMethod;
    final BootstrapDefaultPoolMethod bootstrapDefaultPoolMethod;
    final BootstrapSupportUserMethod bootstrapSupportUserMethod;
    final BootstrapServiceRootMethod bootstrapServiceRootMethod;
    final BootstrapDockerHostMethod bootstrapDockerHostMethod;
    final BootstrapAdminUserMethod bootstrapAdminUserMethod;

    @Override
    public Uni<Void> bootstrapServerIndex() {
        return bootstrapServerIndexMethod.bootstrapServerIndex();
    }

    @Override
    public Uni<Void> bootstrapServiceRoot() {
        return bootstrapServiceRootMethod.bootstrapServiceRoot();
    }

    @Override
    public Uni<Void> bootstrapAdminUser() {
        return bootstrapAdminUserMethod.bootstrapAdminUser();
    }

    @Override
    public Uni<Void> bootstrapSupportUser() {
        return bootstrapSupportUserMethod.bootstrapSupportUser();
    }

    @Override
    public Uni<Void> bootstrapRegistryUser() {
        return bootstrapRegistryUserMethod.bootstrapRegistryUser();
    }

    @Override
    public Uni<Void> bootstrapBuilderUser() {
        return bootstrapBuilderUserMethod.bootstrapBuilderUser();
    }

    @Override
    public Uni<Void> bootstrapServiceUser() {
        return bootstrapServiceUserMethod.bootstrapServiceUser();
    }

    @Override
    public Uni<Void> bootstrapDefaultPool() {
        return bootstrapDefaultPoolMethod.execute();
    }

    @Override
    public Uni<Void> bootstrapDockerHost() {
        return bootstrapDockerHostMethod.execute();
    }
}
