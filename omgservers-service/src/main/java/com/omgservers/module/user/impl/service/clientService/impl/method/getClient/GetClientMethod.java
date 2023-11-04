package com.omgservers.module.user.impl.service.clientService.impl.method.getClient;

import com.omgservers.model.dto.user.GetClientResponse;
import com.omgservers.model.dto.user.GetClientRequest;
import io.smallrye.mutiny.Uni;

public interface GetClientMethod {
    Uni<GetClientResponse> getClient(GetClientRequest request);
}
