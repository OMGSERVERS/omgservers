package com.omgservers.service.module.root.impl.service.rootService.impl.method.root.deleteRoot;

import com.omgservers.schema.module.root.root.DeleteRootRequest;
import com.omgservers.schema.module.root.root.DeleteRootResponse;
import io.smallrye.mutiny.Uni;

public interface DeleteRootMethod {
    Uni<DeleteRootResponse> deleteRoot(DeleteRootRequest request);
}
