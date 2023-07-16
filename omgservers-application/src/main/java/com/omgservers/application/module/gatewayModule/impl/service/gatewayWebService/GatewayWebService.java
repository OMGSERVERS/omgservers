package com.omgservers.application.module.gatewayModule.impl.service.gatewayWebService;

import com.omgservers.application.module.gatewayModule.impl.service.gatewayInternalService.request.AssignPlayerInternalRequest;
import com.omgservers.application.module.gatewayModule.impl.service.gatewayInternalService.request.RespondMessageInternalRequest;
import io.smallrye.mutiny.Uni;

public interface GatewayWebService {

    Uni<Void> respondMessage(RespondMessageInternalRequest request);

    Uni<Void> assignPlayer(AssignPlayerInternalRequest request);
}
