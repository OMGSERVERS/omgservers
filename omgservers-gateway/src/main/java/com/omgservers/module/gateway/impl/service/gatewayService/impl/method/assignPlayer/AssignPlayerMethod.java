package com.omgservers.module.gateway.impl.service.gatewayService.impl.method.assignPlayer;

import com.omgservers.dto.gateway.AssignPlayerRoutedRequest;
import io.smallrye.mutiny.Uni;

public interface AssignPlayerMethod {
    Uni<Void> assignPlayer(AssignPlayerRoutedRequest request);
}
