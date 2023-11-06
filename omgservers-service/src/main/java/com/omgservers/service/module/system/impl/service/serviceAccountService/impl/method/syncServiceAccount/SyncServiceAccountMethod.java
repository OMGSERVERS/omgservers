package com.omgservers.service.module.system.impl.service.serviceAccountService.impl.method.syncServiceAccount;

import com.omgservers.model.dto.system.SyncServiceAccountRequest;
import com.omgservers.model.dto.system.SyncServiceAccountResponse;
import io.smallrye.mutiny.Uni;

public interface SyncServiceAccountMethod {
    Uni<SyncServiceAccountResponse> syncServiceAccount(SyncServiceAccountRequest request);
}
