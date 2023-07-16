package com.omgservers.application.module.internalModule.impl.service.producerHelpService.impl;

import com.omgservers.application.module.internalModule.impl.service.producerHelpService.ProducerHelpService;
import com.omgservers.application.module.internalModule.impl.service.producerHelpService.impl.method.produceEventMethod.ProducerEventMethod;
import com.omgservers.application.module.internalModule.impl.service.producerHelpService.request.ProducerEventHelpRequest;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor(access = AccessLevel.PACKAGE)
public class ProducerHelpServiceImpl implements ProducerHelpService {

    final ProducerEventMethod producerEventMethod;

    @Override
    public Uni<Void> produceEvent(ProducerEventHelpRequest request) {
        return producerEventMethod.produceEvent(request);
    }
}
