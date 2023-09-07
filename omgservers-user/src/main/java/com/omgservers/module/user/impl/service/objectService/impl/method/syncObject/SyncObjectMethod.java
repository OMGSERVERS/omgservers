package com.omgservers.module.user.impl.service.objectService.impl.method.syncObject;

import com.omgservers.dto.user.SyncObjectResponse;
import com.omgservers.dto.user.SyncObjectRequest;
import io.smallrye.mutiny.Uni;

public interface SyncObjectMethod {
    Uni<SyncObjectResponse> syncObject(SyncObjectRequest request);
}
