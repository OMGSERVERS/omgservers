package com.omgservers.module.internal.impl.service.serviceAccountService.impl.method.syncServiceAccount;

import com.omgservers.dto.internal.SyncServiceAccountRequest;
import io.smallrye.mutiny.Uni;

public interface SyncServiceAccountMethod {
    Uni<Void> syncServiceAccount(SyncServiceAccountRequest request);
}
