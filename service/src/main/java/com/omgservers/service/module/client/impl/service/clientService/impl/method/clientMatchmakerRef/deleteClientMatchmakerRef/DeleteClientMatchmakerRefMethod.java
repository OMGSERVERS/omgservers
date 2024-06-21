package com.omgservers.service.module.client.impl.service.clientService.impl.method.clientMatchmakerRef.deleteClientMatchmakerRef;

import com.omgservers.model.dto.client.DeleteClientMatchmakerRefRequest;
import com.omgservers.model.dto.client.DeleteClientMatchmakerRefResponse;
import io.smallrye.mutiny.Uni;

public interface DeleteClientMatchmakerRefMethod {
    Uni<DeleteClientMatchmakerRefResponse> deleteClientMatchmakerRef(DeleteClientMatchmakerRefRequest request);
}
