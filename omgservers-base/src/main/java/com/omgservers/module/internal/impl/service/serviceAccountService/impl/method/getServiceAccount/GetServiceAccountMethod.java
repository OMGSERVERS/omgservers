package com.omgservers.module.internal.impl.service.serviceAccountService.impl.method.getServiceAccount;

import com.omgservers.dto.internal.GetServiceAccountRequest;
import com.omgservers.dto.internal.GetServiceAccountResponse;
import io.smallrye.mutiny.Uni;

public interface GetServiceAccountMethod {
    Uni<GetServiceAccountResponse> getServiceAccount(GetServiceAccountRequest request);
}
