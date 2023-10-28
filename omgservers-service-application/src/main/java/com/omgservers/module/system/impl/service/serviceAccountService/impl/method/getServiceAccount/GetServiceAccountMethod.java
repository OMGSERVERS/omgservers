package com.omgservers.module.system.impl.service.serviceAccountService.impl.method.getServiceAccount;

import com.omgservers.model.dto.internal.GetServiceAccountRequest;
import com.omgservers.model.dto.internal.GetServiceAccountResponse;
import io.smallrye.mutiny.Uni;

public interface GetServiceAccountMethod {
    Uni<GetServiceAccountResponse> getServiceAccount(GetServiceAccountRequest request);
}
