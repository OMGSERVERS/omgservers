package com.omgservers.service.module.system.impl.service.handlerService.impl.method.deleteHandler;

import com.omgservers.model.dto.system.DeleteHandlerRequest;
import com.omgservers.model.dto.system.DeleteHandlerResponse;
import io.smallrye.mutiny.Uni;

public interface DeleteHandlerMethod {
    Uni<DeleteHandlerResponse> deleteHandler(DeleteHandlerRequest request);
}
