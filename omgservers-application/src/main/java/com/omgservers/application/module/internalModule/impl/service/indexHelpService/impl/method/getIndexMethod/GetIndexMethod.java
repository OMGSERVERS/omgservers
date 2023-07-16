package com.omgservers.application.module.internalModule.impl.service.indexHelpService.impl.method.getIndexMethod;

import com.omgservers.application.module.internalModule.impl.service.indexHelpService.request.GetIndexHelpRequest;
import com.omgservers.application.module.internalModule.impl.service.indexHelpService.response.GetIndexHelpResponse;
import io.smallrye.mutiny.Uni;

public interface GetIndexMethod {
    Uni<GetIndexHelpResponse> getIndex(GetIndexHelpRequest request);
}
