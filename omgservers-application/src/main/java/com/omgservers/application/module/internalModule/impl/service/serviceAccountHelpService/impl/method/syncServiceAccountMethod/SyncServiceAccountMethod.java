package com.omgservers.application.module.internalModule.impl.service.serviceAccountHelpService.impl.method.syncServiceAccountMethod;

import com.omgservers.application.module.internalModule.impl.service.serviceAccountHelpService.request.SyncServiceAccountHelpRequest;
import io.smallrye.mutiny.Uni;

public interface SyncServiceAccountMethod {
    Uni<Void> syncServiceAccount(SyncServiceAccountHelpRequest request);
}
