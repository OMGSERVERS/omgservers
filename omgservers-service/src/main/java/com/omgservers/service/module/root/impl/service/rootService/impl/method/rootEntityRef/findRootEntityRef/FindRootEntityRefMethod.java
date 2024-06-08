package com.omgservers.service.module.root.impl.service.rootService.impl.method.rootEntityRef.findRootEntityRef;

import com.omgservers.model.dto.root.rootEntityRef.FindRootEntityRefRequest;
import com.omgservers.model.dto.root.rootEntityRef.FindRootEntityRefResponse;
import io.smallrye.mutiny.Uni;

public interface FindRootEntityRefMethod {
    Uni<FindRootEntityRefResponse> findRootEntityRef(FindRootEntityRefRequest request);
}
