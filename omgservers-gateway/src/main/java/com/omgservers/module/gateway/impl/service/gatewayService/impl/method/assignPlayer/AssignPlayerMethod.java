package com.omgservers.module.gateway.impl.service.gatewayService.impl.method.assignPlayer;

import com.omgservers.dto.gateway.AssignPlayerRequest;
import io.smallrye.mutiny.Uni;

public interface AssignPlayerMethod {
    Uni<Void> assignPlayer(AssignPlayerRequest request);
}
