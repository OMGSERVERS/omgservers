package com.omgservers.service.module.system.impl.service.handlerService.impl.method.viewHandler;

import com.omgservers.model.dto.system.ViewHandlersRequest;
import com.omgservers.model.dto.system.ViewHandlersResponse;
import io.smallrye.mutiny.Uni;

public interface ViewHandlersMethod {
    Uni<ViewHandlersResponse> viewHandlers(ViewHandlersRequest request);
}
