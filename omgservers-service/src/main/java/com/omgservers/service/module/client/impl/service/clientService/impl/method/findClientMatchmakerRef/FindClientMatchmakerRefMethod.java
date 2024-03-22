package com.omgservers.service.module.client.impl.service.clientService.impl.method.findClientMatchmakerRef;

import com.omgservers.model.dto.client.FindClientMatchmakerRefRequest;
import com.omgservers.model.dto.client.FindClientMatchmakerRefResponse;
import io.smallrye.mutiny.Uni;

public interface FindClientMatchmakerRefMethod {
    Uni<FindClientMatchmakerRefResponse> findClientMatchmakerRef(FindClientMatchmakerRefRequest request);
}
