package com.omgservers.application.module.internalModule.impl.service.producerHelpService.impl;

import com.omgservers.application.module.internalModule.impl.service.producerHelpService.ProducerHelpService;
import com.omgservers.application.module.internalModule.impl.service.producerHelpService.impl.method.produceEventMethod.ProduceEventMethod;
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

    final ProduceEventMethod produceEventMethod;

    @Override
    public Uni<Void> produceEventAsync(ProducerEventHelpRequest request) {
        return produceEventMethod.produceEventAsync(request);
    }

    @Override
    public void produceEventSync(ProducerEventHelpRequest request) {
        produceEventMethod.produceEventSync(request);
    }
}
