package com.omgservers.service.module.system.impl.service.serviceAccountService.impl.method.findServiceAccount;

import com.omgservers.model.dto.system.FindServiceAccountRequest;
import com.omgservers.model.dto.system.FindServiceAccountResponse;
import io.smallrye.mutiny.Uni;

public interface FindServiceAccountMethod {
    Uni<FindServiceAccountResponse> findServiceAccount(FindServiceAccountRequest request);
}
