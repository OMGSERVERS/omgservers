package com.omgservers.application.module.internalModule.impl.service.eventHelpService.impl.method.fireEventMethod;

import com.omgservers.application.module.internalModule.impl.service.eventHelpService.request.FireEventHelpRequest;
import com.omgservers.application.module.internalModule.impl.service.eventHelpService.response.FireEventHelpResponse;
import io.smallrye.mutiny.Uni;

public interface FireEventMethod {
    Uni<FireEventHelpResponse> fireEvent(FireEventHelpRequest request);
}
