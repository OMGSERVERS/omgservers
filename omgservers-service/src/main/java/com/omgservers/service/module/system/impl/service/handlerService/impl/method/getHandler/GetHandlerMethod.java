package com.omgservers.service.module.system.impl.service.handlerService.impl.method.getHandler;

import com.omgservers.model.dto.system.GetHandlerRequest;
import com.omgservers.model.dto.system.GetHandlerResponse;
import io.smallrye.mutiny.Uni;

public interface GetHandlerMethod {
    Uni<GetHandlerResponse> getHandler(GetHandlerRequest request);
}
