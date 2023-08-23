package com.omgservers.application.module.runtimeModule.impl.service.runtimeInternalService.impl.method.deleteActorMethod;

import com.omgservers.application.module.runtimeModule.impl.service.runtimeInternalService.request.DeleteActorInternalRequest;
import com.omgservers.application.module.runtimeModule.impl.service.runtimeInternalService.response.DeleteActorInternalResponse;
import io.smallrye.mutiny.Uni;

public interface DeleteActorMethod {
    Uni<DeleteActorInternalResponse> deleteActor(DeleteActorInternalRequest request);
}
