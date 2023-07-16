package com.omgservers.application.module.bootstrapModule.impl.service.bootstrapHelpService.impl.method.bootstrapEventDispatcherMethod;

import com.omgservers.application.module.internalModule.InternalModule;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
class BootstrapEventDispatcherMethodImpl implements BootstrapEventDispatcherMethod {

    final InternalModule internalModule;

    @Override
    public Uni<Void> bootstrapEventDispatcherMethod() {
        return Uni.createFrom().voidItem()
                .call(voidItem -> internalModule.getEventHelpService().startEventDispatcher());
    }
}
