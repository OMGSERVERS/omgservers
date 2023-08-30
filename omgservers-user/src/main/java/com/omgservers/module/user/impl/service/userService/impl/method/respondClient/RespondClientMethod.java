package com.omgservers.module.user.impl.service.userService.impl.method.respondClient;

import com.omgservers.module.user.impl.service.userService.request.RespondClientRequest;
import io.smallrye.mutiny.Uni;

public interface RespondClientMethod {
    Uni<Void> respondClient(RespondClientRequest request);
}