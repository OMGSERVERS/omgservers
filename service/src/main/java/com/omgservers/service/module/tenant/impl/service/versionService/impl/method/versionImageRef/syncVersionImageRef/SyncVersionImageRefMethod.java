package com.omgservers.service.module.tenant.impl.service.versionService.impl.method.versionImageRef.syncVersionImageRef;

import com.omgservers.schema.module.tenant.versionImageRef.SyncVersionImageRefRequest;
import com.omgservers.schema.module.tenant.versionImageRef.SyncVersionImageRefResponse;
import io.smallrye.mutiny.Uni;

public interface SyncVersionImageRefMethod {
    Uni<SyncVersionImageRefResponse> syncVersionImageRef(SyncVersionImageRefRequest request);
}
