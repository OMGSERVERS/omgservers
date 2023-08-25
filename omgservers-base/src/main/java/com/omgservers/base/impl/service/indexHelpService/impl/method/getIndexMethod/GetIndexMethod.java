package com.omgservers.base.impl.service.indexHelpService.impl.method.getIndexMethod;

import com.omgservers.dto.internalModule.GetIndexHelpRequest;
import com.omgservers.dto.internalModule.GetIndexHelpResponse;
import io.smallrye.mutiny.Uni;

public interface GetIndexMethod {
    Uni<GetIndexHelpResponse> getIndex(GetIndexHelpRequest request);
}
