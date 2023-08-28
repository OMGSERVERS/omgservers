package com.omgservers.module.gateway.impl.service.gatewayWebService;

import com.omgservers.dto.gateway.AssignPlayerRoutedRequest;
import com.omgservers.dto.gateway.RespondMessageRoutedRequest;
import io.smallrye.mutiny.Uni;

public interface GatewayWebService {

    Uni<Void> respondMessage(RespondMessageRoutedRequest request);

    Uni<Void> assignPlayer(AssignPlayerRoutedRequest request);
}
