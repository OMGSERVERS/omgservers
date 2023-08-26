package com.omgservers.base.module.internal.impl.service.serviceAccountService.impl.method.syncServiceAccount;

import com.omgservers.dto.internalModule.SyncServiceAccountRequest;
import io.smallrye.mutiny.Uni;

public interface SyncServiceAccountMethod {
    Uni<Void> syncServiceAccount(SyncServiceAccountRequest request);
}
