package com.omgservers.service.module.client.impl.service.clientService.impl.method.getClientMatchmakerRef;

import com.omgservers.model.dto.client.GetClientMatchmakerRefRequest;
import com.omgservers.model.dto.client.GetClientMatchmakerRefResponse;
import io.smallrye.mutiny.Uni;

public interface GetClientMatchmakerRefMethod {
    Uni<GetClientMatchmakerRefResponse> getClientMatchmakerRef(GetClientMatchmakerRefRequest request);
}
