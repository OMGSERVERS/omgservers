package com.omgservers.application.module.internalModule.impl.service.consumerHelpService.impl;

import com.omgservers.application.module.internalModule.impl.service.consumerHelpService.ConsumerHelpService;
import com.omgservers.application.module.internalModule.impl.service.consumerHelpService.impl.method.startConsumersMethod.StartConsumersMethod;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor(access = AccessLevel.PACKAGE)
public class ConsumerHelpServiceImpl implements ConsumerHelpService {

    final StartConsumersMethod startConsumersMethod;

    @Override
    public Uni<Void> startConsumers() {
        return startConsumersMethod.startConsumers();
    }
}
