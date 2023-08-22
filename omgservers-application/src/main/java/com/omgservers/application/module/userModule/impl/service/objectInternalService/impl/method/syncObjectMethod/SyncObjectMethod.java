package com.omgservers.application.module.userModule.impl.service.objectInternalService.impl.method.syncObjectMethod;

import com.omgservers.application.module.userModule.impl.service.objectInternalService.request.SyncObjectInternalRequest;
import com.omgservers.application.module.userModule.impl.service.objectInternalService.response.SyncObjectInternalResponse;
import io.smallrye.mutiny.Uni;

public interface SyncObjectMethod {
    Uni<SyncObjectInternalResponse> syncObject(SyncObjectInternalRequest request);
}
