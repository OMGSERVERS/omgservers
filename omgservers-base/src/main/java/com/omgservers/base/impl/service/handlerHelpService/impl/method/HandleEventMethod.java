package com.omgservers.base.impl.service.handlerHelpService.impl.method;

import com.omgservers.base.impl.service.handlerHelpService.request.HandleEventHelpRequest;
import com.omgservers.base.impl.service.handlerHelpService.response.HandleEventHelpResponse;
import io.smallrye.mutiny.Uni;

public interface HandleEventMethod {
    Uni<HandleEventHelpResponse> handleEvent(HandleEventHelpRequest request);
}
