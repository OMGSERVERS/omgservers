package com.omgservers.service.module.root.impl.service.rootService.impl.method.root.getRoot;

import com.omgservers.model.dto.root.root.GetRootRequest;
import com.omgservers.model.dto.root.root.GetRootResponse;
import io.smallrye.mutiny.Uni;

public interface GetRootMethod {
    Uni<GetRootResponse> getRoot(GetRootRequest request);
}
