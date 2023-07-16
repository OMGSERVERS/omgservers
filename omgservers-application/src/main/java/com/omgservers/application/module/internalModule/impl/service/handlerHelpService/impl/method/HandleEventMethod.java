package com.omgservers.application.module.internalModule.impl.service.handlerHelpService.impl.method;

import com.omgservers.application.module.internalModule.impl.service.handlerHelpService.request.HandleEventHelpRequest;
import com.omgservers.application.module.internalModule.impl.service.handlerHelpService.response.HandleEventHelpResponse;
import io.smallrye.mutiny.Uni;

public interface HandleEventMethod {
    Uni<HandleEventHelpResponse> handleEvent(HandleEventHelpRequest request);
}
