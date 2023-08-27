package com.omgservers.module.gateway.impl.service.gatewayWebService;

import com.omgservers.dto.gateway.AssignPlayerRequest;
import com.omgservers.dto.gateway.RespondMessageRequest;
import io.smallrye.mutiny.Uni;

public interface GatewayWebService {

    Uni<Void> respondMessage(RespondMessageRequest request);

    Uni<Void> assignPlayer(AssignPlayerRequest request);
}
