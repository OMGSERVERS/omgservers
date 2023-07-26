package com.omgservers.application.module.internalModule.impl.service.eventHelpService.impl.method.fireEventMethod;

import com.omgservers.application.module.internalModule.impl.service.eventHelpService.request.FireEventHelpRequest;
import io.smallrye.mutiny.Uni;

public interface FireEventMethod {
    Uni<Void> fireEvent(FireEventHelpRequest request);
}
