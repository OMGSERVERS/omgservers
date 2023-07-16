package com.omgservers.application.module.internalModule.impl.service.producerHelpService;

import com.omgservers.application.module.internalModule.impl.service.producerHelpService.request.ProducerEventHelpRequest;
import io.smallrye.mutiny.Uni;

public interface ProducerHelpService {

    Uni<Void> produceEvent(ProducerEventHelpRequest request);
}
