package com.omgservers.application.module.userModule.impl.service.objectInternalService.impl.method.syncObjectMethod;

import com.omgservers.application.module.userModule.impl.service.objectInternalService.request.SyncObjectInternalRequest;
import io.smallrye.mutiny.Uni;

public interface SyncObjectMethod {
    Uni<Void> syncObject(SyncObjectInternalRequest request);
}
