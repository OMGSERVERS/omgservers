package com.omgservers.service.module.system.impl.service.serviceAccountService.impl.method.deleteServiceAccount;

import com.omgservers.model.dto.system.DeleteServiceAccountRequest;
import com.omgservers.model.dto.system.DeleteServiceAccountResponse;
import io.smallrye.mutiny.Uni;

public interface DeleteServiceAccountMethod {
    Uni<DeleteServiceAccountResponse> deleteServiceAccount(DeleteServiceAccountRequest request);
}
