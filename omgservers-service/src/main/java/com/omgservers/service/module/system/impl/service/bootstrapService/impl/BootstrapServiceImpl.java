package com.omgservers.service.module.system.impl.service.bootstrapService.impl;

import com.omgservers.service.module.system.impl.service.bootstrapService.BootstrapService;
import com.omgservers.service.module.system.impl.service.bootstrapService.impl.method.bootstrapAdmin.BootstrapAdminMethod;
import com.omgservers.service.module.system.impl.service.bootstrapService.impl.method.bootstrapIndex.BootstrapIndexMethod;
import com.omgservers.service.module.system.impl.service.bootstrapService.impl.method.bootstrapRelay.BootstrapRelayMethod;
import com.omgservers.service.module.system.impl.service.bootstrapService.impl.method.bootstrapRoot.BootstrapRootMethod;
import com.omgservers.service.module.system.impl.service.bootstrapService.impl.method.bootstrapSchema.BootstrapSchemaMethod;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor(access = AccessLevel.PACKAGE)
class BootstrapServiceImpl implements BootstrapService {

    final BootstrapSchemaMethod bootstrapSchemaMethod;
    final BootstrapAdminMethod bootstrapAdminMethod;
    final BootstrapIndexMethod bootstrapIndexMethod;
    final BootstrapRelayMethod bootstrapRelayMethod;
    final BootstrapRootMethod bootstrapRootMethod;

    @Override
    public Uni<Void> bootstrapSchema() {
        return bootstrapSchemaMethod.bootstrapSchema();
    }

    @Override
    public Uni<Void> bootstrapIndex() {
        return bootstrapIndexMethod.bootstrapIndex();
    }

    @Override
    public Uni<Void> bootstrapAdmin() {
        return bootstrapAdminMethod.bootstrapAdmin();
    }

    @Override
    public Uni<Void> bootstrapRoot() {
        return bootstrapRootMethod.bootstrapRoot();
    }

    @Override
    public Uni<Void> bootstrapRelay() {
        return bootstrapRelayMethod.bootstrapRelay();
    }
}
