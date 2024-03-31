package com.omgservers.service.module.client.impl.service.clientService.impl.method.clientRuntimeRef.deleteClientRuntimeRef;

import com.omgservers.model.dto.client.DeleteClientRuntimeRefRequest;
import com.omgservers.model.dto.client.DeleteClientRuntimeRefResponse;
import io.smallrye.mutiny.Uni;

public interface DeleteClientRuntimeRefMethod {
    Uni<DeleteClientRuntimeRefResponse> deleteClientRuntimeRef(DeleteClientRuntimeRefRequest request);
}
