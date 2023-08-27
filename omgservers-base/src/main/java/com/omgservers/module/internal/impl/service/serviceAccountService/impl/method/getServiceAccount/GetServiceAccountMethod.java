package com.omgservers.module.internal.impl.service.serviceAccountService.impl.method.getServiceAccount;

import com.omgservers.dto.internal.GetServiceAccountHelpRequest;
import com.omgservers.dto.internal.GetServiceAccountHelpResponse;
import io.smallrye.mutiny.Uni;

public interface GetServiceAccountMethod {
    Uni<GetServiceAccountHelpResponse> getServiceAccount(GetServiceAccountHelpRequest request);
}
