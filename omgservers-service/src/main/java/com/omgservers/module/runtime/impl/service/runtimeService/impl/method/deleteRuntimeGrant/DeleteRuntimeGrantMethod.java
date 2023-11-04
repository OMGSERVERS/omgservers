package com.omgservers.module.runtime.impl.service.runtimeService.impl.method.deleteRuntimeGrant;

import com.omgservers.model.dto.runtime.DeleteRuntimeGrantRequest;
import com.omgservers.model.dto.runtime.DeleteRuntimeGrantResponse;
import io.smallrye.mutiny.Uni;

public interface DeleteRuntimeGrantMethod {
    Uni<DeleteRuntimeGrantResponse> deleteRuntimeGrant(DeleteRuntimeGrantRequest request);
}
