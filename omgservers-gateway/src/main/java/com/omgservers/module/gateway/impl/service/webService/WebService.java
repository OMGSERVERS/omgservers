package com.omgservers.module.gateway.impl.service.webService;

import com.omgservers.dto.gateway.AssignClientRequest;
import com.omgservers.dto.gateway.AssignRuntimeRequest;
import com.omgservers.dto.gateway.RespondMessageRequest;
import com.omgservers.dto.gateway.RespondMessageResponse;
import io.smallrye.mutiny.Uni;

public interface WebService {

    Uni<RespondMessageResponse> respondMessage(RespondMessageRequest request);

    Uni<Void> assignClient(AssignClientRequest request);

    Uni<Void> assignRuntime(AssignRuntimeRequest request);
}
