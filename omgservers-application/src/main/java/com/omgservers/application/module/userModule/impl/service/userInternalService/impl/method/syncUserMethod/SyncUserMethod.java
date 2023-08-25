package com.omgservers.application.module.userModule.impl.service.userInternalService.impl.method.syncUserMethod;

import com.omgservers.dto.userModule.SyncUserInternalRequest;
import com.omgservers.dto.userModule.SyncUserInternalResponse;
import io.smallrye.mutiny.Uni;

public interface SyncUserMethod {
    Uni<SyncUserInternalResponse> syncUser(SyncUserInternalRequest request);
}
