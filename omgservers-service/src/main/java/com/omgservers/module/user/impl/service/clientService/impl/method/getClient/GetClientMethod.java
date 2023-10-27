package com.omgservers.module.user.impl.service.clientService.impl.method.getClient;

import com.omgservers.dto.user.GetClientResponse;
import com.omgservers.dto.user.GetClientRequest;
import io.smallrye.mutiny.Uni;

public interface GetClientMethod {
    Uni<GetClientResponse> getClient(GetClientRequest request);
}
