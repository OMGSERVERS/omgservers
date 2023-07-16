package com.omgservers.application.module.runtimeModule.impl.service.runtimeWebService;

import com.omgservers.application.module.runtimeModule.impl.service.runtimeInternalService.request.CreateRuntimeInternalRequest;
import com.omgservers.application.module.runtimeModule.impl.service.runtimeInternalService.request.DeleteRuntimeInternalRequest;
import com.omgservers.application.module.runtimeModule.impl.service.runtimeInternalService.request.GetRuntimeInternalRequest;
import com.omgservers.application.module.runtimeModule.impl.service.runtimeInternalService.response.DeleteRuntimeInternalResponse;
import com.omgservers.application.module.runtimeModule.impl.service.runtimeInternalService.response.GetRuntimeInternalResponse;
import io.smallrye.mutiny.Uni;

public interface RuntimeWebService {
    Uni<Void> createRuntime(CreateRuntimeInternalRequest request);

    Uni<GetRuntimeInternalResponse> getRuntime(GetRuntimeInternalRequest request);

    Uni<DeleteRuntimeInternalResponse> deleteRuntime(DeleteRuntimeInternalRequest request);
}
