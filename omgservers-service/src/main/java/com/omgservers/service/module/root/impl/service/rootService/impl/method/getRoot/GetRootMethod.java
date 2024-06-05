package com.omgservers.service.module.root.impl.service.rootService.impl.method.getRoot;

import com.omgservers.model.dto.root.GetRootRequest;
import com.omgservers.model.dto.root.GetRootResponse;
import io.smallrye.mutiny.Uni;

public interface GetRootMethod {
    Uni<GetRootResponse> getRoot(GetRootRequest request);
}
