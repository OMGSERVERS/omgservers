package com.omgservers.application.module.gatewayModule.impl.service.gatewayWebService;

import com.omgservers.dto.gatewayModule.AssignPlayerInternalRequest;
import com.omgservers.dto.gatewayModule.RespondMessageInternalRequest;
import io.smallrye.mutiny.Uni;

public interface GatewayWebService {

    Uni<Void> respondMessage(RespondMessageInternalRequest request);

    Uni<Void> assignPlayer(AssignPlayerInternalRequest request);
}
