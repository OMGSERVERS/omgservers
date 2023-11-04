package com.omgservers.service.module.system.impl.service.serviceAccountService.impl.method.syncServiceAccount;

import com.omgservers.model.dto.system.SyncServiceAccountRequest;
import io.smallrye.mutiny.Uni;

public interface SyncServiceAccountMethod {
    Uni<Void> syncServiceAccount(SyncServiceAccountRequest request);
}
