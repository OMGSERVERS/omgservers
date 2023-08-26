package com.omgservers.application.module.userModule.impl.service.clientInternalService.impl.method.deleteClientMethod;

import com.omgservers.dto.userModule.DeleteClientShardRequest;
import com.omgservers.dto.userModule.DeleteClientInternalResponse;
import io.smallrye.mutiny.Uni;

public interface DeleteClientMethod {
    Uni<DeleteClientInternalResponse> deleteClient(DeleteClientShardRequest request);
}
