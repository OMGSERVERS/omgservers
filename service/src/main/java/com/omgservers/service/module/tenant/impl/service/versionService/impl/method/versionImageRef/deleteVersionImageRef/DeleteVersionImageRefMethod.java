package com.omgservers.service.module.tenant.impl.service.versionService.impl.method.versionImageRef.deleteVersionImageRef;

import com.omgservers.schema.module.tenant.versionImageRef.DeleteVersionImageRefRequest;
import com.omgservers.schema.module.tenant.versionImageRef.DeleteVersionImageRefResponse;
import io.smallrye.mutiny.Uni;

public interface DeleteVersionImageRefMethod {
    Uni<DeleteVersionImageRefResponse> deleteVersionImageRef(DeleteVersionImageRefRequest request);
}
