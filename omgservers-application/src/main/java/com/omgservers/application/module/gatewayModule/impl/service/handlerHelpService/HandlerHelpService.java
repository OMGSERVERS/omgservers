package com.omgservers.application.module.gatewayModule.impl.service.handlerHelpService;

import com.omgservers.application.module.gatewayModule.impl.service.handlerHelpService.request.HandleMessageHelpRequest;
import io.smallrye.mutiny.Uni;

public interface HandlerHelpService {

    Uni<Void> handleMessage(HandleMessageHelpRequest request);
}
