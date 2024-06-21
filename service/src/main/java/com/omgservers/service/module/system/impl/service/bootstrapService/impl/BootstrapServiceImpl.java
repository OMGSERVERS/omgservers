package com.omgservers.service.module.system.impl.service.bootstrapService.impl;

import com.omgservers.service.module.system.impl.service.bootstrapService.BootstrapService;
import com.omgservers.service.module.system.impl.service.bootstrapService.impl.method.bootstrapAdminUser.BootstrapAdminUserMethod;
import com.omgservers.service.module.system.impl.service.bootstrapService.impl.method.bootstrapDatabaseSchema.BootstrapDatabaseSchemaMethod;
import com.omgservers.service.module.system.impl.service.bootstrapService.impl.method.bootstrapDefaultPool.BootstrapDefaultPoolMethod;
import com.omgservers.service.module.system.impl.service.bootstrapService.impl.method.bootstrapDockerHost.BootstrapDockerHostMethod;
import com.omgservers.service.module.system.impl.service.bootstrapService.impl.method.bootstrapRelayJob.BootstrapRelayJobMethod;
import com.omgservers.service.module.system.impl.service.bootstrapService.impl.method.bootstrapSchedulerJob.BootstrapSchedulerJobMethod;
import com.omgservers.service.module.system.impl.service.bootstrapService.impl.method.bootstrapServiceIndex.BootstrapServiceIndexMethod;
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
    final BootstrapServiceIndexMethod bootstrapServiceIndexMethod;
    final BootstrapSchedulerJobMethod bootstrapSchedulerJobMethod;
    final BootstrapDefaultPoolMethod bootstrapDefaultPoolMethod;
    final BootstrapSupportUserMethod bootstrapSupportUserMethod;
    final BootstrapServiceRootMethod bootstrapServiceRootMethod;
    final BootstrapDockerHostMethod bootstrapDockerHostMethod;
    final BootstrapAdminUserMethod bootstrapAdminUserMethod;
    final BootstrapRelayJobMethod bootstrapRelayJobMethod;

    @Override
    public Uni<Void> bootstrapDatabaseSchema() {
        return bootstrapDatabaseSchemaMethod.bootstrapDatabaseSchema();
    }

    @Override
    public Uni<Void> bootstrapServiceIndex() {
        return bootstrapServiceIndexMethod.bootstrapServiceIndex();
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
