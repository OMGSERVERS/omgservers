package com.omgservers.module.runtime.impl.service.runtimeService.impl.method.deleteRuntimeGrant;

import com.omgservers.dto.runtime.DeleteRuntimeGrantRequest;
import com.omgservers.dto.runtime.DeleteRuntimeGrantResponse;
import io.smallrye.mutiny.Uni;

public interface DeleteRuntimeGrantMethod {
    Uni<DeleteRuntimeGrantResponse> deleteRuntimeGrant(DeleteRuntimeGrantRequest request);
}
