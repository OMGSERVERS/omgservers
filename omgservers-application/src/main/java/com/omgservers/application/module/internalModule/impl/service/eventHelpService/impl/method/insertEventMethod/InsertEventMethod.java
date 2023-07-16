package com.omgservers.application.module.internalModule.impl.service.eventHelpService.impl.method.insertEventMethod;

import com.omgservers.application.module.internalModule.impl.service.eventHelpService.request.InsertEventHelpRequest;
import io.smallrye.mutiny.Uni;

public interface InsertEventMethod {
    Uni<Void> insertEvent(InsertEventHelpRequest request);
}
