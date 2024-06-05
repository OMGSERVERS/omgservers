package com.omgservers.service.module.root.impl.service.rootService.impl.method.deleteRoot;

import com.omgservers.model.dto.root.DeleteRootRequest;
import com.omgservers.model.dto.root.DeleteRootResponse;
import io.smallrye.mutiny.Uni;

public interface DeleteRootMethod {
    Uni<DeleteRootResponse> deleteRoot(DeleteRootRequest request);
}
