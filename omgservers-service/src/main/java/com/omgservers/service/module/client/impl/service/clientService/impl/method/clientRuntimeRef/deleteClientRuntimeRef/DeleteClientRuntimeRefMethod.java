package com.omgservers.service.module.client.impl.service.clientService.impl.method.clientRuntimeRef.deleteClientRuntimeRef;

import com.omgservers.schema.module.client.DeleteClientRuntimeRefRequest;
import com.omgservers.schema.module.client.DeleteClientRuntimeRefResponse;
import io.smallrye.mutiny.Uni;

public interface DeleteClientRuntimeRefMethod {
    Uni<DeleteClientRuntimeRefResponse> deleteClientRuntimeRef(DeleteClientRuntimeRefRequest request);
}
