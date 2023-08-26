package com.omgservers.base.module.internal.impl.service.serviceAccountService.impl.method.deleteServiceAccount;

import com.omgservers.dto.internalModule.DeleteServiceAccountHelpRequest;
import io.smallrye.mutiny.Uni;

public interface DeleteServiceAccountMethod {
    Uni<Void> deleteServiceAccount(DeleteServiceAccountHelpRequest request);
}
