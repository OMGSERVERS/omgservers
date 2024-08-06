package com.omgservers.service.module.client.impl.service.clientService.impl.method.clientMatchmakerRef.deleteClientMatchmakerRef;

import com.omgservers.schema.module.client.DeleteClientMatchmakerRefRequest;
import com.omgservers.schema.module.client.DeleteClientMatchmakerRefResponse;
import io.smallrye.mutiny.Uni;

public interface DeleteClientMatchmakerRefMethod {
    Uni<DeleteClientMatchmakerRefResponse> deleteClientMatchmakerRef(DeleteClientMatchmakerRefRequest request);
}
