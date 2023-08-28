package com.omgservers.module.internal.impl.service.serviceAccountService.impl.method.deleteServiceAccount;

import com.omgservers.dto.internal.DeleteServiceAccountRequest;
import io.smallrye.mutiny.Uni;

public interface DeleteServiceAccountMethod {
    Uni<Void> deleteServiceAccount(DeleteServiceAccountRequest request);
}
