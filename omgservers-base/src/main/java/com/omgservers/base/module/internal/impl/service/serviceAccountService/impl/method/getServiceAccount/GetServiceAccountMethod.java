package com.omgservers.base.module.internal.impl.service.serviceAccountService.impl.method.getServiceAccount;

import com.omgservers.dto.internalModule.GetServiceAccountHelpRequest;
import com.omgservers.dto.internalModule.GetServiceAccountHelpResponse;
import io.smallrye.mutiny.Uni;

public interface GetServiceAccountMethod {
    Uni<GetServiceAccountHelpResponse> getServiceAccount(GetServiceAccountHelpRequest request);
}
