package com.omgservers.application.module.internalModule.impl.service.consumerHelpService;

import io.smallrye.mutiny.Uni;

public interface ConsumerHelpService {

    Uni<Void> startConsumers();
}
