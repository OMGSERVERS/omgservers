package com.omgservers.service.module.root.impl.service.rootService.impl.method.rootEntityRef.getRootEntityRef;

import com.omgservers.model.dto.root.rootEntityRef.GetRootEntityRefRequest;
import com.omgservers.model.dto.root.rootEntityRef.GetRootEntityRefResponse;
import io.smallrye.mutiny.Uni;

public interface GetRootEntityRefMethod {
    Uni<GetRootEntityRefResponse> getRootEntityRef(GetRootEntityRefRequest request);
}
