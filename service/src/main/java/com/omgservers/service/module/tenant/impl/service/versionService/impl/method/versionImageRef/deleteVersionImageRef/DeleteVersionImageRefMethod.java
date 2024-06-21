package com.omgservers.service.module.tenant.impl.service.versionService.impl.method.versionImageRef.deleteVersionImageRef;

import com.omgservers.model.dto.tenant.versionImageRef.DeleteVersionImageRefRequest;
import com.omgservers.model.dto.tenant.versionImageRef.DeleteVersionImageRefResponse;
import io.smallrye.mutiny.Uni;

public interface DeleteVersionImageRefMethod {
    Uni<DeleteVersionImageRefResponse> deleteVersionImageRef(DeleteVersionImageRefRequest request);
}
