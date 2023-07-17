package com.omgservers.application.module.internalModule.impl.service.producerHelpService.impl.method.produceEventMethod;

import com.omgservers.application.module.internalModule.impl.service.producerHelpService.request.ProducerEventHelpRequest;
import io.smallrye.mutiny.Uni;

public interface ProduceEventMethod {
    Uni<Void> produceEventAsync(ProducerEventHelpRequest request);

    void produceEventSync(ProducerEventHelpRequest request);
}
