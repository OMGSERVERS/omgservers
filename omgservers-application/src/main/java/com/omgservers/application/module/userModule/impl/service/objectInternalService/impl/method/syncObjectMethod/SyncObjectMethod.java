package com.omgservers.application.module.userModule.impl.service.objectInternalService.impl.method.syncObjectMethod;

import com.omgservers.dto.userModule.SyncObjectRoutedRequest;
import com.omgservers.dto.userModule.SyncObjectInternalResponse;
import io.smallrye.mutiny.Uni;

public interface SyncObjectMethod {
    Uni<SyncObjectInternalResponse> syncObject(SyncObjectRoutedRequest request);
}
