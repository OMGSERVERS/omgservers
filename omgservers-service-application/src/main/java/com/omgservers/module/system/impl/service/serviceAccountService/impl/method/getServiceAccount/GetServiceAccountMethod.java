package com.omgservers.module.system.impl.service.serviceAccountService.impl.method.getServiceAccount;

import com.omgservers.model.dto.system.GetServiceAccountRequest;
import com.omgservers.model.dto.system.GetServiceAccountResponse;
import io.smallrye.mutiny.Uni;

public interface GetServiceAccountMethod {
    Uni<GetServiceAccountResponse> getServiceAccount(GetServiceAccountRequest request);
}
