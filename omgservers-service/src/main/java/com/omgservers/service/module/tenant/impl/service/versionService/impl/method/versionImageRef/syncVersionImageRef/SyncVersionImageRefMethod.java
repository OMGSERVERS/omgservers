package com.omgservers.service.module.tenant.impl.service.versionService.impl.method.versionImageRef.syncVersionImageRef;

import com.omgservers.model.dto.tenant.versionImageRef.SyncVersionImageRefRequest;
import com.omgservers.model.dto.tenant.versionImageRef.SyncVersionImageRefResponse;
import io.smallrye.mutiny.Uni;

public interface SyncVersionImageRefMethod {
    Uni<SyncVersionImageRefResponse> syncVersionImageRef(SyncVersionImageRefRequest request);
}
