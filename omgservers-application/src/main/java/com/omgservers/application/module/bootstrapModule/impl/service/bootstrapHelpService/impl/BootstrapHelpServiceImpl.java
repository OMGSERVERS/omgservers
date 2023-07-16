package com.omgservers.application.module.bootstrapModule.impl.service.bootstrapHelpService.impl;

import com.omgservers.application.module.bootstrapModule.impl.service.bootstrapHelpService.BootstrapHelpService;
import com.omgservers.application.module.bootstrapModule.impl.service.bootstrapHelpService.impl.method.bootstrapDatabaseSchemaMethod.BootstrapDatabaseSchemaMethod;
import com.omgservers.application.module.bootstrapModule.impl.service.bootstrapHelpService.impl.method.bootstrapConsumersMethod.BootstrapConsumersMethod;
import com.omgservers.application.module.bootstrapModule.impl.service.bootstrapHelpService.impl.method.bootstrapEventDispatcherMethod.BootstrapEventDispatcherMethod;
import com.omgservers.application.module.bootstrapModule.impl.service.bootstrapHelpService.impl.method.bootstrapStandaloneConfigurationMethod.BootstrapStandaloneConfigurationMethod;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor(access = AccessLevel.PACKAGE)
class BootstrapHelpServiceImpl implements BootstrapHelpService {

    final BootstrapStandaloneConfigurationMethod bootstrapStandaloneConfigurationMethod;
    final BootstrapEventDispatcherMethod bootstrapEventDispatcherMethod;
    final BootstrapDatabaseSchemaMethod bootstrapDatabaseSchemaMethod;
    final BootstrapConsumersMethod bootstrapConsumersMethod;

    @Override
    public Uni<Void> bootstrap() {
        return bootstrapDatabaseSchemaMethod.bootstrapDatabaseSchema()
                .flatMap(voidItem -> bootstrapStandaloneConfigurationMethod.bootstrapStandaloneConfiguration())
                .flatMap(voidItem -> bootstrapConsumersMethod.bootstrapConsumersMethod())
                .flatMap(voidItem -> bootstrapEventDispatcherMethod.bootstrapEventDispatcherMethod());
    }
}
