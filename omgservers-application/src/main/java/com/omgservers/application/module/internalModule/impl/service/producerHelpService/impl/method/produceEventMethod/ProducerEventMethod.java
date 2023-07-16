package com.omgservers.application.module.internalModule.impl.service.producerHelpService.impl.method.produceEventMethod;

import com.omgservers.application.module.internalModule.impl.service.producerHelpService.request.ProducerEventHelpRequest;
import io.smallrye.mutiny.Uni;

public interface ProducerEventMethod {
    Uni<Void> produceEvent(ProducerEventHelpRequest request);
}
