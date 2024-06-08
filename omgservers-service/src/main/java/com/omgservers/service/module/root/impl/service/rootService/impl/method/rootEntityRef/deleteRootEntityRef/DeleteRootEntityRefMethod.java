package com.omgservers.service.module.root.impl.service.rootService.impl.method.rootEntityRef.deleteRootEntityRef;

import com.omgservers.model.dto.root.rootEntityRef.DeleteRootEntityRefRequest;
import com.omgservers.model.dto.root.rootEntityRef.DeleteRootEntityRefResponse;
import io.smallrye.mutiny.Uni;

public interface DeleteRootEntityRefMethod {
    Uni<DeleteRootEntityRefResponse> deleteRootEntityRef(DeleteRootEntityRefRequest request);
}
