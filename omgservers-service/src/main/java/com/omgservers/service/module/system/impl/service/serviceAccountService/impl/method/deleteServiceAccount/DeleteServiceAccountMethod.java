package com.omgservers.service.module.system.impl.service.serviceAccountService.impl.method.deleteServiceAccount;

import com.omgservers.model.dto.system.DeleteServiceAccountRequest;
import io.smallrye.mutiny.Uni;

public interface DeleteServiceAccountMethod {
    Uni<Void> deleteServiceAccount(DeleteServiceAccountRequest request);
}