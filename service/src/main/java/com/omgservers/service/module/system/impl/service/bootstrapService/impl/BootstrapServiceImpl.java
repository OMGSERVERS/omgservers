package com.omgservers.service.module.system.impl.service.bootstrapService.impl;

import com.omgservers.service.module.system.impl.service.bootstrapService.BootstrapService;
import com.omgservers.service.module.system.impl.service.bootstrapService.impl.method.bootstrapAdminUser.BootstrapAdminUserMethod;
import com.omgservers.service.module.system.impl.service.bootstrapService.impl.method.bootstrapBuilderUser.BootstrapBuilderUserMethod;
import com.omgservers.service.module.system.impl.service.bootstrapService.impl.method.bootstrapDatabaseSchema.BootstrapDatabaseSchemaMethod;
import com.omgservers.service.module.system.impl.service.bootstrapService.impl.method.bootstrapDefaultPool.BootstrapDefaultPoolMethod;
import com.omgservers.service.module.system.impl.service.bootstrapService.impl.method.bootstrapDockerHost.BootstrapDockerHostMethod;
import com.omgservers.service.module.system.impl.service.bootstrapService.impl.method.bootstrapRegistryUser.BootstrapRegistryUserMethod;
import com.omgservers.service.module.system.impl.service.bootstrapService.impl.method.bootstrapRelayJob.BootstrapRelayJobMethod;
import com.omgservers.service.module.system.impl.service.bootstrapService.impl.method.bootstrapRouterUser.BootstrapRouterUserMethod;
import com.omgservers.service.module.system.impl.service.bootstrapService.impl.method.bootstrapSchedulerJob.BootstrapSchedulerJobMethod;
import com.omgservers.service.module.system.impl.service.bootstrapService.impl.method.bootstrapServerIndex.BootstrapServerIndexMethod;
import com.omgservers.service.module.system.impl.service.bootstrapService.impl.method.bootstrapServiceRoot.BootstrapServiceRootMethod;
import com.omgservers.service.module.system.impl.service.bootstrapService.impl.method.bootstrapSupportUser.BootstrapSupportUserMethod;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor(access = AccessLevel.PACKAGE)
class BootstrapServiceImpl implements BootstrapService {

    final BootstrapDatabaseSchemaMethod bootstrapDatabaseSchemaMethod;
    final BootstrapSchedulerJobMethod bootstrapSchedulerJobMethod;
    final BootstrapRegistryUserMethod bootstrapRegistryUserMethod;
    final BootstrapBuilderUserMethod bootstrapBuilderUserMethod;
    final BootstrapServerIndexMethod bootstrapServerIndexMethod;
    final BootstrapDefaultPoolMethod bootstrapDefaultPoolMethod;
    final BootstrapSupportUserMethod bootstrapSupportUserMethod;
    final BootstrapServiceRootMethod bootstrapServiceRootMethod;
    final BootstrapDockerHostMethod bootstrapDockerHostMethod;
    final BootstrapRouterUserMethod bootstrapRouterUserMethod;
    final BootstrapAdminUserMethod bootstrapAdminUserMethod;
    final BootstrapRelayJobMethod bootstrapRelayJobMethod;

    @Override
    public Uni<Void> bootstrapDatabaseSchema() {
        return bootstrapDatabaseSchemaMethod.bootstrapDatabaseSchema();
    }

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
    public Uni<Void> bootstrapRouterUser() {
        return bootstrapRouterUserMethod.bootstrapRouterUser();
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
    public Uni<Void> bootstrapDefaultPool() {
        return bootstrapDefaultPoolMethod.bootstrapDefaultPool();
    }

    @Override
    public Uni<Void> bootstrapDockerHost() {
        return bootstrapDockerHostMethod.bootstrapDockerHost();
    }

    @Override
    public Uni<Void> bootstrapRelayJob() {
        return bootstrapRelayJobMethod.bootstrapRelayJob();
    }

    @Override
    public Uni<Void> bootstrapSchedulerJob() {
        return bootstrapSchedulerJobMethod.bootstrapSchedulerJob();
    }
}
