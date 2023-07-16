package com.omgservers.application.module.bootstrapModule.impl.service.bootstrapHelpService.impl.method.bootstrapConsumersMethod;

import com.omgservers.application.module.internalModule.InternalModule;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
class BootstrapConsumersMethodImpl implements BootstrapConsumersMethod {

    final InternalModule internalModule;

    @Override
    public Uni<Void> bootstrapConsumersMethod() {
        return Uni.createFrom().voidItem()
                .call(voidItem -> internalModule.getConsumerHelpService().startConsumers());
    }
}
