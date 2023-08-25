package com.omgservers.base.impl.service.serviceAccountHelpService.impl.method.syncServiceAccountMethod;

import com.omgservers.dto.internalModule.SyncServiceAccountHelpRequest;
import io.smallrye.mutiny.Uni;

public interface SyncServiceAccountMethod {
    Uni<Void> syncServiceAccount(SyncServiceAccountHelpRequest request);
}
