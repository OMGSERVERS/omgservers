package com.omgservers.application.module.userModule.impl.service.userInternalService.impl.method.syncUserMethod;

import com.omgservers.application.module.userModule.impl.service.userInternalService.request.SyncUserInternalRequest;
import com.omgservers.application.module.userModule.impl.service.userInternalService.response.SyncUserInternalResponse;
import io.smallrye.mutiny.Uni;

public interface SyncUserMethod {
    Uni<SyncUserInternalResponse> syncUser(SyncUserInternalRequest request);
}
